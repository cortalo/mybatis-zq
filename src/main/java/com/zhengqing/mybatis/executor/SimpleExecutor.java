package com.zhengqing.mybatis.executor;

import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.Lists;
import com.zhengqing.mybatis.annotations.Param;
import com.zhengqing.mybatis.mapping.MappedStatement;
import com.zhengqing.mybatis.session.Configuration;
import com.zhengqing.mybatis.type.TypeHandler;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleExecutor implements Executor {

    private Configuration configuration;

    public SimpleExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @SneakyThrows
    @Override
    public <T> List<T> query(MappedStatement ms, Object param) {

        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(ms.getSql());
        Map<Class<?>, TypeHandler> typeHandlerMap = configuration.getTypeHandlerMap();
        Map<String, Object> paramValueMap = (Map<String, Object>) param;

        List<String> sqlParameters = ms.getSqlParameters();
        for (int i = 0; i < sqlParameters.size(); i++) {
            Object value = paramValueMap.get(sqlParameters.get(i));
            typeHandlerMap.get(value.getClass()).setParameter(ps, i + 1, value);
        }

        ps.execute();
        ResultSet rs = ps.getResultSet();

        List<String> columnList = Lists.newArrayList();
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            columnList.add(metaData.getColumnName(i + 1));
        }

        Class<?> returnType = ms.getReturnType();
        List list = Lists.newArrayList();
        while (rs.next()) {
            Object instance = returnType.newInstance();
            for (String columnName : columnList) {
                Field field = ReflectUtil.getField(returnType, columnName);
                Object val = typeHandlerMap.get(field.getType()).getResult(rs, columnName);
                ReflectUtil.setFieldValue(instance, field, val);
            }
            list.add(instance);
        }
        rs.close();
        ps.close();
        connection.close();
        return list;
    }

    @Override
    public int update(MappedStatement ms, Object parameter) {
        return 0;
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
