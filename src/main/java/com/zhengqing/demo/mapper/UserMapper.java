package com.zhengqing.demo.mapper;

import com.zhengqing.demo.entity.User;
import com.zhengqing.mybatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    @Select("select * from online_shopping_user")
    public List<User> select();

}
