package com.zhengqing.mybatis;

import com.zhengqing.demo.mapper.UserMapper;
import com.zhengqing.mybatis.binding.MapperProxyFactory;
import org.junit.Test;

public class TestApp {

    @Test
    public void test() {
        UserMapper userMapper = MapperProxyFactory.getProxy(UserMapper.class);
        userMapper.selectByIdAndName(123L, "zhangsan");
    }

}
