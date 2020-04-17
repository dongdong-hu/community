package com.example.demo1.controller;

import com.example.demo1.dto.QuestionDTO;
import com.example.demo1.mapper.QuestionMapper;
import com.example.demo1.mapper.UserMapper;
import com.example.demo1.model.Question;
import com.example.demo1.model.User;
import com.example.demo1.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {



    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish")
    public  String publish(){

        return  "publish";
    }

    @PostMapping("/publish")
    public  String doPublish(@RequestParam(value = "title", required = false) String title,
                             @RequestParam(value = "description", required = false) String description,
                             @RequestParam(value = "tag" , required = false) String tag,
                             HttpServletRequest request,
                             Model model){

        User user = (User) request.getSession().getAttribute("user");

        if (user==null){
            model.addAttribute("error","用户未登陆！");
            return "publish";
        }

        Question question = new Question();

       if (StringUtils.isBlank(title)){
           model.addAttribute("error","标题不能为空");
           return "publish";
       }
        if (StringUtils.isBlank(description)){
            model.addAttribute("error","描述不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(tag)){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(user.getId());

        questionMapper.insertQuestion(question);

     return "redirect:/";

    }

    @GetMapping("/publish/{id}")
    public String getPublish(@PathVariable(name = "id") Long id,
                             Model model){

        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question" ,questionDTO );
        return "/publish";
    }
}
