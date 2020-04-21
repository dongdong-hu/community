package com.example.demo1.controller;

import com.example.demo1.dto.AccessTokenDTO;
import com.example.demo1.dto.GithubUserDTO;
import com.example.demo1.mapper.UserMapper;
import com.example.demo1.model.User;
import com.example.demo1.provider.GithubProvider;

import com.example.demo1.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
     @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    //@Autowired
   // private UserMapper userMapper;
    @Autowired
    private UserService userService;

     @GetMapping("/callback")
     public String callback(@RequestParam(name = "code") String code,
                            @RequestParam(name = "state") String state,
                            HttpServletRequest request,
                            HttpServletResponse response){
         System.out.print(code+"\n");
         AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
         accessTokenDTO.setCode(code);
         accessTokenDTO.setState(state);
         accessTokenDTO.setClient_id(clientId);
         accessTokenDTO.setClient_secret(clientSecret);
         accessTokenDTO.setRedirect_uri(redirectUri);
           String accessToken =  githubProvider.getAccessToken(accessTokenDTO);
         GithubUserDTO githubUserDTO = githubProvider.getUser(accessToken);
         request.getSession().setAttribute("user",githubUserDTO);

          if (githubUserDTO != null){
              User user = new User();
              //String accountId = UUID.randomUUID().toString();
              user.setAccountId(String.valueOf(githubUserDTO.getId()));
              user.setName(githubUserDTO.getName());
              String token = UUID.randomUUID().toString();
              user.setToken(token);
              user.setGmtCreate(System.currentTimeMillis());
              user.setGmtModified(user.getGmtCreate());
              user.setAvatarUrl(githubUserDTO.getAvatarUrl());
               userService.CreateOrUpdate(user);

              response.addCookie(new Cookie("token",token));


              return "publish" ;
          }
          else{
             return "redirect:/" ;
          }
     }
       @GetMapping("/logout")
       public String logout(HttpServletRequest request,
                            HttpServletResponse response){

         request.getSession().removeAttribute("user");

         Cookie cookie = new Cookie("token",null);

           cookie.setMaxAge(0);
           response.addCookie(cookie);

         return "redirect:/" ;
       }
}
