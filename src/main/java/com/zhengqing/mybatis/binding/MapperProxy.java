package com.zhengqing.mybatis.binding;

import com.zhengqing.mybatis.annotations.Select;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <p> mapper代理 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/4/21 19:57
 */
public class MapperProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Connection connection = getConnection();

        // 拿到sql
        Select select = method.getAnnotation(Select.class);
        String sql = select.value();

        // 构建sql & 执行
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.execute();

        // 拿到结果集
        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
            System.out.println(rs.getString("name") + " -- " + rs.getString("email"));
        }

        // 释放资源
        rs.close();
        ps.close();
        connection.close();
        return null;
    }

    @SneakyThrows
    private static Connection getConnection() {
        // 加载JDBC驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 建立db连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/online_shopping?useUnicode=true&characterEncoding=UTF8&useSSL=false", "root", "root");
        return connection;
    }

}