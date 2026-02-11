package com.zhengqing.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface TypeHandler<T> {

    public void setParameter(PreparedStatement ps, int i, T value) throws SQLException;

}
