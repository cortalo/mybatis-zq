package com.zhengqing.mybatis.binding;

import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhengqing.mybatis.annotations.Param;
import com.zhengqing.mybatis.annotations.Select;
import com.zhengqing.mybatis.executor.SimpleExecutor;
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
        MappedStatement ms = configuration.getMappedStatement(this.mapperClass.getName() + "." + method.getName());

        Map<String, Object> paramValueMap = new HashMap<>();
        for (int i = 0; i < method.getParameters().length; i++) {
            paramValueMap.put(method.getParameters()[i].getAnnotation(Param.class).value(), args[i]);
        }

        return configuration.newExecutor().query(ms, paramValueMap);
    }

}
