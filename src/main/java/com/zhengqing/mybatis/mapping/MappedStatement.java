package com.zhengqing.mybatis.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p> mapper配置信息 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/4/22 18:16
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class MappedStatement {

    private String id; // 唯一标识 eg: com.zhengqing.demo.mapper.UserMapper.selectList
    private String sql; // SQL
    private Class returnType;// 返回类型
    private SqlCommandType sqlCommandType; // SQL命令类型


}