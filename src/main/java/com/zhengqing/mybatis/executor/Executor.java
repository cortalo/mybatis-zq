package com.zhengqing.mybatis.executor;

import com.zhengqing.mybatis.mapping.MappedStatement;

import java.util.List;

public interface Executor {

    <T> List<T> query(MappedStatement ms, Object parameter);

    int update(MappedStatement ms, Object parameter);

}
