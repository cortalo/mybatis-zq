package com.zhengqing.mybatis.session.defaults;

import com.zhengqing.mybatis.session.Configuration;
import com.zhengqing.mybatis.session.SqlSession;
import com.zhengqing.mybatis.session.SqlSessionFactory;

/**
 * <p> 默认的SqlSessionFactory </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/4/23 01:58
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(this.configuration, this.configuration.newExecutor());
    }
}