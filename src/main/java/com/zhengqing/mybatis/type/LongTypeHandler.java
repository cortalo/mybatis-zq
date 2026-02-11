package com.zhengqing.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LongTypeHandler implements TypeHandler<Long> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Long value) throws SQLException {
        ps.setLong(i, value);
    }
}
