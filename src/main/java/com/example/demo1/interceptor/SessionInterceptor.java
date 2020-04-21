package com.example.demo1.interceptor;

import com.example.demo1.mapper.UserMapper;
import com.example.demo1.model.User;
import com.example.demo1.model.UserExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;

    private Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("拦截。。。");
        Cookie[] cookies =  request.getCookies();
        for (Cookie cookie: cookies ) {
            if (cookie.getName().equals("token")){
                String token =  cookie.getValue();
                UserExample example = new UserExample();
                example.createCriteria().andTokenEqualTo(token);
                List<User> list = userMapper.selectByExample(example);
                User user = list.get(0);
               //User user = userMapper.getUser(token);

                if(user != null){
                    request.getSession().setAttribute("user",user);

                }
                break;
            }

        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
