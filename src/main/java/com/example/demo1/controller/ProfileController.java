package com.example.demo1.controller;

import com.example.demo1.dto.PaginationDTO;
import com.example.demo1.mapper.UserMapper;
import com.example.demo1.model.User;
import com.example.demo1.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService service;

    @GetMapping("/profile/{active}")
    public String question(HttpServletRequest request,
                           @PathVariable(name = "active") String active,
                           Model model,
                           @RequestParam(name="page" ,defaultValue = "1") Integer page,
                           @RequestParam(name="size" ,defaultValue = "2") Integer size){

        User user = (User) request.getSession().getAttribute("user");

        PaginationDTO pagination =  service.getListByid(user.getId(),page,size);

        model.addAttribute("pagination",pagination);
          if("questions".equals(active)){
              model.addAttribute("section","questions");
              model.addAttribute("sectionName","我的提问");
          }else if("replies".equals(active)){
              model.addAttribute("section","replies");
              model.addAttribute("sectionName","最新回复");
        }
          return "profile";
    }
}
