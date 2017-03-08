package com.mljr.sync.service;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.mljr.rabbitmq.RabbitmqClient;
import com.mljr.redis.RedisClient;
import com.mljr.spider.dao.CarBodyInfoDao;
import com.mljr.spider.model.CarBodyInfoDo;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fulin on 2017/2/20.
 */
@Service
public class CarHomeNetService {
  protected static transient Logger logger = LoggerFactory.getLogger(CarHomeNetService.class);
  // 队列的名称
  private final static String QUEUE_NAME = "car_test";
  // 将数据存入到数据库中去
  @Autowired
  CarBodyInfoDao carBodyInfoDao;

  @Autowired
  private RedisClient redisClient;

  // 消耗队列中的消息
  public void consume() throws Exception {
    final Channel channel = RabbitmqClient.newChannel();
    try {
      while (true) {
        try {
          // 每次从队列中拿取一条数据
          GetResponse response = RabbitmqClient.pollMessage(channel, QUEUE_NAME, false);
          if (response == null) {
            logger.debug("qid=" + QUEUE_NAME + " queue is empty.waitting message");
            return;
          }
          String message = new String(response.getBody(), "UTF-8");
          writeToDb(message);
          channel.basicAck(response.getEnvelope().getDeliveryTag(), false);
        } catch (Throwable e) {
          logger.error("CarHomeNetService error: " + ", " + ExceptionUtils.getStackTrace(e));
        }
      }
    } finally {
      if (channel != null) {
        channel.close();
      }
    }

  }

