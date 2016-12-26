package com.mljr.monitor.controller;

import com.mljr.zk.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by songchi on 16/12/23.
 */
@Controller
public class ZkConfigController {
    ZkClient zkClient = ZkUtils.getZkClient();
    @RequestMapping("/zk")
    ModelAndView zkConfig() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("dataList", "ddd");
        return mav;
    }
}
