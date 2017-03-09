package com.mljr.spider.processor;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fulin on 2017/3/3.
 */
public class TencentCarNetworkProcessor  extends AbstractPageProcessor{
      //品牌地址格式
      private  final static String START_BRAND_URL="http://js.data.auto.qq.com/car_manufacturer/%s/serial_list_json_baojia.js?_=1484291243255";
      //获取详细的品牌下对应的车系
      private final static  String BRAND_CATGORY_URL="http://baojia.auto.qq.com/php/baojia_detail.php?info=0&serialID=%s";
      private static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";
      //最终详情页
      public final static String TARGET_URL_REGEX = "http://data\\.auto\\.qq\\.com/car_serial/[0-9]{1,6}/modelscompare\\.shtml";
      private static Site site = Site.me().setDomain("auto.qq.com")
                  .setSleepTime(300)
                  .setRetrySleepTime(2000)
                  .setRetryTimes(3)
                  .setUserAgent(USER_AGENT );
      public  TencentCarNetworkProcessor(){
        super(site);
      }
      @Override
      boolean onProcess(Page page) {
        String currentUrl = page.getUrl().get();
        //处理首地址得到品牌地址
        if (currentUrl.contains("manufacturer_list_json")) {
          page.setSkip(true);
          List<String> allList = Lists.newArrayList();
          List<String> listMap = Lists.newArrayList();
          try {
            String data = Jsoup.connect(currentUrl).ignoreContentType(true).get().text();
            if(StringUtils.isNotBlank(data)){
              if(data.contains("[")&&data.contains("]")){
                data=data.substring(data.indexOf("["),data.indexOf("]")+1);
              }
              allList.addAll(getCodes(data));
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
          //获取品牌标签
          for (int i = 0; i <allList.size() ; i++) {
            String middleUrl = String.format(START_BRAND_URL,allList.get(i));
            listMap.add(middleUrl);
          }
          page.addTargetRequests(listMap);
        }else if(currentUrl.contains("serial_list_json_baojia")){
          page.setSkip(true);
          //抓取品牌对应的所有车系
          List<String> allList = Lists.newArrayList();
          List<String> listMap = Lists.newArrayList();
          try {
            String data = Jsoup.connect(currentUrl).ignoreContentType(true).get().text();
            if(StringUtils.isNotBlank(data)){
              if(data.contains("[")&&data.contains("]")){
                data=data.substring(data.indexOf("["),data.indexOf("]")+1);
              }
              allList.addAll(getCodes(data));
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
          //获取品牌下面的所有车系
          for (int i = 0; i <allList.size() ; i++) {
            String middleUrl = String.format(BRAND_CATGORY_URL,allList.get(i));
            listMap.add(middleUrl);
          }
          page.addTargetRequests(listMap);
        }else if(currentUrl.contains("baojia_detail") && currentUrl.contains("serialID")){
          page.setSkip(true);
          //抓取详情页
          List<String> peizhiUrlone = page.getHtml().links().regex(TARGET_URL_REGEX).all();
          Set<String> set2 = Sets.newHashSet();
          for (String peizhiUrl : peizhiUrlone) {
            set2.add(peizhiUrl);
          }
          //将地址丢进去
          List<String> listMap = Lists.newArrayList();
          for (String s : set2) {
            listMap.add(s);
          }
          page.addTargetRequests(listMap);

        }else if(currentUrl.matches(TARGET_URL_REGEX)){
          processCarUrlone(page);
        }
        return true;
      }

      public  void processCarUrlone(Page page){
        String currentUrl = page.getUrl().get();
        System.out.println("目标地址一："+currentUrl);
        //获取对应的数据存入到队列中去
        Html html = page.getHtml();
        //先获取表格的参数列表
        List<String>  parameter_list =  html.xpath("//ul[@id='config_name']//li[@class !='bar']//tidyText()").all();
        //获取车辆信息的个数
        List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
        int carNum =  html.xpath("//ul[@class='name blue1']//li").all().size();
        if(carNum ==0){
          page.setSkip(true);
        }else{
          for (int i = 0; i<carNum ; i++) {
            Map<String,String> map = new HashMap<String,String>();
            Map<String, String> map2 = new HashMap<String, String>();
            String path = String.format("//div[@class='data cl']//ul//li[@id='car_%d']//ul//li[@class !='bar']//tidyText()",i);
            List<String> list = html.xpath(path).all();
            //将每一列数据对应起来
            for (int j = 0; j <list.size() ; j++) {
              map2.put(parameter_list.get(j).replaceAll("<(.*)>", "").replace("\n","").replace("*","").trim(),list.get(j).replaceAll("<(.*)>", "").replace("\n","").replace("*"," ").trim());
            }
            //获取品牌和生产商
            String car_brand = html.xpath("//div[@class='header-bread']//a[4]//text()").get();
            map.put("car_brand",car_brand.trim());
            //获取车配置数据
            String colorPath =String.format("#model_name_%d > a:nth-child(1)",i);
            //获取车系
            String car_categroy=html.xpath("//div[@class='trigger']//a//text()").get();
            map.put("car_category",car_categroy);
            //获取汽车的年款配置
            String model= html.css(colorPath,"title").toString();
            if(StringUtils.isBlank(model)){
              //titile属性中拿不到的情况下，再去text()中去拿
              path = String.format("//div[@class='right']//ul[@class='name blue1']//li[@id='model_name_%d']//a/text()", i);
              model=html.xpath(path).get();
            }
            String uniqueCarBrand= car_categroy+" "+model;
            if(StringUtils.isNotBlank(uniqueCarBrand)){
              if(uniqueCarBrand.contains("MT")){
                uniqueCarBrand = uniqueCarBrand.replace("MT","手动");
              }else if(uniqueCarBrand.contains("AT")){
                uniqueCarBrand = uniqueCarBrand.replace("AT","自动");
              }else if(uniqueCarBrand.contains("CVT")){
                uniqueCarBrand = uniqueCarBrand.replace("CVT","自动");
              }
            }
            //对表头进行拆分处理
            String[] carHome = uniqueCarBrand.split(" ");
            String carHomeConfig="";
            String carTime="";
            String reg = "[0-9]{4}款";
            Pattern   pattern = Pattern.compile(reg);
            for (int j = 0; j <carHome.length ; j++) {
              if(carHome[j].contains("款")){
                Matcher matcher = pattern.matcher(carHome[j]);
                while(matcher.find()){
                  carTime=matcher.group();
                }
              }
              if(carHome[j].contains("型")){
                carHomeConfig += carHome[j];
              }else if(carHome[j].contains("版")){
                carHomeConfig+=carHome[j];
              }
            }
            //放入车款
            map.put("car_time",carTime);
            //放入车的配置
            map.put("car_config",carHomeConfig);
            String regheader = "[0-9].[0-9]{1,2}TFSI|[0-9]{1,2}TFSI";
            String header_brand=" ";
            for (int j = 0; j <carHome.length ; j++) {
                 if(carHome[j].matches(regheader)){
                    int num1 = carHome[j].indexOf("T");
                    String s1 = carHome[j].substring(0,num1).trim();
                    String s2 = carHome[j].substring(num1,carHome[j].length()).trim();
                    header_brand+=s1+" "+s2+" ";
                 }else{
                   header_brand+=carHome[j]+" ";
                 }
            }
            map.put("unique_car_brand",header_brand.trim());
            //获取车型
            String car_model=html.xpath("//div[@class='header-bread']//a[2]//text()").get();
            map.put("car_model",car_model.trim());
            //获取厂商指导价
            if(map2.containsKey("指导价格(万)")){
              map.put("car_price",map2.get("指导价格(万)"));
            }else{
              map.put("car_price","");
            }
            //获取厂商
            map.put("car_factory",car_brand);
            //获取生产地信息
            if(map2.containsKey("产地")){
              map.put("car_country",map2.get("产地"));
            }else{
              map.put("car_country","");
            }
            //放入车长
            if(map2.containsKey("长/宽/高尺寸(mm)")){
              String carSize = map2.get("长/宽/高尺寸(mm)");
              if(StringUtils.isBlank(carSize)){
                map.put("car_length","");
                map.put("car_width","");
                map.put("car_height","");
              }else{
                String[] car_size = carSize.split(" ");
                if(car_size.length==3){
                  map.put("car_length",car_size[0]);
                  map.put("car_width",car_size[1]);
                  map.put("car_height",car_size[2]);
                }else if(car_size.length==2){
                  map.put("car_length",car_size[0]);
                  map.put("car_width",car_size[1]);
                  map.put("car_height","");
                }else if(car_size.length==1){
                  map.put("car_length",car_size[0]);
                  map.put("car_width","");
                  map.put("car_height","");
                }else{
                  map.put("car_length","");
                  map.put("car_width","");
                  map.put("car_height","");
                }
              }
            }else{
              map.put("car_length","");
              map.put("car_width","");
              map.put("car_height","");
            }
            //放入轴距
            if(map2.containsKey("轴距(mm)")){
              map.put("car_wheel_base",map2.get("轴距(mm)"));
            }else{
              map.put("car_wheel_base","");
            }
            //整车重量
            if(map2.containsKey("整备质量(kg)")){
              map.put("car_weight",map2.get("整备质量(kg)"));
            }else{
              map.put("car_weight","");
            }
            //放入车座数
            if(map2.containsKey("座位数(个)")){
              String seatNum = map2.get("座位数(个)");
              if(StringUtils.isBlank(seatNum)){
                map.put("car_seatNum","");
              }else{
                if(seatNum.contains("座")){
                  map.put("car_seatNum",seatNum);
                }else{
                  map.put("car_seatNum",seatNum+"座");
                }
              }
            }else{
              map.put("car_seatNum","");
            }
            //汽车排量
            if(map2.containsKey("排气量(cc)")){
              map.put("car_displacement",map2.get("排气量(cc)"));
            }else{
              map.put("car_displacement","");
            }
            //放置官方标准排量
            String sT = "[0-9].[0-9]{1,2}[T|L]";
            String sL="";
            for(int j = 0; j <carHome.length ; j++){
              if(carHome[j].matches(sT)){
                sL=carHome[j];
              }
            }
            map.put("official_displacement",sL);
            //放入发动机型号
            if(map2.containsKey("发动机")){
              map.put("car_engine",map2.get("发动机").replace("--","").replace("⊙","").replace("●",""));
            }else{
              map.put("car_engine","");
            }
            //汽车发动机功耗
            if(map2.containsKey("最大马力(Ps)")){
              String mali = map2.get("最大马力(Ps)").trim();
              map.put("car_engine_power",mali);
            }else{
              map.put("car_engine_power","");
            }
            //汽车燃料
            if(map2.containsKey("燃油标准")){
              String[] fuelType = map2.get("燃油标准").split(" ");
              String fuel_type="";
              for (int j = 0; j < fuelType.length; j++) {
                if(fuelType[j].contains("油") || fuelType[j].contains("电动")||fuelType[j].contains("混合")){
                  fuel_type+=fuelType[j];
                }
              }
              map.put("car_fuel",fuel_type);
            }else{
              map.put("car_fuel","");
            }
            //环保标准
            if(map2.containsKey("环保标准")){
              map.put("car_environment",map2.get("环保标准"));
            }else{
              map.put("car_environment","");
            }
            //放入变速箱类型
            if(map2.containsKey("变速器")){
              map.put("car_gearbox",map2.get("变速器"));
            }else{
              map.put("car_gearbox","");
            }
            //汽车驱动方式
            if(map2.containsKey("驱动方式")){
              map.put("car_driver",map2.get("驱动方式"));
            }else{
              map.put("car_driver","");
            }
            //车身颜色
            if(map2.containsKey("车身颜色")){
              String carColor = map2.get("车身颜色").replace("--","").trim();
              if(StringUtils.isNotBlank(carColor)){
                map.put("car_color",carColor.replace(" ",","));
              }else{
                map.put("car_color","");
              }
            }else{
              map.put("car_color","");
            }
            //放入车身结构
            if(map2.containsKey("车身结构")){
              map.put("car_structure",map2.get("车身结构"));
            }else{
              map.put("car_structure","");
            }
            //汽车电动天窗
            if(map2.containsKey("天窗")){
              String sunroof = map2.get("天窗").replace("--","");
              if(StringUtils.isNotBlank(sunroof)){
                if(sunroof.contains("电动")&& sunroof.contains("全景")){
                  map.put("car_electric_sunroof","1");
                  map.put("car_open_sunroof","1");
                }else if(sunroof.contains("电动")&& !sunroof.contains("全景")){
                  map.put("car_electric_sunroof","1");
                  map.put("car_open_sunroof","0");
                }else{
                  map.put("car_electric_sunroof","0");
                  map.put("car_open_sunroof","0");
                }
              }else{
                map.put("car_electric_sunroof","0");
                map.put("car_open_sunroof","0");
              }
            }else{
              map.put("car_electric_sunroof","0");
              map.put("car_open_sunroof","0");
            }
            //座椅材质
            if(map2.containsKey("真皮座椅")){
              String seatMaterial = map2.get("真皮座椅").replace("--","").trim();
              if(StringUtils.isNotBlank(seatMaterial)){
                map.put("car_seat_material","真皮");
              }else{
                map.put("car_seat_material","");
              }
            }else{
              map.put("car_seat_material","");
            }
            //车身稳定控制
            if(map2.containsKey("车身稳定控制(ESP/DSC/VSC)")){
              String  control = map2.get("车身稳定控制(ESP/DSC/VSC)").replace("--","").trim();
              if(StringUtils.isNotBlank(control)){
                map.put("car_steady_control","1");
              }else{
                map.put("car_steady_control","0");
              }
            }else{
              map.put("car_steady_control","0");
            }
            //大灯
            if(map2.containsKey("氙气大灯")){
              String headMap=map2.get("氙气大灯").replace("--","").replace("⊙","").trim();
              if(StringUtils.isNotBlank(headMap)){
                map.put("car_headlamp","氙气");
              }else{
                map.put("car_headlamp","");
              }
            }else{
              map.put("car_headlamp","");
            }
            //gps导航
            if(map2.containsKey("电子导航系统")){
              String gps = map2.get("电子导航系统").replace("--","").trim();
              if(StringUtils.isNotBlank(gps)){
                map.put("car_gps","1");
              }else{
                map.put("car_gps","0");
              }
            }else{
              map.put("car_gps","0");
            }
            //定速巡航
            if(map2.containsKey("定速巡航")){
              String speed =map2.get("定速巡航").replace("--","").trim();
              if(StringUtils.isNotBlank(speed)){
                map.put("car_steady_speed","1");
              }else{
                map.put("car_steady_speed","0");
              }
            }else{
              map.put("car_steady_speed","0");
            }
            //倒车雷达
            map.put("front_radar","0");
            map.put("back_radar","0");
            //汽车空调
            if(map2.containsKey("手动空调") && map2.containsKey("自动空调") ){
              String aircon= map2.get("手动空调").replace("--","").replace("⊙","").trim();
              String aircon2= map2.get("自动空调").replace("--","").replace("⊙","").trim();
              if( StringUtils.isNotBlank(aircon2)){
                map.put("car_conditioner","自动");
              }else if(StringUtils.isNotBlank(aircon)){
                map.put("car_conditioner","手动");
              }else{
                map.put("car_conditioner","");
              }
            }else if(map2.containsKey("手动空调") && !map2.containsKey("自动空调")){
              String aircon= map2.get("手动空调").replace("--","").replace("⊙","").trim();
              if(StringUtils.isNotBlank(aircon)){
                map.put("car_conditioner","手动");
              }else{
                map.put("car_conditioner","");
              }
            }else if(map2.containsKey("自动空调") && !map2.containsKey("手动空调")){
              String aircon2= map2.get("自动空调").replace("--","").replace("⊙","").trim();
              if(StringUtils.isNotBlank(aircon2)){
                map.put("car_conditioner","自动");
              }else{
                map.put("car_conditioner","");
              }
            }else{
              map.put("car_conditioner","");
            }
            //多功能方向盘
            if(map2.containsKey("多功能方向盘")){
              String wheel = map2.get("多功能方向盘").replace("--","").replace("⊙","");
              if(StringUtils.isNotBlank(wheel)){
                map.put("car_wheel","1");
              }else{
                map.put("car_wheel","0");
              }
            }else{
              map.put("car_wheel","0");
            }
            //车厢长度
            map.put("car_carriage_length","");
            //车厢宽度
            map.put("car_carriage_width","");
            //车厢高度
            map.put("car_carriage_height","");
            //吨位级别
            map.put("car_level","");
            //公告型号
            map.put("car_notice","");
            //座位排数
            map.put("car_seat_arrangement","");
            //电动座椅调节
            if(map2.containsKey("驾驶员座椅调节")){
              String  seatAdjust = map2.get("驾驶员座椅调节").replace("--","").trim();
              if(StringUtils.isNotBlank(seatAdjust)){
                if(seatAdjust.contains("电动")){
                  map.put("car_seat_electric","1");
                }else{
                  map.put("car_seat_electric","0");
                }
              }else{
                map.put("car_seat_electric","0");
              }
            }else{
              map.put("car_seat_electric","0");
            }
            //汽车来源
            map.put("source_come","腾讯汽车");
            map.put("source_url",currentUrl);
            listmap.add(map);
          }
          page.putField("data", listmap);
        }

      }
      public static List<String> getCodes(String data) {
        Gson gson = new GsonBuilder().create();
        List<String> list = gson.fromJson(data, ArrayList.class);
        List<String> result = Lists.newArrayList();
        for (Object str : list) {
          if (str instanceof LinkedTreeMap) {
            Map m = (LinkedTreeMap) str;
            result.add((String) (m.get("ID")));
          }
        }
        return result;
      }
}
