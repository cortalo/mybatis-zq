package com.zhengqing.demo.mapper;

import com.zhengqing.demo.entity.User;
import com.zhengqing.mybatis.annotations.Param;
import com.zhengqing.mybatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    @Select("select * from online_shopping_user where user_id = #{user_id}")
    public List<User> selectById(@Param("user_id") Long user_id);

    @Select("select * from online_shopping_user where user_id = #{user_id} and name = #{name}")
    public List<User> selectByIdAndName(@Param("user_id") Long user_id, @Param("name") String name);

}
