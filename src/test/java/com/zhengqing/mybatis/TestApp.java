package com.zhengqing.mybatis;

import cn.hutool.json.JSONUtil;
import com.zhengqing.demo.entity.User;
import com.zhengqing.demo.mapper.UserMapper;
import com.zhengqing.mybatis.builder.XMLConfigBuilder;
import com.zhengqing.mybatis.session.Configuration;
import com.zhengqing.mybatis.session.SqlSessionFactory;
import com.zhengqing.mybatis.session.SqlSessionFactoryBuilder;
import com.zhengqing.mybatis.session.defaults.DefaultSqlSession;
import com.zhengqing.mybatis.session.SqlSession;
import com.zhengqing.mybatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * <p> 测试 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/4/20 19:22
 */
public class TestApp {

    @Test
    public void test() throws Exception {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        List<User> userList = userMapper.selectList(125L, "wangwu");
        System.out.println(JSONUtil.toJsonStr(userList));

        User user = userMapper.selectOne(125L);
        System.out.println(JSONUtil.toJsonStr(user));

//        System.out.println(userMapper.selectOne(1));
    }

}