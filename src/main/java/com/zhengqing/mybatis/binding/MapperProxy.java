package com.zhengqing.mybatis.binding;

import com.zhengqing.mybatis.annotations.Param;
import com.zhengqing.mybatis.annotations.Select;
import com.zhengqing.mybatis.parsing.GenericTokenParser;
import com.zhengqing.mybatis.parsing.ParameterMappingTokenHandler;
import com.zhengqing.mybatis.parsing.TokenHandler;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Connection connection = getConnection();
        TokenHandler tokenHandler = new ParameterMappingTokenHandler();

        String originalSql = method.getAnnotation(Select.class).value();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", tokenHandler);
        String sql = genericTokenParser.parse(originalSql);
        PreparedStatement ps = connection.prepareStatement(sql);

        Map<String, Object> paramValueMap = new HashMap<>();
        for (int i = 0; i < method.getParameters().length; i++) {
            paramValueMap.put(method.getParameters()[i].getAnnotation(Param.class).value(), args[i]);
        }

        List<String> sqlParameters = tokenHandler.getParameters();
        for (int i = 0; i < sqlParameters.size(); i++) {
            ps.setObject(i + 1, paramValueMap.get(sqlParameters.get(i)));
        }

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
