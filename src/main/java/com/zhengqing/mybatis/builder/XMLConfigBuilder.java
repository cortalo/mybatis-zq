package com.zhengqing.mybatis.builder;

import cn.hutool.core.util.ClassUtil;
import com.google.common.collect.Lists;
import com.zhengqing.mybatis.annotations.Delete;
import com.zhengqing.mybatis.annotations.Insert;
import com.zhengqing.mybatis.annotations.Select;
import com.zhengqing.mybatis.annotations.Update;
import com.zhengqing.mybatis.mapping.MappedStatement;
import com.zhengqing.mybatis.mapping.SqlCommandType;
import com.zhengqing.mybatis.session.Configuration;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

/**
 * <p> XML配置构建器 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/4/22 18:10
 */
public class XMLConfigBuilder {

    private List<Class<? extends Annotation>> sqlAnnotationTypeList =
            Lists.newArrayList(Insert.class, Delete.class, Update.class, Select.class);

    public Configuration parse() {
        Configuration configuration = new Configuration();
        // 解析mapper
        this.parseMapper(configuration);
        return configuration;
    }

    @SneakyThrows
    private void parseMapper(Configuration configuration) {
        Set<Class<?>> classes = ClassUtil.scanPackage("com.zhengqing.demo.mapper");
        for (Class<?> aClass : classes) {
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                SqlCommandType sqlCommandType = null;
                String originalSql = "";
                for (Class<? extends Annotation> sqlAnnotationType : this.sqlAnnotationTypeList) {
                    Annotation annotation = method.getAnnotation(sqlAnnotationType);
                    if (annotation != null) {
                        originalSql = (String) annotation.getClass().getMethod("value").invoke(annotation);
                        if (annotation instanceof Insert) {
                            sqlCommandType = SqlCommandType.INSERT;
                        } else if (annotation instanceof Delete) {
                            sqlCommandType = SqlCommandType.DELETE;
                        } else if (annotation instanceof Update) {
                            sqlCommandType = SqlCommandType.UPDATE;
                        } else if (annotation instanceof Select) {
                            sqlCommandType = SqlCommandType.SELECT;
                        }
                    }
                }

                // 拿到mapper的返回类型
                Class returnType = null;
                Type genericReturnType = method.getGenericReturnType();
                if (genericReturnType instanceof ParameterizedType) {
                    returnType = (Class) ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
                } else if (genericReturnType instanceof Class) {
                    returnType = (Class) genericReturnType;
                }

                // 封装
                MappedStatement mappedStatement = MappedStatement.builder()
                        .id(aClass.getName() + "." + method.getName())
                        .sql(originalSql)
                        .returnType(returnType)
                        .sqlCommandType(sqlCommandType)
                        .build();
                configuration.addMappedStatement(mappedStatement);
            }
        }
    }

}