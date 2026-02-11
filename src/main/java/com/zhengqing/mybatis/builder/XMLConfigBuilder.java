package com.zhengqing.mybatis.builder;

import cn.hutool.core.util.ClassUtil;
import com.zhengqing.mybatis.annotations.Select;
import com.zhengqing.mybatis.mapping.MappedStatement;
import com.zhengqing.mybatis.parsing.GenericTokenParser;
import com.zhengqing.mybatis.parsing.ParameterMappingTokenHandler;
import com.zhengqing.mybatis.parsing.TokenHandler;
import com.zhengqing.mybatis.session.Configuration;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

public class XMLConfigBuilder {

    public Configuration parse() {
        Configuration configuration = new Configuration();
        parseMapper(configuration);
        return configuration;
    }

    private void parseMapper(Configuration configuration) {
        Set<Class<?>> classes = ClassUtil.scanPackage("com.zhengqing.demo.mapper");
        for (Class<?> aClass : classes) {
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {

                TokenHandler tokenHandler = new ParameterMappingTokenHandler();
                String originalSql = method.getAnnotation(Select.class).value();
                GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", tokenHandler);
                String sql = genericTokenParser.parse(originalSql);

                Class<?> returnType = null;
                Type genericReturnType = method.getGenericReturnType();
                if (genericReturnType instanceof ParameterizedType) {
                    returnType = (Class<?>) ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
                }

                MappedStatement ms = MappedStatement.builder()
                        .id(aClass.getName() + "." + method.getName())
                        .sql(sql)
                        .returnType(returnType)
                        .sqlParameters(tokenHandler.getParameters())
                        .build();
                configuration.addMappedStatement(ms.getId(), ms);
            }
        }

    }

}
