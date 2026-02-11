package com.zhengqing.mybatis.binding;

import com.zhengqing.mybatis.annotations.Select;
import com.zhengqing.mybatis.parsing.GenericTokenParser;
import com.zhengqing.mybatis.parsing.ParameterMappingTokenHandler;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MapperProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Connection connection = getConnection();

        String originalSql = method.getAnnotation(Select.class).value();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}",
                new ParameterMappingTokenHandler());
        String sql = genericTokenParser.parse(originalSql);
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, (Long) args[0]);
        ps.setString(2, (String) args[1]);
        ps.execute();
        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
            System.out.println(rs.getString("name"));
        }
        rs.close();
        ps.close();
        connection.close();
        return null;
    }

    @SneakyThrows
    private static Connection getConnection() {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/online_shopping?useUnicode=true&characterEncoding=UTF8&useSSL=false",
                "root", "root");
        return connection;
    }
}