  // 将解析后的数据导入到数据库
  public void writeToDb(String carHtml) {
    CarBodyInfoDo car = new CarBodyInfoDo();
    CarBodyInfoDo car2 = new CarBodyInfoDo();
    Gson gson = new Gson();
    List<Map<String, String>> list = gson.fromJson(carHtml, List.class);
    redisClient.use(new Function<Jedis, Long>() {

      @Override
      public Long apply(Jedis input) {
        return input.incrBy("car-home-count", list.size());
      }

    });
    for (int i = 0; i < list.size(); i++) {
      Map<String, String> map = list.get(i);
      car.setUniqueCarBrand(map.get("unique_car_brand"));
      car.setVehicleBrand(map.get("car_brand"));
      car.setVehicleModelYear(map.get("car_time"));
      car.setVehicleCategory(map.get("car_category"));
      car.setVehicleFactory(map.get("car_factory"));
      car.setOriginCountry(map.get("car_country"));
      car.setDisplacement(map.get("car_displacement"));
      car.setEnvironmentalStandard(map.get("car_environment"));
      car.setFuelType(map.get("car_fuel"));
      car.setEngineModel(map.get("car_engine"));
      car.setEngineHorsePower(map.get("car_engine_power"));
      car.setDriveModel(map.get("car_driver"));
      car.setGearbox(map.get("car_gearbox"));
      car.setSeatNumber(map.get("car_seatNum"));
      car.setConfigure(map.get("car_config"));
      car.setExteriorColor(map.get("car_color"));
      car.setOfficialGuidePrice(map.get("car_price"));
      car.setAnnouncementType(map.get("car_notice"));
      car.setWholeCurbWeight(map.get("car_weight"));
      car.setWheelBase(map.get("car_wheel_base"));
      car.setCarBodyLength(map.get("car_length"));
      car.setCarBodyWidth(map.get("car_width"));
      car.setCarBodyHeight(map.get("car_height"));
      car.setCarriageLength(map.get("car_carriage_length"));
      car.setCarriageWidth(map.get("car_carriage_width"));
      car.setCarriageHeight(map.get("car_carriage_height"));
      car.setTonnageLevel(map.get("car_level"));
      car.setVehicleBodyStructure(map.get("car_structure"));
      car.setVehicleModel(map.get("car_model"));
      car.setSunroof(map.get("car_electric_sunroof"));
      car.setOpenSunRoof(map.get("car_open_sunroof"));
      car.setElectricAdjustSeat(map.get("car_seat_electric"));
      car.setLeatherSeat(map.get("car_seat_material"));
      car.setCarBodyStedyControl(map.get("car_steady_control"));
      car.setHeadlampType(map.get("car_headlamp"));
      car.setNavigationSystem(map.get("car_gps"));
      car.setCruiseControl(map.get("car_steady_speed"));
      car.setFrontRadar(map.get("front_radar"));
      car.setBackRadar(map.get("back_radar"));
      car.setAutomaticAirConditioner(map.get("car_conditioner"));
      car.setMultifunctionSteerWheel(map.get("car_wheel"));
      car.setSeatArrangement(map.get("car_seat_arrangement"));
      String url = map.get("source_url");
      car.setSourceCome(Joiner.on("@").join(map.get("source_come"), StringUtils.isBlank(url) ? "" : url));
      car.setOfficialDisplacement(map.get("official_displacement"));
      car.setCreateTime(new Date());
      car.setUpdateTime(new Date());
      // 如果已经存在就更新，否者插入
      Stopwatch watch = Stopwatch.createStarted();
      car2 = carBodyInfoDao.load(car.getUniqueCarBrand());
      watch.stop();
      long loadtime = 0, createtime = 0, updatetime = 0;
      loadtime = watch.elapsed(TimeUnit.MILLISECONDS);
      watch = Stopwatch.createStarted();
      if (car2 != null) {
       // car2 = carBodyInfoDao.create(car);
        updataCarInfo(car,car2);
      }else{
        //没有找到还分下面的情况。
        String newheader = "";
        String headerBrand = car.getUniqueCarBrand();
        String  gearBox = car.getGearbox();
        if(headerBrand.contains("动")){
           newheader=getUniqueBrand(headerBrand,"");
        }else{
           newheader=getUniqueBrand(headerBrand,gearBox);
        }
        if(StringUtils.isNotBlank(newheader)){
          car2 = carBodyInfoDao.load(newheader);
          if(car2 !=null){
             updataCarInfo(car,car2);
          }else{
            car2=carBodyInfoDao.create(car);
          }
        }else{
          car2=carBodyInfoDao.create(car);
        }
        watch.stop();
        updatetime = watch.elapsed(TimeUnit.MILLISECONDS);
        logger.info("updaet car info. "+car.getUniqueCarBrand()+" <> " + car2.toString());
      }
      if (loadtime > 100 || createtime > 100 || updatetime > 100) {
        logger.debug("to long loadtime=" + loadtime + ", createtime=" + createtime + ", updatetime=" + updatetime);
      }
    }
  }
  //三个数返回一个范围
  public  String  getRangeprice(String num1,String num2 ,String  num3){
      String rangePrice = "";
      int  rt = num1.compareTo(num2);
       if(rt<0){
           int rtone = num1.compareTo(num3);
           if(rtone>0){
               rangePrice = num3+"万~"+num2+"万";
           }else{
             int rttwo = num2.compareTo(num3);
             if(rttwo>0){
               rangePrice = num1+"万~"+num2+"万";
             }else{
               rangePrice = num1+"万~"+num3+"万";
             }
           }
       }else{
         int rtone = num2.compareTo(num3);
         if(rtone>0){
           rangePrice = num3+"万~"+num1+"万";
         }else{
           int rttwo = num3.compareTo(num1);
           if(rttwo>0){
             rangePrice = num2+"万~"+num3+"万";
           }else{
             rangePrice = num2+"万~"+num1+"万";
           }
         }
       }

    return rangePrice ;
  }
  //两个数返回一个范围
  public  String  getRangeprice2(String num1, String num2){
    int rt = num1.compareTo(num2);
    String rangePrice = "";
    if (rt < 0) {
      rangePrice = num1+"万~"+num2+"万";
    }else{
      rangePrice = num2+"万~"+num1+"万";
    }
    return rangePrice;
  }
  //对表表头进行处理
  public  String getUniqueBrand(String header,String gerbox){
    String act = "";
    if(StringUtils.isNotBlank(gerbox)){
        if(gerbox.contains("AT")||gerbox.contains("CVT")||gerbox.contains("DCT")||gerbox.contains("AMT")){
              act ="自动";
        }else {
              act="手动";
        }
    }
    String[] carHome =header.split(" ");
    String header_brand = " ";
    Pattern p = Pattern.compile("[a-zA-z]{1,}");
    Pattern pp = Pattern.compile("[a-zA-z]{2,}");
    for (int i = 0; i <carHome.length ; i++) {
      if(carHome[i].contains("型") && !p.matcher(carHome[i]).find()){
        if(!carHome[i].contains("动")){
          header_brand+=act+carHome[i]+" ";
        }else{
          if(carHome[i].contains("自动")){
            header_brand+=carHome[i].replace("自动","").trim()+" ";
          }else if(carHome[i].contains("手动")){
            header_brand+=carHome[i].replace("手动","").trim()+" ";
          }
        }

      }else if(carHome[i].contains("型") && pp.matcher(carHome[i]).find()){
        Matcher matcher = pp.matcher(carHome[i]);
        while(matcher.find()){
          String ss = matcher.group();
          String ss2 = carHome[i].substring(ss.length(),carHome[i].length());
          header_brand+=ss+" "+ss2+" ";
        }
      }else{
        header_brand+=carHome[i]+" ";
      }

    }
    return header_brand.trim();
  }
  //更新数据
  public  void updataCarInfo(CarBodyInfoDo car,CarBodyInfoDo car2){
    //更新价格
    String price1 = car.getOfficialGuidePrice();
    String price2 = car2.getOfficialGuidePrice();
    if(StringUtils.isNotBlank(price2)){
      if(price2.contains("暂无报价")){
        if(StringUtils.isNotBlank(price1)){
          car2.setOfficialGuidePrice(price1);
        }
      }else{
        if(StringUtils.isNotBlank(price1)){
          String result =null;
          if(price2.contains("~")){
            //比较三个数
            String[] ss = price2.split("~");
            if(ss.length==2){
              result = getRangeprice(ss[0].replace("万","").trim(),ss[1].replace("万","").trim(),price1.replace("万","").trim());
            }else if(ss.length==1){
              result = getRangeprice2(ss[0].replace("万","").trim(),price1.replace("万","").trim());
            }
            car2.setOfficialGuidePrice(result);
          }else{
            //比较两个数
            result = getRangeprice2(price2.replace("万","").trim(),price1.replace("万","").trim());
            car2.setOfficialGuidePrice(result);
          }
          car2.setOfficialGuidePrice(price1);
        }
      }
    }else{
      car2.setOfficialGuidePrice(price1);
    }
    //更新国别
    if(StringUtils.isNotBlank(car.getOriginCountry())){
      car2.setOriginCountry(car.getOriginCountry());
    }
    //更新排放
    if(StringUtils.isBlank(car2.getDisplacement().replace("-",""))){
      if(StringUtils.isNotBlank(car.getDisplacement().replace("--",""))){
        car2.setDisplacement(car.getDisplacement());
      }
    }
    //更新整车质量
    if(StringUtils.isBlank(car2.getWholeCurbWeight().replace("-",""))){
      if(StringUtils.isNotBlank(car.getWholeCurbWeight().replace("--",""))){
        car2.setWholeCurbWeight(car.getWholeCurbWeight());
      }
    }
    //更新车长，
    if(StringUtils.isBlank(car2.getCarBodyLength().replace("-",""))){
      if(StringUtils.isNotBlank(car.getCarBodyLength())){
        car2.setCarBodyLength(car.getCarBodyLength());
      }
    }
    //更新车宽
    if(StringUtils.isBlank(car2.getCarBodyWidth().replace("-",""))){
      if(StringUtils.isNotBlank(car.getCarBodyWidth())){
        car2.setCarBodyWidth(car.getCarBodyWidth());
      }
    }
    //更新车高
    if(StringUtils.isBlank(car2.getCarBodyHeight().replace("-",""))){
      if(StringUtils.isNotBlank(car.getCarBodyHeight())){
        car2.setCarBodyHeight(car.getCarBodyHeight());
      }
    }
    //更新车型
    if(StringUtils.isBlank(car2.getVehicleModel())){
      if(StringUtils.isNotBlank(car.getVehicleModel())){
        car2.setVehicleModel(car.getVehicleModel());
      }
    }
    //更新官方排放标准
    if(StringUtils.isBlank(car2.getOfficialDisplacement())){
      if(StringUtils.isNotBlank(car.getOfficialDisplacement())){
        car2.setOfficialDisplacement(car.getOfficialDisplacement());
      }
    }
    //更新环保标准
    if(StringUtils.isBlank(car2.getEnvironmentalStandard())){
      if(StringUtils.isNotBlank(car.getEnvironmentalStandard())){
        car2.setEnvironmentalStandard(car.getEnvironmentalStandard());
      }
    }
    //更新燃料类型
    if(StringUtils.isBlank(car2.getFuelType())){
      if(StringUtils.isNotBlank(car.getFuelType())){
        car2.setFuelType(car.getFuelType());
      }
    }
    //更新发动机
    if(StringUtils.isBlank(car2.getEngineModel())){
      if(StringUtils.isNotBlank(car.getEngineModel())){
        car2.setEngineModel(car.getEngineModel());
      }
    }
    //更新马力
    if(StringUtils.isBlank(car2.getEngineHorsePower())){
      if(StringUtils.isNotBlank(car.getEngineModel())){
        car2.setEngineModel(car.getEngineModel());
      }
    }
    //更新驱动
    if(StringUtils.isBlank(car2.getDriveModel())){
      if(StringUtils.isNotBlank(car.getDriveModel())){
        car2.setDriveModel(car.getDriveModel());
      }
    }
    //更新变速箱
    if(StringUtils.isBlank(car2.getGearbox())){
      if(StringUtils.isNotBlank(car.getGearbox())){
        car2.setGearbox(car.getGearbox());
      }
    }
    //更新车座
    if(StringUtils.isBlank(car2.getSeatNumber())){
      if(StringUtils.isNotBlank(car.getSeatNumber())){
        car2.setSeatNumber(car.getSeatNumber());
      }
    }
    //更新配置
    if(StringUtils.isBlank(car2.getConfigure())){
      if(StringUtils.isNotBlank(car.getConfigure())){
        car2.setConfigure(car.getConfigure());
      }
    }
    //更新车颜色
    if(StringUtils.isBlank(car2.getExteriorColor())){
      if(StringUtils.isNotBlank(car.getExteriorColor())){
        car2.setExteriorColor(car.getExteriorColor());
      }
    }
    //更新公告型号
    if(StringUtils.isBlank(car2.getAnnouncementType())){
      if(StringUtils.isNotBlank(car.getAnnouncementType())){
        car2.setAnnouncementType(car.getAnnouncementType());
      }
    }
    //更新轴距
    if(StringUtils.isBlank(car2.getWheelBase().replace("-",""))){
      if(StringUtils.isNotBlank(car.getWheelBase())){
        car2.setWheelBase(car.getWheelBase());
      }
    }
    //更新货箱长
    if(StringUtils.isBlank(car2.getCarriageLength().replace("-",""))){
      if(StringUtils.isNotBlank(car.getCarriageLength())){
        car2.setCarriageLength(car.getCarriageLength());
      }
    }
    //更新货箱宽
    if(StringUtils.isBlank(car2.getCarriageWidth().replace("-",""))){
      if(StringUtils.isNotBlank(car.getCarriageWidth())){
        car2.setCarriageWidth(car.getCarriageWidth());
      }
    }
    //更新货箱高
    if(StringUtils.isBlank(car2.getCarriageHeight().replace("-",""))){
      if(StringUtils.isNotBlank(car.getCarriageHeight())){
        car2.setCarriageHeight(car.getCarriageHeight());
      }
    }
    //更新座位排数
    if(StringUtils.isBlank(car2.getSeatArrangement().replace("-",""))){
      if(StringUtils.isNotBlank(car.getSeatArrangement())){
        car2.setSeatArrangement(car.getSeatArrangement());
      }
    }
    //更新吨位级别
    if(StringUtils.isBlank(car2.getTonnageLevel())){
      if(StringUtils.isNotBlank(car.getTonnageLevel())){
        car2.setTonnageLevel(car.getTonnageLevel());
      }
    }
    //更新车身结构
    if(StringUtils.isBlank(car2.getVehicleBodyStructure())){
      if(StringUtils.isNotBlank(car.getVehicleBodyStructure())){
        car2.setVehicleBodyStructure(car.getVehicleBodyStructure());
      }
    }
    //更新电动天窗
    if("0".equals(car2.getSunroof())){
      if("1".equals(car.getSunroof())){
        car2.setSunroof(car.getVehicleBodyStructure());
      }
    }
    //更新全景天窗
    if("0".equals(car2.getOpenSunRoof())){
      if("1".equals(car.getOpenSunRoof())){
        car2.setOpenSunRoof(car.getOpenSunRoof());
      }
    }
    //更新电动座椅调节
    if("0".equals(car2.getElectricAdjustSeat())){
      if("1".equals(car.getElectricAdjustSeat())){
        car2.setElectricAdjustSeat(car.getElectricAdjustSeat());
      }
    }
    //更新车身稳定控制
    if("0".equals(car2.getCarBodyStedyControl())){
      if("1".equals(car.getCarBodyStedyControl())){
        car2.setCarBodyStedyControl(car.getCarBodyStedyControl());
      }
    }
    //更新座椅材质
    if(StringUtils.isBlank(car2.getLeatherSeat())){
      if(StringUtils.isNotBlank(car.getLeatherSeat())){
        car2.setLeatherSeat(car.getLeatherSeat());
      }
    }
    //更新大灯材料
    if(StringUtils.isBlank(car2.getHeadlampType())){
      if(StringUtils.isNotBlank(car.getHeadlampType())){
        car2.setHeadlampType(car.getHeadlampType());
      }
    }
    //更新导航
    if("0".equals(car2.getNavigationSystem())){
      if("1".equals(car.getNavigationSystem())){
        car2.setNavigationSystem(car.getNavigationSystem());
      }
    }
    //更新定速巡航
    if("0".equals(car2.getCruiseControl())){
      if("1".equals(car.getCruiseControl())){
        car2.setCruiseControl(car.getCruiseControl());
      }
    }
    //跟新前置雷达
    if("0".equals(car2.getFrontRadar())){
      if("1".equals(car.getFrontRadar())){
        car2.setFrontRadar(car.getFrontRadar());
      }
    }
    //更新后置雷达
    if("0".equals(car2.getBackRadar())){
      if("1".equals(car.getBackRadar())){
        car2.setBackRadar(car.getBackRadar());
      }
    }
    //更新空调控制形式
    if(StringUtils.isBlank(car2.getAutomaticAirConditioner())){
      if(StringUtils.isNotBlank(car.getAutomaticAirConditioner())){
        car2.setAutomaticAirConditioner(car.getAutomaticAirConditioner());
      }
    }
    //更新多功能方向盘
    if("0".equals(car2.getMultifunctionSteerWheel())){
      if("1".equals(car.getMultifunctionSteerWheel())){
        car2.setMultifunctionSteerWheel(car.getMultifunctionSteerWheel());
      }
    }
    //更新时间
    car2.setUpdateTime(new Date());
    carBodyInfoDao.update(car2);
  }

}
