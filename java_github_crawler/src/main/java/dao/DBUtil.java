package dao;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/java_github_crawler";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static DataSource dataSource = null;

    //获取 DataSource
    public static DataSource getDataSource(){
        if(dataSource == null) {
            synchronized (DBUtil.class) {
                if (dataSource == null) {
                    dataSource = new MysqlDataSource();
                    ((MysqlDataSource)dataSource).setUrl(URL);
                    ((MysqlDataSource)dataSource).setUser(USERNAME);
                    ((MysqlDataSource)dataSource).setPassword(PASSWORD);
                }
            }
        }
        return dataSource;
    }

    //获取数据库连接
    public static Connection getConnection() {

        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            System.out.println("数据库连接获取失败!");
        }
        return null;
    }

    //关闭数据库连接
    public static void close(Connection connection, Statement statement,
                             ResultSet resultSet){
        try {
            if (resultSet != null){
                resultSet.close();
            }
            if (statement != null){
                statement.close();
            }
            if (connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection connection, Statement statement){
        close(connection, statement, null);
    }
}
