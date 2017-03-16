/**
 *
 */
package com.mljr.spider.processor;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Html;

import java.util.*;

public class Auto163Processor extends AbstractPageProcessor {

  public Auto163Processor() {
    super(site);
  }
  private static String USER_AGENT ="Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";
  //构造函数
  private static Site site = Site.me()
          .setDomain("auto.163.com")
          .setSleepTime(300)
          .setRetrySleepTime(2000)
          .setTimeOut(90000)
          .setRetryTimes(3)
           .setUserAgent(USER_AGENT);
  public final static String START_URL = "http://product.auto.163.com/brand/";
  public final static String SECOND_URL_REGEX = "http://product\\.auto\\.163\\.com/brand/[a-z]/";
  public final static String THIRD_URL_REGEX = "http://product\\.auto\\.163\\.com/series/[0-9]{1,6}\\.html";
  public final static String SELL_URL_REGEX = "http://product\\.auto\\.163\\.com/series/config1/[0-9]{1,6}_[0-9]{1,6}[a-zA-Z]{1,6}\\.html";
  //在售车的url类型
  @Override
  boolean onProcess(Page page) {
    String currentUrl = page.getUrl().get();
    if (currentUrl.equals(START_URL)) {
      page.setSkip(true);
      List<String> brandUrls = page.getHtml().xpath("//div[@class='clearfix']").links().regex(SECOND_URL_REGEX).all();
      for (String url : brandUrls) {
        page.addTargetRequest(url);
      }
    } else if (currentUrl.matches(SECOND_URL_REGEX)) {
      page.setSkip(true);
      List<String> urls = page.getHtml().xpath("//div[@class='bd']//div[@class='group']//div[@class='gbd']//div[@class='gbox gbox1']").links().all();
      Set<String> set2 = Sets.newHashSet();
      //去重
      for (String url : urls) {
        if (url.contains("#")) {
          url = url.substring(0, url.lastIndexOf("#"));
        }
        if (url.matches(THIRD_URL_REGEX)) {
          set2.add(url);
        }
      }
      for (String s : set2) {
        page.addTargetRequest(s);
      }
    }else if (currentUrl.matches(THIRD_URL_REGEX)) {
      page.setSkip(true);
      //抓取停售的汽车
      List<String> pageUrl = page.getHtml().links().all();
      Set<String> set = Sets.newHashSet();
      //去重
      for (String url : pageUrl) {
        if (url.contains("#")) {
          url = url.substring(0, url.lastIndexOf("#"));
        }
        set.add(url);
      }
      for(String url : set){
        String urls= CharMatcher.whitespace().replaceFrom(CharMatcher.anyOf("\r\n\t").replaceFrom(url, ""), "");
        if (urls.contains("config_compare")){
          page.addTargetRequest(urls);
        }
        if (urls.matches(SELL_URL_REGEX)) {
          page.addTargetRequest(urls);
        }
      }
    } else if (currentUrl.contains("config1")||currentUrl.contains("config_compare")) {
      processCarUrlone(page);
    }
    return true;
  }
  public  void processCarUrlone(Page page){
     final String sLine="--";
     final String sCell="○";
     final String sBlack="●";
    String currentUrl = page.getUrl().toString();
    Html html = page.getHtml();
    //获取表格的有效行
    int carNum = 0;
    if(currentUrl.contains("config1")){
      carNum = html.xpath("//div[@class='cell']//div//div[@class='action']//a[@class='btn_compare']//em").all().size();
    }else if(currentUrl.contains("config_compare")){
      carNum = html.xpath("//div[@class='cell']//div[@class='item_car_info']//div[@class='action']//a[@class='btn_car_change']//em").all().size();
    }
    //用来存放一辆车的信息
    List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
    List<String> list_name = html.xpath("//div[@class='car_config_param_names']//div[@class='row']//div//span//tidyText()").all();
    if(carNum>0){
      for (int i = 1; i <= carNum ; i++) {
        Map<String,String> map = new HashMap<String,String>();
        Map<String, String> map2 = new HashMap<String, String>();
        //抓取每辆车的具体参数
        String path = String.format("//div[@class='car_config_param_list']//div[@class='row']//div[%d]//tidyText()",i);
        List<String> list = html.xpath(path).all();
        //将每一列数据对应起来
        for (int j = 0; j <list.size() ; j++) {
          String clearData = list.get(j).replaceAll("<(.*)>", "").replace("\n","").trim();
          map2.put(list_name.get(j).replaceAll("<(.*)>", "").replace("\n","").trim(),clearData);
        }
        //获取车配置数据
        path = String.format("//div[@class='car_config_param_head']//div//div//div[%d]//h3//tidyText()", i);
        String model= html.xpath(path).get().replaceAll("<(.*)>", "").replace("\n"," ").trim();
        map.put("unique_car_brand",model);
        //获取厂商指导价
        path = String.format("//div[@class='car_config_param_head']//div//div//div[%d]//div[@class='price']//text()", i);
        String price = html.xpath(path).get().replaceAll("<(.*)>", "").replace("\n"," ").trim();
        int xPrice = price.indexOf("：");
        price=price.substring(xPrice+1,price.length());
         //获取年款
        String[] carHome = model.split(" ");
        String carHomeConfig="";
        String carTime="";
        for (int j = 0; j <carHome.length ; j++) {
          if(carHome[j].contains("款")){
            carTime+=carHome[j];
          }
          if(carHome[j].contains("型")){
            carHomeConfig += carHome[j];
          }else if(carHome[j].contains("版")){
            carHomeConfig+=carHome[j];
          }
        }
        //获取车系
        String title = carHome[0].replace("-","").trim();
        if(StringUtils.isNotBlank(title)){
          map.put("car_category",title);
        }else{
          map.put("car_category","");
        }
        //放置官方标准排量
        String sT = "[0-9].[0-9]{1,2}[T|L]";
        String sL="";
        for(int j = 0; j <carHome.length ; j++){
          if(carHome[j].matches(sT)){
            sL=carHome[j];
          }
        }
        if(StringUtils.isNotBlank(sL)){
          map.put("official_displacement",sL);
        }else{
          //从发动机里面拿
          if(map2.containsKey("发动机")){
            String[]  ssL = map2.get("发动机").split(" ");
            for(int j = 0; j <ssL.length ; j++){
              if(ssL[j].matches(sT)){
                sL=ssL[j];
              }
            }
            if(StringUtils.isNotBlank(sL)){
              map.put("official_displacement",sL);
            }else{
              map.put("official_displacement","");
            }

          }else{
            map.put("official_displacement","");
          }
        }
        //设置车的颜色
        if(map2.containsKey("车身颜色")){
          String car_color = map2.get("车身颜色").replace(sLine,"").replace(sCell,"").trim();
          map.put("car_color",car_color);
        }else{
          map.put("car_color","");
        }
        //放入车款
        map.put("car_time",carTime);
        //放入车的配置
        map.put("car_config",carHomeConfig);
        //放入厂商指导价
        map.put("car_price",price);
        //放入车的品牌
        if(map2.containsKey("厂商")){
          map.put("car_brand",map2.get("厂商"));
        }else{
          map.put("car_brand","");
        }
        //放入车厂
        if(map2.containsKey("厂商")){
          map.put("car_factory",map2.get("厂商"));
        }else{
          map.put("car_factory","");
        }
        //判断国别
        if(map2.containsKey("厂商")){
          String country = map2.get("厂商");
          if(country.contains("进口")){
            map.put("car_country","进口");
          }else{
            map.put("car_country","国产");
          }
        }else{
          map.put("car_country","");
        }
        //放入车型对应汽车之家是级别
        if(map2.containsKey("车身结构")){
          map.put("car_model",map2.get("车身结构"));
        }else{
          map.put("car_model","");
        }
        //放入车长
        if(map2.containsKey("车长(mm)")){
          map.put("car_length",map2.get("车长(mm)"));
        }else{
          map.put("car_length","");
        }
        //放入车宽
        if(map2.containsKey("车宽(mm)")){
          map.put("car_width",map2.get("车宽(mm)"));
        }else{
          map.put("car_width","");
        }
        //放入车高
        if(map2.containsKey("车高(mm)")){
          map.put("car_height",map2.get("车高(mm)"));
        }else{
          map.put("car_height","");
        }
        //放入轴距
        if(map2.containsKey("轴距(mm)")){
          map.put("car_wheel_base",map2.get("轴距(mm)"));
        }else{
          map.put("car_wheel_base","");
        }
        //整车重量
        if(map2.containsKey("整备质量(Kg)")){
          map.put("car_weight",map2.get("整备质量(Kg)"));
        }else{
          map.put("car_weight","");
        }
        //放入车身结构
          map.put("car_structure","");
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
        //放入发动机型号
        if(map2.containsKey("发动机厂家型号")){
          map.put("car_engine",map2.get("发动机厂家型号").replace(sLine,"").replace(sCell,"").replace(sBlack,"").trim());
        }else{
          map.put("car_engine","");
        }
        //汽车排量
        if(map2.containsKey("排气量(L)")){
          map.put("car_displacement",map2.get("排气量(L)"));
        }else{
          map.put("car_displacement","");
        }
        //发动机最大功耗
        if(map2.containsKey("发动机最大马力(Ps)")){
          String enginePower = map2.get("发动机最大马力(Ps)").replace(sLine,"").replace(sCell,"").trim();
           if(StringUtils.isNotBlank(enginePower)){
             if(enginePower.contains("马力")){
               map.put("car_engine_power",enginePower);
             }else{
               map.put("car_engine_power",enginePower+"马力");
             }
           }else{
             map.put("car_engine_power","");
           }
        }else{
          map.put("car_engine_power","");
        }
        //汽车燃料
        if(map2.containsKey("燃料类型")){
          String fuelType = map2.get("燃料类型");
          if(fuelType.contains("/")){
            int xFuel = fuelType.indexOf("/");
            map.put("car_fuel",fuelType.substring(0,xFuel));
          }else{
            map.put("car_fuel",map2.get("燃料形式"));
          }
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
        if(map2.containsKey("变速箱")){
          map.put("car_gearbox",map2.get("变速箱"));
        }else{
          map.put("car_gearbox","");
        }
        //汽车驱动方式
        if(map2.containsKey("驱动方式")){
          map.put("car_driver",map2.get("驱动方式"));
        }else{
          map.put("car_driver","");
        }
        //汽车电动天窗
        if(map2.containsKey("天窗类型")){
          String openWindow = map2.get("天窗类型").replace(sLine,"").trim();
          if(StringUtils.isNotBlank(openWindow)){
            map.put("car_electric_sunroof","1");
          }else{
            map.put("car_electric_sunroof","0");
          }
        }else{
          map.put("car_electric_sunroof","0");
        }
        //全景天窗
        map.put("car_open_sunroof","0");
        //座椅材质
        if(map2.containsKey("皮质座椅")){
          String leaterSeat = map2.get("皮质座椅").replace(sLine,"").replace(sCell,"").trim();
          if(StringUtils.isNotBlank(leaterSeat)){
            map.put("car_seat_material","真皮");
          }else{
            map.put("car_seat_material","");
          }
        }else{
          map.put("car_seat_material","");
        }
        //车身稳定控制
        if(map2.containsKey("(ESP/VDC/VSC/DSC等)..")){
          String  control = map2.get("(ESP/VDC/VSC/DSC等)..").replace(sLine,"").replace(sCell,"").trim();
          if(StringUtils.isNotBlank(control)){
            map.put("car_steady_control","1");
          }else{
            map.put("car_steady_control","0");
          }
        }else{
          map.put("car_steady_control","0");
        }
        //大灯材质
        if(map2.containsKey("前雾灯")){
          String headMap=map2.get("前雾灯").replace(sLine,"").replace(sCell,"").replace(sBlack,"").trim();
          if(StringUtils.isNotBlank(headMap)){
            map.put("car_headlamp",headMap);
          }else{
            map.put("car_headlamp","");
          }
        }else{
          map.put("car_headlamp","");
        }
        //gps导航
        if(map2.containsKey("GPS导航系统")){
          String gps = map2.get("GPS导航系统").replace(sLine,"").replace(sCell,"").trim();
          if(StringUtils.isNotBlank(gps)){
            map.put("car_gps","1");
          }else{
            map.put("car_gps","0");
          }
        }else{
          map.put("car_gps","0");
        }
        //定速巡航
        if(map2.containsKey("定速巡航系统")){
          String speed =map2.get("定速巡航系统").replace(sLine,"").replace(sCell,"").trim();
          if(StringUtils.isNotBlank(speed)){
            map.put("car_steady_speed","1");
          }else{
            map.put("car_steady_speed","0");
          }
        }else{
          map.put("car_steady_speed","0");
        }
        //泊车雷达
        if(map2.containsKey("泊车雷达")){
          String radar = map2.get("泊车雷达").replace("/","");
          if(radar.contains("前") && radar.contains("后")){
            int num1 = radar.indexOf("前");
            String front_radar=radar.substring(num1+1,num1+2);
            int num2 = radar.indexOf("后");
            String back_radar=radar.substring(num2+1,num2+2);
            if(sBlack.equals(front_radar) && sBlack.equals(back_radar)){
              map.put("front_radar","1");
              map.put("back_radar","1");
            }else if(sBlack.equals(front_radar) && !sBlack.equals(back_radar)){
              map.put("front_radar","1");
              map.put("back_radar","0");
            }else if(sBlack.equals(back_radar) && !sBlack.equals(front_radar)){
              map.put("front_radar","0");
              map.put("back_radar","1");
            }else{
              map.put("front_radar","0");
              map.put("back_radar","0");
            }
          }else if(radar.contains(sLine)||radar == null){
            map.put("front_radar","0");
            map.put("back_radar","0");
          }
        }else{
          map.put("front_radar","0");
          map.put("back_radar","0");
        }
        //汽车空调
        if(map2.containsKey("手动空调") && map2.containsKey("自动空调") ){
          String aircon1= map2.get("手动空调").replace(sLine,"").replace(sCell,"").trim();
          String aircon2= map2.get("自动空调").replace(sLine,"").replace(sCell,"").trim();
          if(StringUtils.isNotBlank(aircon2)){
            map.put("car_conditioner","自动");
          }else if(StringUtils.isNotBlank(aircon1)){
            map.put("car_conditioner","手动");
          }else{
            map.put("car_conditioner","");
          }
        }else if(map2.containsKey("手动空调") && !map2.containsKey("自动空调")){
          String aircon1= map2.get("手动空调").replace(sLine,"").replace(sCell,"").trim();
          if(StringUtils.isNotBlank(aircon1)){
            map.put("car_conditioner","手动");
          }else{
            map.put("car_conditioner","");
          }
        }else if(map2.containsKey("自动空调") && !map2.containsKey("手动空调")){
          String aircon2= map2.get("自动空调").replace(sLine,"").replace(sCell,"").trim();
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
          String wheel = map2.get("多功能方向盘").replace(sLine,"").replace(sCell,"").trim();
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
        //座位排数
        map.put("car_seat_arrangement","");
        //电动座椅调节
        map.put("car_seat_electric","0");
        //公告型号
        map.put("car_notice","");
        //汽车来源
        map.put("source_come","网易汽车");
        map.put("source_url",currentUrl);
        listmap.add(map);
      }
      page.putField("data", listmap);
    }else{
      page.setSkip(true);
    }
  }
}
