package com.zhengqing.mybatis;

import org.junit.Test;

import java.sql.*;

public class TestJdbc {

    @Test
    public void test() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/online_shopping?useUnicode=true&characterEncoding=UTF8&useSSL=false",
                "root", "root");
        PreparedStatement ps = connection.prepareStatement("select * from online_shopping_user");
        ps.execute();
        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
            System.out.println(rs.getString("name"));
        }
        rs.close();
        ps.close();
        connection.close();

    }

}
