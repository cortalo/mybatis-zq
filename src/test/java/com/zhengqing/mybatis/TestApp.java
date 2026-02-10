package com.zhengqing.mybatis;

import com.zhengqing.demo.entity.User;
import com.zhengqing.demo.mapper.UserMapper;
import com.zhengqing.mybatis.binding.MapperProxyFactory;
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
        UserMapper userMapper = MapperProxyFactory.getProxy(UserMapper.class);
        List<User> userList = userMapper.selectList(123, "zhangsan");
        System.out.println(userList);

//        System.out.println(userMapper.selectOne(1));
    }

}