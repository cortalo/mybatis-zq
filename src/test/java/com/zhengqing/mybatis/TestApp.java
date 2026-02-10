package com.zhengqing.mybatis;

import cn.hutool.json.JSONUtil;
import com.zhengqing.demo.entity.User;
import com.zhengqing.demo.mapper.UserMapper;
import com.zhengqing.mybatis.binding.MapperProxyFactory;
import com.zhengqing.mybatis.builder.XMLConfigBuilder;
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
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        xmlConfigBuilder.parse();

        UserMapper userMapper = MapperProxyFactory.getProxy(UserMapper.class);
        List<User> userList = userMapper.selectList(125L, "wangwu");
        System.out.println(JSONUtil.toJsonStr(userList));

//        System.out.println(userMapper.selectOne(1));
    }

}