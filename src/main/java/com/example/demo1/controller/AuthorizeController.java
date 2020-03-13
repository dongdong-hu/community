package com.example.demo1.controller;

import com.example.demo1.dto.AccessTokenDTO;
import com.example.demo1.dto.GithubUserDTO;
import com.example.demo1.provider.GithubProvider;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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



     @GetMapping("/callback")
     public String callback(@RequestParam(name = "code") String code,
                            @RequestParam(name = "state") String state){
         System.out.print(code+"\n");
         AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
         accessTokenDTO.setCode(code);
         accessTokenDTO.setState(state);
         accessTokenDTO.setClient_id(clientId);
         accessTokenDTO.setClient_secret(clientSecret);
         accessTokenDTO.setRedirect_uri(redirectUri);
           String accessToken =  githubProvider.getAccessToken(accessTokenDTO);
         GithubUserDTO user = githubProvider.getUser(accessToken);
         System.out.print("\n"+user.getName());
         System.out.println();
         return "index";
     }
}
