package com.mljr.sync.service;

import com.google.gson.Gson;
import com.mljr.rabbitmq.RabbitmqClient;
import com.mljr.spider.dao.CarBodyInfoDao;
import com.mljr.spider.model.CarBodyInfoDo;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by fulin on 2017/2/20.
 */
@Service
public class CarHomeNetService {
  protected static transient Logger logger = LoggerFactory.getLogger(CarHomeNetService.class);
  // 队列的名称
  private final static String QUEUE_NAME = "autohome_result";
  // 将数据存入到数据库中去
  @Autowired
  CarBodyInfoDao carBodyInfoDao;

  // 消耗队列中的消息
  public void consume() throws Exception {
    final Channel channel = RabbitmqClient.newChannel();
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

    } catch (Exception e) {
      logger.error("CarHomeNetService error: " + ", " + ExceptionUtils.getStackTrace(e));
    } finally {
      if (channel != null) {
        channel.close();
      }
    }

  }

  // 将解析后的数据导入到数据库
  public void writeToDb(String carHtml) {
    CarBodyInfoDo car = new CarBodyInfoDo();
    Gson gson = new Gson();
    List<Map<String, String>> list = gson.fromJson(carHtml, List.class);
    for (int i = 0; i < list.size(); i++) {
      Map<String, String> map = list.get(i);
      car.setVehicleBrand(map.get("car_brand"));
      car.setVehicleModelYear(map.get("car_time"));
      car.setVehicleCategory(map.get("car_category"));
      car.setVehicleFactory(map.get("car_factory"));
      car.setOriginCountry(map.get("car_country"));
      car.setDisplacement(map.get("car_displacement"));
      car.setEnvironmentalStandard(map.get("car_environment"));
      car.setFuelType(map.get("car_fuel"));
      car.setEngineModel(map.get("car_engine"));
      car.setEngineHorsePower(map.get("car_engine_power") + "马力");
      car.setDriveModel(map.get("car_driver"));
      car.setGearbox(map.get("car_gearbox"));
      car.setSeatNumber(map.get("car_seatNum") + "座");
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
      car.setParkDistanceControl(map.get("car_radar"));
      car.setAutomaticAirConditioner(map.get("car_conditioner"));
      car.setMultifunctionSteerWheel(map.get("car_wheel"));
      car.setSeatArrangement(map.get("car_seat_arrangement"));
      car.setCreateTime(new Date());
      car.setUpdateTime(new Date());
      car = carBodyInfoDao.create(car);
    }
  }

}
