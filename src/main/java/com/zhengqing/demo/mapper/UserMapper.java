package com.zhengqing.demo.mapper;

import com.zhengqing.demo.entity.User;
import com.zhengqing.mybatis.annotations.Param;
import com.zhengqing.mybatis.annotations.Select;

import java.util.List;

/**
 * <p> 用户Mapper </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/4/20 19:06
 */
public interface UserMapper {

    @Select("select * from online_shopping_user where user_id = #{user_id} and name = #{name}")
    List<User> selectList(@Param("user_id") Integer user_id, @Param("name") String name);

}