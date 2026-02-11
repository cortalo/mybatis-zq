package com.zhengqing.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface TypeHandler<T> {

    public void setParameter(PreparedStatement ps, int i, T value) throws SQLException;

    public T getResult(ResultSet rs, String columnName) throws SQLException;

}
