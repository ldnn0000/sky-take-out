package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;

@Mapper
public interface UserMapper {
    @Select("select * from user where openid = #{openid}")
    User getUserByOpenid(String openid);
    
    void insertUser(User user);
}
