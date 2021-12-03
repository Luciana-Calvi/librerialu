package com.act1.libreriaLu.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class libreriaController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

//    @GetMapping("/login")
//    public String login() {
//        return "login.html";
//    }
//
//    @GetMapping("/registro")
//    public String regitro() {
//        return "registro.html";
//    }
}
