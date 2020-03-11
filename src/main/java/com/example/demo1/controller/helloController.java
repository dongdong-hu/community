package com.example.demo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class helloController {
    @GetMapping("/hello")
    public  String hello(@RequestParam(name = "name",required = true,defaultValue = "hu") String name,Model model){

   model.containsAttribute(name);
        return "Hello";
    }
}
