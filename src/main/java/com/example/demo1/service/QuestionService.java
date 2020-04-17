package com.example.demo1.service;

import com.example.demo1.dto.PaginationDTO;
import com.example.demo1.dto.QuestionDTO;
import com.example.demo1.mapper.QuestionMapper;
import com.example.demo1.mapper.UserMapper;
import com.example.demo1.model.Question;
import com.example.demo1.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO getList(Integer page,Integer size) {

        List<QuestionDTO> questions = new ArrayList<QuestionDTO>();
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount =  questionMapper.getCount();
        paginationDTO.setPagination(totalCount,page,size);
        if(page < 1){
            page = 1;

        }
        if (page> paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        Integer offset = size * (page -1);
        List<Question> list = questionMapper.list(offset,size);
        paginationDTO.setPage(page);
        for (Question question:
             list) {
           int a =  question.getCreator();
           User user =  userMapper.queryById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);

            questions.add(questionDTO);

        }
        paginationDTO.setQuestions(questions);
        //总数；

          paginationDTO.setPages(paginationDTO.getPages());
        return paginationDTO;
    }

    public PaginationDTO getListByid(Integer userID,Integer page,Integer size){

        List<QuestionDTO> questions = new ArrayList<QuestionDTO>();
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount =  questionMapper.getCountByid(userID);
        paginationDTO.setPagination(totalCount,page,size);
        if(page < 1){
            page = 1;

        }
        if (page> paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        Integer offset = size * (page -1);
        List<Question> list = questionMapper.listByid(userID,offset,size);
        paginationDTO.setPage(page);
        for (Question question:
                list) {
            int a =  question.getCreator();
            User user =  userMapper.queryById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);

            questions.add(questionDTO);

        }
        paginationDTO.setQuestions(questions);
        //总数；

        paginationDTO.setPages(paginationDTO.getPages());
        return paginationDTO;

    }

    public QuestionDTO getById(Long id) {
        Question question  = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
         BeanUtils.copyProperties(question,questionDTO);
        User user =  userMapper.queryById(question.getCreator());
        questionDTO.setUser(user);

        return questionDTO;
    }
}
