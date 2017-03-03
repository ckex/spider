package com.mljr.spider.processor;

import org.apache.commons.lang.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String sourceUrl = page.getUrl().get();
        //获取对应的数据存入到队列中去
        Html html = page.getHtml();
        //先获取表格的行数
        int carNum =  html.xpath("//table[@class='tbset']//tr//td").all().size();
        //用来存放一行的数据
        List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
        List<String> list_name = html.xpath("//table[@id='tab_side']//tr[@id]//th//div//tidyText()").all();
        for (int i = 1; i <= carNum ; i++) {
            Map<String,String> map = new HashMap<String,String>();
            Map<String, String> map2 = new HashMap<String, String>();
            String path = String.format("//table[@class='tbcs']//tr[@id]//td[%d]//div//tidyText()",i);
            List<String> list = html.xpath(path).all();
            //将每一列数据对应起来
            for (int j = 0; j <list.size() ; j++) {
                map2.put(list_name.get(j).replaceAll("<(.*)>", "").replace("\n","").trim(),list.get(j).replaceAll("<(.*)>", "").replace("\n","").trim());
            }
            //获取车的配置颜色
            String li_num = String.format("//tr[@id='tr_2003']//td[%d]//div//ul//li",i);
            int color_num = html.xpath(li_num).all().size();
            String car_color="";
            String colorPath =String.format("#tr_2003 > td:nth-child(%d) > div > ul > li:nth-child(1) > a",i+1);
            String color_name = html.css(colorPath,"title").toString();
            if(StringUtils.isNotBlank(color_name)){
                car_color+=color_name+",";
            }
            for(int j=2;j<=color_num;++j){
                String colorPath2 = String.format("#tr_2003 > td:nth-child(%d) > div > ul > li:nth-child(%d) > span",i+1,j);
               String color_names = html.css(colorPath2,"title").toString();
                if(StringUtils.isNotBlank(color_names)){
                    car_color+=color_names+",";
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
            map.put("unique_car_brand",model.trim());
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
            //放入车款
            map.put("car_time",carTime);
            //放入车的配置
            map.put("car_config",carHomeConfig);

            //放入厂商指导价
            if(map2.containsKey("厂商指导价")){
                map.put("car_price",map2.get("厂商指导价"));
            }else{
                map.put("car_price","");
            }
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
            if(map2.containsKey("厂商")){
                String country = map2.get("厂商");
                //汽车生产国别
                if(country.contains("进口")){
                    map.put("car_country","进口");
                }else{
                    map.put("car_country","国产");
                }
            }else{
                map.put("car_country","");
            }
            //放入车型
            if(map2.containsKey("级别")){
                map.put("car_model",map2.get("级别"));
            }else{
                map.put("car_model","");
            }
            //放入车长
            if(map2.containsKey("长度(mm)")){
                map.put("car_length",map2.get("长度(mm)"));
            }else{
                map.put("car_length","");
            }
            //放入车宽
            if(map2.containsKey("宽度(mm)")){
                map.put("car_width",map2.get("宽度(mm)"));
            }else{
                map.put("car_width","");
            }
            //放入车高
            if(map2.containsKey("高度(mm)")){
                map.put("car_height",map2.get("高度(mm)"));
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
            if(map2.containsKey("整备质量(kg)")){
                map.put("car_weight",map2.get("整备质量(kg)"));
            }else{
                map.put("car_weight","");
            }
            //放入车身结构
            if(map2.containsKey("车身结构")){
                map.put("car_structure",map2.get("车身结构"));
            }else{
                map.put("car_structure","");
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
            //放入发动机型号
            if(map2.containsKey("发动机型号")){
                map.put("car_engine",map2.get("发动机型号").replace("-","").replace("○","").replace("●",""));
            }else{
                map.put("car_engine","");
            }
            //汽车排量
            if(map2.containsKey("排量(mL)")){
                map.put("car_displacement",map2.get("排量(mL)"));
            }else{
                map.put("car_displacement","");
            }
            //汽车发动机功耗
            if(map2.containsKey("最大马力(Ps)")){
                String mali = map2.get("最大马力(Ps)");
                if(StringUtils.isBlank(mali)){
                    //先去表头抓
                    String reg = "[0-9]{2,4}马力";
                    Pattern pattern = Pattern.compile(reg);
                    String enginePower = "";
                    for(int j = 0; j <carHome.length ; j++){
                        if(carHome[j].contains("马力")){
                            Matcher matcher = pattern.matcher(carHome[j]);
                            while(matcher.find()){
                                enginePower=matcher.group();
                            }
                        }
                    }
                    //再去发动机或则电动机抓
                    if(StringUtils.isNotBlank(enginePower)){
                        map.put("car_engine_power",enginePower);
                    }else{
                        if(map2.containsKey("发动机")){
                            String[]  ssL = map2.get("发动机").split(" ");
                            String sPower="";
                            for(int j = 0; j <ssL.length ; j++){
                                if(ssL[j].contains("马力")){
                                    Matcher matcher = pattern.matcher(ssL[j]);
                                    while(matcher.find()){
                                        sPower=matcher.group();
                                    }
                                }
                            }
                            map.put("car_engine_power",sPower);
                        }else if(map2.containsKey("电动机")){
                            String[]  ssL = map2.get("电动机").split(" ");
                            String sPower="";
                            for(int j = 0; j <ssL.length ; j++){
                                if(ssL[j].contains("马力")){
                                    Matcher matcher = pattern.matcher(ssL[j]);
                                    while(matcher.find()){
                                        sPower=matcher.group();
                                    }
                                }
                            }
                            map.put("car_engine_power",sPower);
                        }else{
                            map.put("car_engine_power","");
                        }
                    }
                }else{
                    if(mali.contains("马力")){
                        map.put("car_engine_power",mali);
                    }else{
                        map.put("car_engine_power",mali+"马力");
                    }
                }
            }else{
                //先去表头抓
                String reg = "[0-9]{2,4}马力";
                Pattern pattern = Pattern.compile(reg);
                String enginePower = "";
                for(int j = 0; j <carHome.length ; j++){
                    if(carHome[j].contains("马力")){
                        Matcher matcher = pattern.matcher(carHome[j]);
                        while(matcher.find()){
                            enginePower=matcher.group();
                        }
                    }
                }
                //再去发动机或则电动机抓
                if(StringUtils.isNotBlank(enginePower)){
                    map.put("car_engine_power",enginePower);
                }else{
                    if(map2.containsKey("发动机")){
                        String[]  ssL = map2.get("发动机").split(" ");
                        String sPower="";
                        for(int j = 0; j <ssL.length ; j++){
                            if(ssL[j].contains("马力")){
                                Matcher matcher = pattern.matcher(ssL[j]);
                                while(matcher.find()){
                                    sPower=matcher.group();
                                }
                            }
                        }
                        map.put("car_engine_power",sPower);
                    }else if(map2.containsKey("电动机")){
                        String[]  ssL = map2.get("电动机").split(" ");
                        String sPower="";
                        for(int j = 0; j <ssL.length ; j++){
                            if(ssL[j].contains("马力")){
                                Matcher matcher = pattern.matcher(ssL[j]);
                                while(matcher.find()){
                                    sPower=matcher.group();
                                }
                            }
                        }
                        map.put("car_engine_power",sPower);
                    }else{
                        map.put("car_engine_power","");
                    }
                }
            }
            //汽车燃料
            if(map2.containsKey("燃料形式")){
                map.put("car_fuel",map2.get("燃料形式"));
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
            if(map2.containsKey("变速箱类型")){
                map.put("car_gearbox",map2.get("变速箱类型"));
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
            if(map2.containsKey("电动天窗")){
                   String sunroof = map2.get("电动天窗");
                   if(sunroof.contains("￥") ||sunroof.contains("免费")||sunroof.contains("暂无价格")){
                       int num = sunroof.indexOf("\n");
                       String subLine = sunroof.substring(0,num+1);
                       if("●".equals(subLine)){
                           map.put("car_electric_sunroof","1");
                       }else{
                           map.put("car_electric_sunroof","0");
                       }
                   }else{
                       if("●".equals(sunroof)){
                           map.put("car_electric_sunroof","1");
                       }else{
                           map.put("car_electric_sunroof","0");
                       }
                   }
               }else{
                map.put("car_electric_sunroof","0");
            }

            //汽车全景天窗
                if(map2.containsKey("全景天窗")){
                    String openroof = map2.get("全景天窗");
                    if(openroof.contains("￥") ||openroof.contains("免费")||openroof.contains("暂无价格")){
                        int num = openroof.indexOf("\n");
                        String subLine = openroof.substring(0,num+1);
                        if("●".equals(subLine)){
                            map.put("car_open_sunroof","1");
                        }else{
                            map.put("car_open_sunroof","0");
                        }
                    }else{
                        if("●".equals(openroof)){
                            map.put("car_open_sunroof","1");
                        }else{
                            map.put("car_open_sunroof","0");
                        }
                    }
                }else {
                    map.put("car_open_sunroof","0");
                }

           //座椅材质
            if(map2.containsKey("座椅材质")){
                String seatMaterial = map2.get("座椅材质").replace("-","").replace("○","").replace("●","");
                if(seatMaterial.contains("￥") ||seatMaterial.contains("免费")||seatMaterial.contains("暂无价格")){
                    int num = seatMaterial.indexOf("\n");
                    String subLine = seatMaterial.substring(0,num+1);
                    map.put("car_seat_material",subLine);
                }else{
                    map.put("car_seat_material",seatMaterial);
                }
            }else{
                map.put("car_seat_material","");
            }
            //车身稳定控制
            if(map2.containsKey("车身稳定控制(ESC/ESP/DSC等)")){
                String  control = map2.get("车身稳定控制(ESC/ESP/DSC等)");
                if("●".equals(control)){
                    map.put("car_steady_control","1");
                }else{
                    map.put("car_steady_control","0");
                }
            }else{
                map.put("car_steady_control","0");
            }
            //大灯
            if(map2.containsKey("远光灯")){
                String headMap=map2.get("远光灯").replace("-","").replace("○","").replace("●","");
                if(headMap.contains("￥") ||headMap.contains("免费")||headMap.contains("暂无价格")){
                    int num = headMap.indexOf("\n");
                    String subLine = headMap.substring(0,num+1);
                    map.put("car_headlamp",subLine);
                }else{
                    map.put("car_headlamp",headMap);
                }
            }else{
                map.put("car_headlamp","");
            }
            //gps导航
            if(map2.containsKey("GPS导航系统")){
                String gps = map2.get("GPS导航系统");
                if("●".equals(gps)){
                    map.put("car_gps","1");
                }else{
                    map.put("car_gps","0");
                }
            }else{
                map.put("car_gps","0");
            }
            //定速巡航
            if(map2.containsKey("定速巡航")){
                String speed =map2.get("定速巡航");
                if("●".equals(speed)){
                    map.put("car_steady_speed","1");
                }else{
                    map.put("car_steady_speed","0");
                }
            }else{
                map.put("car_steady_speed","0");
            }
            //倒车雷达
            if(map2.containsKey("前/后驻车雷达")){
                String radar = map2.get("前/后驻车雷达").replace("/","");
                if(radar.contains("￥") ||radar.contains("免费")||radar.contains("暂无价格")){
                    int num = radar.indexOf("\n");
                    String subLine = radar.substring(0,num+1);
                    //开始判断前后雷达安装情况
                    if(subLine.contains("前") && subLine.contains("后")){
                        int num1 = subLine.indexOf("前");
                        String front_radar=subLine.substring(num1+1,num1+2);
                        int num2 = subLine.indexOf("后");
                        String back_radar=subLine.substring(num2+1,num2+2);
                        if("●".equals(front_radar) && "●".equals(back_radar)){
                            map.put("front_radar","1");
                            map.put("back_radar","1");
                        }else if("●".equals(front_radar) && !"●".equals(back_radar)){
                            map.put("front_radar","1");
                            map.put("back_radar","0");
                        }else if("●".equals(back_radar) && !"●".equals(front_radar)){
                            map.put("front_radar","0");
                            map.put("back_radar","1");
                        }else{
                            map.put("front_radar","0");
                            map.put("back_radar","0");
                        }
                    }else if(radar.contains("-")||radar == null){
                        map.put("front_radar","0");
                        map.put("back_radar","0");
                    }
                }else{
                    if(radar.contains("前") && radar.contains("后")){
                        int num1 = radar.indexOf("前");
                        String front_radar=radar.substring(num1+1,num1+2);
                        int num2 = radar.indexOf("后");
                        String back_radar=radar.substring(num2+1,num2+2);
                        if("●".equals(front_radar) && "●".equals(back_radar)){
                            map.put("front_radar","1");
                            map.put("back_radar","1");
                        }else if("●".equals(front_radar) && !"●".equals(back_radar)){
                            map.put("front_radar","1");
                            map.put("back_radar","0");
                        }else if("●".equals(back_radar) && !"●".equals(front_radar)){
                            map.put("front_radar","0");
                            map.put("back_radar","1");
                        }else{
                            map.put("front_radar","0");
                            map.put("back_radar","0");
                        }
                    }else if(radar.contains("-")||radar == null){
                        map.put("front_radar","0");
                        map.put("back_radar","0");
                    }
                }
            }else{
                map.put("front_radar","0");
                map.put("back_radar","0");
            }
            //汽车空调
            if(map2.containsKey("空调控制方式")){
                String aircon= map2.get("空调控制方式").replace("-","").replace("○","").replace("●","");
                if(aircon != null){
                    map.put("car_conditioner",aircon);
                }else{
                    map.put("car_conditioner","");
                }
            }else{
                map.put("car_conditioner","");
            }
            //多功能方向盘
            if(map2.containsKey("多功能方向盘")){
                String wheel = map2.get("多功能方向盘");
                if(wheel.contains("￥") ||wheel.contains("免费")||wheel.contains("暂无价格")){
                    int num = wheel.indexOf("\n");
                    String subLine = wheel.substring(0,num+1);
                    if("●".equals(subLine)){
                        map.put("car_wheel","1");
                    }else{
                        map.put("car_wheel","0");
                    }
                }else{
                    if("●".equals(wheel)){
                        map.put("car_wheel","1");
                    }else{
                        map.put("car_wheel","0");
                    }
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
            map.put("source_come","汽车之家");
            map.put("source_url",sourceUrl);
            listmap.add(map);
        }
        page.putField("data", listmap);
        return true;
    }

}
