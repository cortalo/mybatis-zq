package com.zhengqing.mybatis.session;

import com.zhengqing.mybatis.builder.XMLConfigBuilder;
import com.zhengqing.mybatis.session.defaults.DefaultSqlSessionFactory;

/**
 * <p> SqlSession工厂构建者 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/4/23 02:09
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build() {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parse();
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return sqlSessionFactory;
    }

}