package com.zhengqing.demo.mapper;

import com.zhengqing.demo.entity.User;
import com.zhengqing.mybatis.annotations.*;

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
    List<User> selectList(@Param("user_id") Long user_id, @Param("name") String name);

    @Select("select * from online_shopping_user where user_id = #{user_id}")
    User selectOne(@Param("user_id") Long user_id);

    @Insert("insert into online_shopping_user(user_type, name, email, address, phone) values(#{user.user_type}, #{user.name}, #{user.email}, #{user.address}, #{user.phone})")
    Integer insert(@Param("user") User user);

    @Delete("delete from online_shopping_user where user_id = #{user_id}")
    Integer delete(@Param("user_id") Long user_id);

    @Update("update online_shopping_user set name = #{name} where user_id = #{user_id}")
    Integer update(@Param("user_id") Long user_id, @Param("name") String name);

}