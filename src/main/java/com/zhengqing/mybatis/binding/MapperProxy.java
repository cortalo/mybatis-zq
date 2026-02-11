package com.zhengqing.mybatis.binding;

import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.mybatis.annotations.Param;
import com.zhengqing.mybatis.annotations.Select;
import com.zhengqing.mybatis.mapping.MappedStatement;
import com.zhengqing.mybatis.parsing.GenericTokenParser;
import com.zhengqing.mybatis.parsing.ParameterMappingTokenHandler;
import com.zhengqing.mybatis.parsing.TokenHandler;
import com.zhengqing.mybatis.session.Configuration;
import com.zhengqing.mybatis.type.IntegerTypeHandler;
import com.zhengqing.mybatis.type.LongTypeHandler;
import com.zhengqing.mybatis.type.StringTypeHandler;
import com.zhengqing.mybatis.type.TypeHandler;
import lombok.SneakyThrows;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperProxy implements InvocationHandler {

    private Configuration configuration;
    private Class<?> mapperClass;

    public MapperProxy(Configuration configuration, Class<?> mapperClass) {
        this.configuration = configuration;
        this.mapperClass = mapperClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Connection connection = getConnection();
        MappedStatement ms = configuration.getMappedStatement(this.mapperClass.getName() + "." + method.getName());
        Map<Class<?>, TypeHandler> typeHandlerMap = configuration.getTypeHandlerMap();

        PreparedStatement ps = connection.prepareStatement(ms.getSql());

        Map<String, Object> paramValueMap = new HashMap<>();
        for (int i = 0; i < method.getParameters().length; i++) {
            paramValueMap.put(method.getParameters()[i].getAnnotation(Param.class).value(), args[i]);
        }

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

    @SneakyThrows
    private static Connection getConnection() {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/online_shopping?useUnicode=true&characterEncoding=UTF8&useSSL=false",
                "root", "root");
        return connection;
    }
}
