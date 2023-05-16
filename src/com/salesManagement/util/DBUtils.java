package com.salesManagement.util;

import java.sql.*;

public class DBUtils {
    private static String url = "jdbc:mysql://sql12.freemysqlhosting.net/sql12618511";
    private static String username = "sql12618511";
    private static String password = "ZQSLlTCsQq";
    private static String driver = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() throws Exception {
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public static void close(Connection connection, PreparedStatement preparedStatement) throws Exception {
        if(preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection!=null){
            connection.close();
        }
    }
    
    public static void close(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet) throws Exception{
        if(resultSet!=null){
            resultSet.close();
        }
        if(preparedStatement!=null){
            preparedStatement.close();
        }
        if (connection!=null){
            connection.close();
        }
    }

}
