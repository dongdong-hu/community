package com.example.demo1.controller;

import com.example.demo1.dto.PaginationDTO;
import com.example.demo1.dto.QuestionDTO;
import com.example.demo1.mapper.UserMapper;
import com.example.demo1.model.User;
import com.example.demo1.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
   @Autowired
   private QuestionService service;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name="page" ,defaultValue = "1") Integer page,
                        @RequestParam(name="size" ,defaultValue = "5") Integer size){

          PaginationDTO pagination =  service.getList(page,size);

        model.addAttribute("pagination",pagination);
        return "index";
    }
}
