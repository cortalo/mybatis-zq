package com.zhengqing.mybatis.session;

import com.google.common.collect.Maps;
import com.zhengqing.mybatis.mapping.MappedStatement;
import com.zhengqing.mybatis.parsing.ParameterMappingTokenHandler;
import com.zhengqing.mybatis.parsing.TokenHandler;
import com.zhengqing.mybatis.type.IntegerTypeHandler;
import com.zhengqing.mybatis.type.LongTypeHandler;
import com.zhengqing.mybatis.type.StringTypeHandler;
import com.zhengqing.mybatis.type.TypeHandler;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    private Map<String, MappedStatement> mappedStatements = new HashMap<>();
    @Getter
    private Map<Class<?>, TypeHandler> typeHandlerMap = Maps.newHashMap();

    public Configuration() {
        typeHandlerMap.put(Integer.class, new IntegerTypeHandler());
        typeHandlerMap.put(Long.class, new LongTypeHandler());
        typeHandlerMap.put(String.class, new StringTypeHandler());
    }

    public MappedStatement getMappedStatement(String id) {
        return mappedStatements.get(id);
    }
    public void addMappedStatement(String id, MappedStatement ms) {
        mappedStatements.put(id, ms);
    }

}
