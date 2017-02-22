package com.mljr.spider.processor;

import com.google.common.collect.Lists;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fulin on 2017/2/18.
 */
public class CarHomeProcessor  extends AbstractPageProcessor {
    //最终爬取数据地址
    private static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";
    //构造函数
    public CarHomeProcessor() {
        super(site);
    }
    //给site赋值
    private static final Site site = Site.me()
            .setDomain("car.autohome.com.cn")
            .setSleepTime(1000)
            .setRetrySleepTime(1000)
            .setCharset("utf-8")
            .setRetryTimes(3)
            .setUserAgent(USER_AGENT);

    @Override
    boolean onProcess(Page page) {
        //获取对应的数据存入到队列中去
        Html html = page.getHtml();
        //先获取表格的行数
        int carNum =  html.xpath("//table[@class='tbset']//tr//td").all().size();
        //用来存放一行的数据
        List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
        for (int i = 1; i <= carNum ; i++) {
            Map<String,String> map = new HashMap<String,String>();
            List<String> destData = Lists.newArrayList();
            String path = String.format("//table[@class='tbcs']//tr[@id]//td[%d]//tidyText()",i);
            List<String> list = html.xpath(path).all();
            //获取车的配置颜色
            String li_num = String.format("//tr[@id='tr_2003']//td[%d]//div//ul//li",i);
            int color_num = html.xpath(li_num).all().size();
            String car_color="";
            String colorPath =String.format("#tr_2003 > td:nth-child(%d) > div > ul > li:nth-child(1) > a",i+1);
            if((!"null".equals(html.css(colorPath,"title"))||( html.css(colorPath,"title")) != null)){
                car_color+=html.css(colorPath,"title")+",";
            }
            for(int j=2;j<=color_num;++j){
                String colorPath2 = String.format("#tr_2003 > td:nth-child(%d) > div > ul > li:nth-child(%d) > span",i+1,j);
                if((!"null".equals(html.css(colorPath2,"title"))||( html.css(colorPath2,"title")) != null)){
                    car_color+=html.css(colorPath2,"title")+",";
                }
            }
            //设置车的颜色
            map.put("car_color",car_color);
            //获取车配置数据
            path = String.format("//div[@id='config_nav']//table//tbody//tr//td[%d]//div[2]//div//a//text()", i);
            //获取车系
            String title = page.getHtml().xpath("//div[@class='path']//a[3]//text()").get();
            //放置车系
            map.put("car_category",title);
            String model= html.xpath(path).get();
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
            //放入车款
            map.put("car_time",carTime);
            //放入车的配置
            map.put("car_config",carHomeConfig);
            //放入厂商指导价
            map.put("car_price",list.get(0).trim().replaceAll("<(.*)>", ""));
            //放入车的品牌
            map.put("car_brand",list.get(3).trim().replaceAll("<(.*)>", ""));
            //放入车厂
            map.put("car_factory",list.get(3).trim().replaceAll("<(.*)>", ""));
            String country = list.get(3).trim().replaceAll("<(.*)>", "");
            //汽车生产国别
            if(country.contains("进口")){
                map.put("car_country","进口");
            }else{
                map.put("car_country","国产");
            }
            //放入车型
            map.put("car_model",list.get(4).trim().replaceAll("<(.*)>", ""));
            //放入车长
            map.put("car_length",list.get(17).trim().replaceAll("<(.*)>", ""));
            //放入车宽
            map.put("car_width",list.get(18).trim().replaceAll("<(.*)>", ""));
            //放入车高
            map.put("car_height",list.get(19).trim().replaceAll("<(.*)>", ""));
            //放入轴距
            map.put("car_wheel_base",list.get(20).trim().replaceAll("<(.*)>", ""));
            //整车重量
            map.put("car_weight",list.get(24).trim().replaceAll("<(.*)>", ""));
            //放入车身结构
            map.put("car_structure",list.get(25).trim().replaceAll("<(.*)>", ""));
            //放入车座数
            map.put("car_seatNum",list.get(27).trim().replaceAll("<(.*)>", ""));
            //放入发动机型号
            map.put("car_engine",list.get(30).trim().replaceAll("<(.*)>", ""));
            //汽车排量
            map.put("car_displacement",list.get(31).trim().replaceAll("<(.*)>", ""));
            //汽车发动机功耗
            map.put("car_engine_power",list.get(40).trim().replaceAll("<(.*)>", ""));
            //汽车燃料
            map.put("car_fuel",list.get(46).trim().replaceAll("<(.*)>", ""));
            //环保标准
            map.put("car_environment",list.get(51).trim().replaceAll("<(.*)>", ""));
            //放入变速箱类型
            map.put("car_gearbox",list.get(54).trim().replaceAll("<(.*)>", ""));
            //汽车驱动方式
            map.put("car_driver",list.get(55).trim().replaceAll("<(.*)>", ""));
            //汽车电动天窗
            if(html.toString().contains("四驱") && html.toString().contains("中央差速器")){
                String sunroof = list.get(95).trim();
                if(sunroof.contains("￥") ||sunroof.contains("免费")||sunroof.contains("暂无价格")){
                    int num = sunroof.indexOf("\n");
                    String subLine = sunroof.substring(0,num+1);
                    if("●".equals(subLine)){
                        map.put("car_electric_sunroof","标配");
                    }else if("○".equals(subLine)){
                        map.put("car_electric_sunroof","选配");
                    }else{
                        map.put("car_electric_sunroof","没有");
                    }
                }else{
                    if("●".equals(sunroof)){
                        map.put("car_electric_sunroof","标配");
                    }else if("○".equals(sunroof)){
                        map.put("car_electric_sunroof","选配");
                    }else{
                        map.put("car_electric_sunroof","没有");
                    }
                }
            }else{
                String sunroof = list.get(93).trim();
                if(sunroof.contains("￥") ||sunroof.contains("免费")||sunroof.contains("暂无价格")){
                    int num = sunroof.indexOf("\n");
                    String subLine = sunroof.substring(0,num+1);
                    if("●".equals(subLine)){
                        map.put("car_electric_sunroof","标配");
                    }else if("○".equals(subLine)){
                        map.put("car_electric_sunroof","选配");
                    }else{
                        map.put("car_electric_sunroof","没有");
                    }
                }else{
                    if("●".equals(sunroof)){
                        map.put("car_electric_sunroof","标配");
                    }else if("○".equals(sunroof)){
                        map.put("car_electric_sunroof","选配");
                    }else{
                        map.put("car_electric_sunroof","没有");
                    }
                }
            }

            //汽车全景天窗
            if(html.toString().contains("四驱") && html.toString().contains("中央差速器")){
                String openroof = list.get(96).trim();
                if(openroof.contains("￥") ||openroof.contains("免费")||openroof.contains("暂无价格")){
                    int num = openroof.indexOf("\n");
                    String subLine = openroof.substring(0,num+1);
                    if("●".equals(subLine)){
                        map.put("car_open_sunroof","标配");
                    }else if("○".equals(subLine)){
                        map.put("car_open_sunroof","选配");
                    }else{
                        map.put("car_open_sunroof","没有");
                    }
                }else{
                    if("●".equals(openroof)){
                        map.put("car_open_sunroof","标配");
                    }else if("○".equals(openroof)){
                        map.put("car_open_sunroof","选配");
                    }else{
                        map.put("car_open_sunroof","没有");
                    }
                }
            }else{
                String openroof = list.get(94).trim();
                if(openroof.contains("￥") ||openroof.contains("免费")||openroof.contains("暂无价格")){
                    int num = openroof.indexOf("\n");
                    String subLine = openroof.substring(0,num+1);
                    if("●".equals(subLine)){
                        map.put("car_open_sunroof","标配");
                    }else if("○".equals(subLine)){
                        map.put("car_open_sunroof","选配");
                    }else{
                        map.put("car_open_sunroof","没有");
                    }
                }else{
                    if("●".equals(openroof)){
                        map.put("car_open_sunroof","标配");
                    }else if("○".equals(openroof)){
                        map.put("car_open_sunroof","选配");
                    }else{
                        map.put("car_open_sunroof","没有");
                    }
                }
            }

           //座椅材质
            if(html.toString().contains("四驱") && html.toString().contains("中央差速器")){
                map.put("car_seat_material",list.get(117).trim());
            }else{
                map.put("car_seat_material",list.get(115).trim());
            }

            //车身稳定控制
            if(html.toString().contains("四驱") && html.toString().contains("中央差速器")){
                String  control = list.get(85).trim();
                if("●".equals(control)){
                    map.put("car_steady_control","标配");
                }else{
                    map.put("car_steady_control","没有");
                }
            }else{
                String  control = list.get(83).trim();
                if("●".equals(control)){
                    map.put("car_steady_control","标配");
                }else{
                    map.put("car_steady_control","没有");
                }
            }

            //大灯
            if(html.toString().contains("四驱") && html.toString().contains("中央差速器")){
                map.put("car_headlamp",list.get(147).trim());
            }else{
                map.put("car_headlamp",list.get(145).trim());
            }

            //gps导航
            if(html.toString().contains("四驱") && html.toString().contains("中央差速器")){
                String gps = list.get(134).trim();
                if("●".equals(gps)){
                    map.put("car_gps","标配");
                }else if("○".equals(gps)){
                    map.put("car_gps","选配");
                }else{
                    map.put("car_gps","没有");
                }
            }else{
                String gps = list.get(132).trim();
                if("●".equals(gps)){
                    map.put("car_gps","标配");
                }else if("○".equals(gps)){
                    map.put("car_gps","选配");
                }else{
                    map.put("car_gps","没有");
                }
            }

            //定速巡航
            if(html.toString().contains("四驱") && html.toString().contains("中央差速器")){
                String speed =list.get(111).trim();
                if("●".equals(speed)){
                    map.put("car_steady_speed","标配");
                }else{
                    map.put("car_steady_speed","没有");
                }
            }else{
                String speed =list.get(109).trim();
                if("●".equals(speed)){
                    map.put("car_steady_speed","标配");
                }else{
                    map.put("car_steady_speed","没有");
                }
            }
            //倒车雷达
            if(html.toString().contains("四驱") && html.toString().contains("中央差速器")){
                String radar = list.get(112).trim().replace("/","");
                if(radar.contains("前")&&radar.contains("后")){
                    int num1 = radar.indexOf("前");
                    String front_radar=radar.substring(num1+1,num1+2);
                    int num2 = radar.indexOf("后");
                    String back_radar=radar.substring(num2+1,num2+2);
                    if("●".equals(front_radar) && "●".equals(back_radar)){
                        map.put("car_radar","前有后有");
                    }else if("●".equals(front_radar) && !"●".equals(back_radar)){
                        map.put("car_radar","前有后无");
                    }else if("●".equals(back_radar) && !"●".equals(front_radar)){
                        map.put("car_radar","前无后有");
                    }
                }else if(radar.contains("-")||radar == null){
                    map.put("car_radar","前无后无");
                }
            }else{
                String radar = list.get(110).trim().replace("/","");
                if(radar.contains("前")&&radar.contains("后")){
                    int num1 = radar.indexOf("前");
                    String front_radar=radar.substring(num1+1,num1+2);
                    int num2 = radar.indexOf("后");
                    String back_radar=radar.substring(num2+1,num2+2);
                    if("●".equals(front_radar) && "●".equals(back_radar)){
                        map.put("car_radar","前有后有");
                    }else if("●".equals(front_radar) && !"●".equals(back_radar)){
                        map.put("car_radar","前有后无");
                    }else if("●".equals(back_radar) && !"●".equals(front_radar)){
                        map.put("car_radar","前无后有");
                    }
                }else if(radar.contains("-")||radar == null){
                    map.put("car_radar","前无后无");
                }

            }
            //汽车空调
            if(html.toString().contains("四驱") && html.toString().contains("中央差速器")){
                String aircon= list.get(171).trim().replace("-","").replace("●","");
                if(aircon != null){
                    map.put("car_conditioner",aircon);
                }else{
                    map.put("car_conditioner","没有");
                }
            }else{
                String aircon= list.get(169).trim().replace("-","").replace("●","");
                if(aircon != null){
                    map.put("car_conditioner",aircon);
                }else{
                    map.put("car_conditioner","没有");
                }
            }

            //多功能方向盘
            if(html.toString().contains("四驱") && html.toString().contains("中央差速器")){
                String wheel = list.get(107).trim();
                if("●".equals(wheel)){
                    map.put("car_wheel","标配");
                }else{
                    map.put("car_wheel","没有");
                }
            }else{
                String wheel = list.get(105).trim();
                if("●".equals(wheel)){
                    map.put("car_wheel","标配");
                }else{
                    map.put("car_wheel","没有");
                }
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
            map.put("car_seat_electric","不是标配");
            //公告型号
            map.put("car_notice","暂无说明");
            listmap.add(map);
        }
        page.putField("data", listmap);
        return true;
    }

}
