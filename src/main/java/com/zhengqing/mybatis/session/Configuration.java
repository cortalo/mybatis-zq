package com.zhengqing.mybatis.session;

import com.zhengqing.mybatis.mapping.MappedStatement;
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

    // eg: com.zhengqing.demo.mapper.UserMapper.selectList --> mapper配置信息
    private Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public void addMappedStatement(MappedStatement ms) {
        this.mappedStatements.put(ms.getId(), ms);
    }

    public MappedStatement getMappedStatement(String id) {
        return this.mappedStatements.get(id);
    }
}