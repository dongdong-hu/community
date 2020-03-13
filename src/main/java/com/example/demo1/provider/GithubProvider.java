package com.example.demo1.provider;

import com.alibaba.fastjson.JSON;
import com.example.demo1.dto.AccessTokenDTO;
import com.example.demo1.dto.GithubUserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {


    public String getAccessToken(AccessTokenDTO accessTokenDTO){

         MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        System.out.print(accessTokenDTO+"\n");
        OkHttpClient client = new OkHttpClient();

       // RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.print(string);
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
          //  log.error("getAccessToken error,{}", accessTokenDTO, e);
        }
        return null;
    }
    public GithubUserDTO getUser(String accessToken){
         OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String string =  response.body().string();
            GithubUserDTO githubUserDTO = JSON.parseObject(string, GithubUserDTO.class);
            return  githubUserDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;

    }
}
