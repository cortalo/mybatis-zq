package com.zhengqing.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p> 字段类型处理器 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2024/4/22 02:58
 */
public interface TypeHandler<T> {

    void setParameter(PreparedStatement ps, int i, T parameter) throws SQLException;

    T getResult(ResultSet rs, String columnName) throws SQLException;

}