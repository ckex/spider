package com.mljr.operators.handler;

import com.mljr.operators.common.constant.OperatorsEnum;
import com.mljr.operators.common.constant.ProvinceEnum;
import com.mljr.operators.handler.chinamobile.DefaultChinaMobileHandler;
import com.mljr.operators.handler.chinaunicom.DefaultChinaUnicomHandler;

/**
 * @author gaoxi
 * @time 2017/3/1
 */
public class AcquisitionHandlerFactory {

  public IAcquisitionHandler create(OperatorsEnum operators, ProvinceEnum province) {
    IAcquisitionHandler handler = null;
    switch (operators) {
      case CHINAUNICOM:
        handler = new DefaultChinaUnicomHandler();
        break;
      case CHINAMOBILE:
        handler = new DefaultChinaMobileHandler();
        break;
    }
    return handler;
  }
}
