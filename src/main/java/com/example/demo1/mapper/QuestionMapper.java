package com.example.demo1.mapper;

import com.example.demo1.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.ClientInfoStatus;
import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void insertQuestion(Question question);
    @Select("select * from question")
    List<Question> queryAll();

    @Select("select * from question limit #{offset} ,#{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
    @Select("select * from question where  creator = #{userId} limit #{offset} ,#{size}")
    List<Question> listByid(@Param(value = "userId") Integer userId, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer getCount();

    @Select("select count(1) from question where creator = #{userId}")
    Integer getCountByid(@Param("userId") Integer userId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Long id);
   @Select("update question q  set q.title = #{title},q.description = #{description} ,q.tag = #{tag} , q.gmt_modified = #{gmtModified} where q.id = #{id}" )
    void update(Question question);
}
