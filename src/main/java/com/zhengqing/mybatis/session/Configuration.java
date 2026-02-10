package com.zhengqing.mybatis.session;

import com.google.common.collect.Maps;
import com.zhengqing.mybatis.executor.Executor;
import com.zhengqing.mybatis.executor.SimpleExecutor;
import com.zhengqing.mybatis.mapping.MappedStatement;
import com.zhengqing.mybatis.type.IntegerTypeHandler;
import com.zhengqing.mybatis.type.LongTypeHandler;
import com.zhengqing.mybatis.type.StringTypeHandler;
import com.zhengqing.mybatis.type.TypeHandler;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> 核心配置 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/4/22 18:11
 */
@Data
public class Configuration {

    private Map<Class, TypeHandler> typeHandlerMap = Maps.newHashMap();

    // eg: com.zhengqing.demo.mapper.UserMapper.selectList --> mapper配置信息
    private Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public Configuration() {
        this.typeHandlerMap.put(Integer.class, new IntegerTypeHandler());
        this.typeHandlerMap.put(Long.class, new LongTypeHandler());
        this.typeHandlerMap.put(String.class, new StringTypeHandler());
    }

    public void addMappedStatement(MappedStatement ms) {
        this.mappedStatements.put(ms.getId(), ms);
    }

    public MappedStatement getMappedStatement(String id) {
        return this.mappedStatements.get(id);
    }

    public Executor newExecutor() {
        return new SimpleExecutor(this);
    }
}