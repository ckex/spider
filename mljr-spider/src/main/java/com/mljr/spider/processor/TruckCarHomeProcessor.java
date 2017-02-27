package com.mljr.spider.processor;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Html;

import java.util.*;

/**
 * Created by fulin on 2017/2/23.
 */
public class TruckCarHomeProcessor  extends AbstractPageProcessor {
    //首地址
    public final static String START_URL ="http://product.360che.com/";
    //品牌地址
    public final static String BRAND_URL_REGEX = "http://product.360che.com/b[0-9]{1,6}/";
    //配置地址
    public final static String CONFIG_URL_REGEXS="http://product.360che.com/s[0-9]{1,6}/[0-9]{1,7}_[0-9]{1,7}_param.html";
    //配置地址
    public final static String CONFIG_URL_REGEXM="http://product.360che.com/m[0-9]{1,6}/[0-9]{1,7}_param.html";
    //http://product.360che.com/m76/19067_param.html
    //最终爬取数据地址
    private static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";
    //给site赋值
    private static final Site site = Site.me()
            .setDomain("product.360che.com")
            .setSleepTime(500)
            .setRetrySleepTime(500)
            .setCharset("utf-8")
            .setRetryTimes(3)
            .setUserAgent(USER_AGENT);

    public  TruckCarHomeProcessor(){
        super(site);
    }
    @Override
    boolean onProcess(Page page) {
        String currentUrl = page.getUrl().get();
        //处理首页地址，获取品牌地址
        System.out.println("看看我是啥："+currentUrl);
        if (currentUrl.equals(START_URL)) {
            //不处理页面
            page.setSkip(true);
            List<String> picUrls = page.getHtml().links().regex(BRAND_URL_REGEX ).all();
            page.addTargetRequests(picUrls);
        }else if(currentUrl.matches(BRAND_URL_REGEX)){
            //不处理页面
            page.setSkip(true);
            List<String> peizhiUrls = page.getHtml().links().regex(CONFIG_URL_REGEXS).all();
            Set<String> set = Sets.newHashSet();
            for (String peizhiUrl : peizhiUrls) {
                set.add(peizhiUrl);
            }
            List<String> peizhiUrlm = page.getHtml().links().regex(CONFIG_URL_REGEXM).all();
            for (String peizhiUrl : peizhiUrlm) {
                set.add(peizhiUrl);
            }
            List<String> listMap = Lists.newArrayList();
            for (String s : set) {
                listMap.add(s);
            }
            page.addTargetRequests(listMap);
        }else{
            processOneCar(page);
        }
        return true;
    }
    public  void  processOneCar(Page page){
        //获取对应的数据存入到队列中去
        Html html = page.getHtml();
        //先获取表格的行数
        int carNum = html.xpath("//div[@class='parameter-detail']//table//thead//tr//th").all().size();
        //用来存放一行的数据
        List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
        List<String> list_name = html.xpath("//div[@class='parameter-detail']//table//tbody//tr[@class='param-row']//td[1]/tidyText()").all();
        for (int i = 2; i <= carNum; i++) {
            Map<String, String> map = new HashMap<String, String>();
            Map<String, String> map2 = new HashMap<String, String>();
            String path = String.format("//div[@class='parameter-detail']//table//tbody//tr[@class='param-row']//td[%d]//div//tidyText()", i);
            List<String> list = html.xpath(path).all();
            for (int j = 0; j <list.size() ; j++) {
                map2.put(list_name.get(j).replace("：","").trim(),list.get(j).trim());
            }
            //获取厂商指导价
            String headPath2 = String.format("//div[@class='parameter-detail']//table//thead//tr[2]//td[%d]//text()", i);
            String price = html.xpath(headPath2).get().trim();
            //放置厂商指导价
            map.put("car_price",price);
            //获取车的品牌，车系，车型
            String brand = html.xpath("//div[@class='inner']//a[3]//text()").get().trim();
            map.put("car_brand",brand);
            String carCatory  = html.xpath("//div[@class='inner']//a[4]//text()").get().trim();
            map.put("car_category",carCatory);
            String[] carModel= html.xpath("//div[@class='inner']//a[5]//text()").get().split(" ");
            map.put("car_model",carModel[carModel.length-1]);
            //放置公告型号
            String s1=map2.get("公告型号").trim();
            map.put("car_notice",s1);
            //驱动方式
            String s2 = map2.get("驱动形式").trim();
            map.put("car_driver",s2);
            //放置轴距
            String s3 = map2.get("轴距").trim();
            map.put("car_wheel_base",s3);
            //放置车身长度
            if(map2.containsKey("车身长度")){
                map.put("car_length",map2.get("车身长度").trim());
            }else if(map2.containsKey("长度")){
                map.put("car_length",map2.get("长度").trim());
            }else{
                map.put("car_length","");
            }
            //放置车身宽度
            if(map2.containsKey("车身宽度")){
                map.put("car_width",map2.get("车身宽度").trim());
            }else if(map2.containsKey("宽度")){
                map.put("car_width",map2.get("宽度").trim());
            }else{
                map.put("car_width","");
            }
            //放置车身高度
            if(map2.containsKey("车身高度")){
                map.put("car_height",map2.get("车身高度").trim());
            }else if(map2.containsKey("高度")){
                map.put("car_height",map2.get("高度").trim());
            }else{
                map.put("car_height","");
            }
            //放置整车质量
            map.put("car_weight",map2.get("整车重量").trim());
            //放入厂商信息
            map.put("car_factory",brand);
            //放入国别
            map.put("car_country",map2.get("产地").trim());
            //放入吨位级别
            map.put("car_level",map2.get("吨位级别"));
            //放入发动机
            map.put("car_engine",map2.get("发动机").trim());
            //放入燃料种类
            map.put("car_fuel",map2.get("燃料种类").trim());
            //放入排量
            map.put("car_displacement",map2.get("排量").trim());
            //放入环保放标准
            map.put("car_environment",map2.get("排放标准").trim());
            //发动机功率
            map.put("car_engine_power",map2.get("最大马力").trim());
            //放入座椅排放形式
            map.put("car_seat_arrangement",map2.get("座位排数").trim());
            //放置变速箱形式
            map.put("car_gearbox",map2.get("变速箱").trim());
            //放置车身结构
            if(map2.containsKey("货箱形式")){
                map.put("car_structure",map2.get("货箱形式").trim());
            }else{
                map.put("car_structure","");
            }
            //放置货箱长
            if(map2.containsKey("货箱长度")){
                map.put("car_carriage_length",map2.get("货箱长度").trim());
            }else if(map2.containsKey("车厢最大深度")){
                map.put("car_carriage_length",map2.get("车厢最大深度").trim());
            }else{
                map.put("car_carriage_length","");
            }
            //货箱宽
            if(map2.containsKey("货箱宽度")){
                map.put("car_carriage_width",map2.get("货箱宽度").trim());
            }else if(map2.containsKey("车厢最大宽度")){
                map.put("car_carriage_width",map2.get("车厢最大宽度").trim());
            }else{
                map.put("car_carriage_width","");
            }
            //货箱高
            if(map2.containsKey("货箱高度")){
                map.put("car_carriage_height",map2.get("货箱高度").trim());
            }else if(map2.containsKey("车厢高度")){
                map.put("car_carriage_height",map2.get("车厢高度").trim());
            }else{
                map.put("car_carriage_height","");
            }
            //放置车座数
            if(map2.containsKey("车座数")){
                map.put("car_seatNum",map2.get("车座数").trim());
            }else{
                map.put("car_seatNum","");
            }
            //放置电动天窗
            if(map2.containsKey("电动天窗")){
                String electricSun = map2.get("电动天窗").replace("-","").replace("○","").trim();
                  if(StringUtils.isNotBlank(electricSun)){
                      map.put("car_electric_sunroof","1");
                  }else{
                      map.put("car_electric_sunroof","0");
                  }
            }else{
                map.put("car_electric_sunroof","0");
            }
            //放置全景天窗
            if(map2.containsKey("全景天窗")){
                String openSun = map2.get("全景天窗").replace("-","").replace("○","").trim();
                if(StringUtils.isNotBlank(openSun)){
                    map.put("car_open_sunroof","1");
                }else{
                    map.put("car_open_sunroof","0");
                }
            }else{
                map.put("car_open_sunroof","0");
            }
            //放置gps导航
            if(map2.containsKey("GPS/北斗行车记录仪")){
                String gps = map2.get("GPS/北斗行车记录仪").replace("-","").replace("○","").trim();
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
                String steadySpeed = map2.get("定速巡航").replace("-","").replace("○","").trim();
                if(StringUtils.isNotBlank(steadySpeed )){
                    map.put("car_steady_speed","1");
                }else{
                    map.put("car_steady_speed","0");
                }
            }else{
                map.put("car_steady_speed","0");
            }
            //倒车雷达
            if(map2.containsKey("倒车影像/倒车雷达")){
                String carRar= map2.get("倒车影像/倒车雷达").replace("-","").replace("○","").trim();
                if(StringUtils.isNotBlank(carRar)){
                    map.put("car_radar","1");
                }else{
                    map.put("car_radar","0");
                }

            }else{
                map.put("car_radar","0");
            }
            //汽车空调
            if(map2.containsKey("空调调节形式")){
                map.put("car_conditioner",map2.get("空调调节形式").replace("-","").replace("○","").trim());
            }else{
                map.put("car_conditioner","");
            }
            //座椅材质
            if(map2.containsKey("座椅材质")){
                map.put("car_seat_material",map2.get("座椅材质").trim());
            }else{
                map.put("car_seat_material","");
            }
            //多功能方向盘
            if(map2.containsKey("多功能方向盘")){
                String wheel = map2.get("多功能方向盘").replace("-","").replace("○","").trim();
                if(StringUtils.isNotBlank(wheel)){
                    map.put("car_wheel",map2.get("1"));
                }else{
                    map.put("car_wheel",map2.get("0"));
                }
            }else{
                map.put("car_wheel","0");
            }
            //车身稳定控制
            if(map2.containsKey("车身稳定控制(ESP/DSC/VSC等)")){
                String SteadyConrol = map2.get("车身稳定控制(ESP/DSC/VSC等)").replace("○","").trim();
                if(StringUtils.isNotBlank(SteadyConrol)){
                    map.put("car_steady_control",map2.get("1"));
                }else{
                    map.put("car_steady_control","0");
                }
            }else{
                map.put("car_steady_control","0");
            }
            //大灯
            if(map2.containsKey("氙气大灯")){
                String headLamp = map2.get("氙气大灯").trim().replace("-","");
                if(StringUtils.isNotBlank(headLamp)){
                    map.put("car_headlamp",map2.get("氙气大灯"));
                }else{
                    map.put("car_headlamp","");
                }
            }else{
                map.put("car_headlamp","");
            }
            //放入车款
            map.put("car_time","");
            //放入配置
            map.put("car_config","");
            //放入车身颜色
            map.put("car_color","");
            //设置电动座椅调节
            map.put("car_seat_electric","0");
            listmap.add(map);
        }
        page.putField("data", listmap);
    }

}
