package cn.edu.zjut.observermode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: 张坤
 * @DateTime: 2022/4/3 16:20
 * @Description: 该类用于 配置socket请求和转发
 */
@Controller
public class SocketController {
    @Autowired
    private WebSocketServer webSocketServer;
    @RequestMapping("/index")
    public String index() {
        return "index";
    }
    @GetMapping("/webSocket")
    public ModelAndView socket() {
        ModelAndView mav=new ModelAndView("/webSocket");
        return mav;
    }
}
