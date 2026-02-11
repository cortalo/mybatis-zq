package com.zhengqing.mybatis;

import cn.hutool.json.JSONUtil;
import com.zhengqing.demo.entity.User;
import com.zhengqing.demo.mapper.UserMapper;
import com.zhengqing.mybatis.binding.MapperProxyFactory;
import com.zhengqing.mybatis.builder.XMLConfigBuilder;
import com.zhengqing.mybatis.session.Configuration;
import org.junit.Test;

import java.util.List;

public class TestApp {

    @Test
    public void test() {
        Configuration configuration = new XMLConfigBuilder().parse();
        UserMapper userMapper = MapperProxyFactory.getProxy(UserMapper.class, configuration);
        List<User> users = userMapper.selectByIdAndName(123L, "zhangsan");
        System.out.println(JSONUtil.toJsonStr(users));
    }

}
