package com.leeyen.crowd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PortalController {

    @RequestMapping("/")
    public String showPortalPage() {

        // 这里实际开发中需要加载数据……

        return "portal";
    }

}
