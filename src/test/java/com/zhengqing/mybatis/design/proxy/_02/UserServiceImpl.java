package com.zhengqing.mybatis.design.proxy._02;

/**
 * <p> 用户实现类 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/4/21 17:09
 */
public class UserServiceImpl implements UserService {
    @Override
    public Object selectList(String name) {
        System.out.println("执行了select list方法: " + name);
        return "ok";
    }

    @Override
    public Object selectOne(String name) {
        System.out.println("执行了select one方法: " + name);
        return "ok";
    }

}