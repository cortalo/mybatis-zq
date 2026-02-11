package com.zhengqing.mybatis;

import cn.hutool.json.JSONUtil;
import com.zhengqing.demo.entity.User;
import com.zhengqing.demo.mapper.UserMapper;
import com.zhengqing.mybatis.binding.MapperProxyFactory;
import org.junit.Test;

import java.util.List;

public class TestApp {

    @Test
    public void test() {
        UserMapper userMapper = MapperProxyFactory.getProxy(UserMapper.class);
        List<User> users = userMapper.selectByIdAndName(123L, "zhangsan");
        System.out.println(JSONUtil.toJsonStr(users));
    }

}
