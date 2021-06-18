package com.salvoproyect.salvo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SalvoController {

    @RequestMapping("/web")
    public String inicio(){
        return "games";
    }
}
