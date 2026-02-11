package com.zhengqing.demo.mapper;

import com.zhengqing.demo.entity.User;
import com.zhengqing.mybatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    @Select("select * from online_shopping_user where user_id = #{user_id}")
    public List<User> selectById(Long user_id);

    @Select("select * from online_shopping_user where user_id = #{user_id} and name = #{name}")
    public List<User> selectByIdAndName(Long user_id, String name);

}
