package com.mljr.operators.controller;

import com.mljr.operators.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by songchi on 16/12/23.
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView("statusCode");
        return mav;
    }


}
