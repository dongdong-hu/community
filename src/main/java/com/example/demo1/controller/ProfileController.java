package com.example.demo1.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @GetMapping("/profile/{active}")
    public String question(@PathVariable(name = "active") String active,
                            Model model){
          if("questions".equals(active)){
              model.addAttribute("section","questions");
              model.addAttribute("sectionName","我的提问");


          }
          return "profile";
    }
}
