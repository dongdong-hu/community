package com.example.demo1.service;

import com.example.demo1.dto.PaginationDTO;
import com.example.demo1.dto.QuestionDTO;
import com.example.demo1.mapper.QuestionMapper;
import com.example.demo1.mapper.UserMapper;
import com.example.demo1.model.Question;
import com.example.demo1.model.QuestionExample;
import com.example.demo1.model.User;
import com.example.demo1.model.UserExample;
import org.apache.ibatis.session.RowBounds;
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
        QuestionExample example = new QuestionExample();
        Integer totalCount =  (int)questionMapper.countByExample(example);
        paginationDTO.setPagination(totalCount,page,size);
        if(page < 1){
            page = 1;

        }
        if (page> paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        Integer offset = size * (page -1);
        //QuestionExample example1 = new QuestionExample();

        List<Question> list = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(),new RowBounds(offset,size));//list(offset,size);
        paginationDTO.setPage(page);
        for (Question question:
             list) {
           int a =  question.getCreator();
            UserExample uExample = new UserExample();
            uExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> userList =    userMapper.selectByExample(uExample);
            User user = userList.get(0);
            //User user =  userMapper.queryById(question.getCreator());
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
        QuestionExample qExample = new QuestionExample();
        qExample.createCriteria().andCreatorEqualTo(userID);
        Integer totalCount =  (int)questionMapper.countByExample(qExample);
       // Integer totalCount =  questionMapper.getCountByid(userID);
        paginationDTO.setPagination(totalCount,page,size);
        if(page < 1){
            page = 1;

        }
        if (page> paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        Integer offset = size * (page -1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userID);
        List<Question> list = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample,new RowBounds(offset,size));//list(offset,size);

        //List<Question> list = questionMapper.listByid(userID,offset,size);
        paginationDTO.setPage(page);
        for (Question question:
                list) {
            int a =  question.getCreator();
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(question.getCreator());
            List<User> userList =  userMapper.selectByExample(example);
            User user = userList.get(0);
           // User user =  userMapper.queryById(question.getCreator());
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

    public QuestionDTO getById(Integer id) {
        Question question =  questionMapper.selectByPrimaryKey(id);
       // Question question  = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
         BeanUtils.copyProperties(question,questionDTO);
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(question.getCreator());
        List<User> list =  userMapper.selectByExample(example);
        User user = list.get(0);
       // User user =  userMapper.queryById(question.getCreator());
        questionDTO.setUser(user);

        return questionDTO;
    }

    public void createOrUpdate(Question question) {
      Question dbQuestion =   questionMapper.selectByPrimaryKey(question.getId());
      // Question dbQuestion =  questionMapper.getById(question.getId());
       if(dbQuestion != null){
           question.setGmtModified(System.currentTimeMillis());

           QuestionExample example = new QuestionExample();
           example.createCriteria().andIdEqualTo(question.getId());
           Question updateQuestion = new Question();
           updateQuestion.setGmtCreate(dbQuestion.getGmtCreate());
           updateQuestion.setGmtModified(System.currentTimeMillis());
           updateQuestion.setTag(question.getTag());
           updateQuestion.setDescription(question.getDescription());
           updateQuestion.setTitle(question.getTitle());
           questionMapper.updateByExampleSelective(updateQuestion, example);
           //questionMapper.update(question);
       }else{
            questionMapper.insertSelective(question);
          // questionMapper.insertQuestion(question);
        }
    }
}
