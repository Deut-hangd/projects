package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ProjectDao {

    // 将项目信息保存到数据库
    public void save(Project project) {
        // 获取数据库连接
        Connection connection = DBUtil.getConnection();
        // sql 语句执行句柄
        PreparedStatement statement = null;
        // 定义 sql 语句
        String sql = "Insert into project_table values(?,?,?,?,?,?,?)";
        try {
            // 将项目信息插入 project_table
            statement = connection.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getUrl());
            statement.setString(3, project.getDescription());
            statement.setInt(4, project.getStarCount());
            statement.setInt(5, project.getForkCount());
            statement.setInt(6, project.getOpenIssueCount());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            statement.setString(7, simpleDateFormat.format(System.currentTimeMillis()));
            int i = statement.executeUpdate();
            if (i < 1) {
                System.out.println("数据插入失败!");
            } else {
                System.out.println("数据插入成功!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection, statement);
        }

    }

    // 根据时间查询相应的项目信息
    public List<Project> selectProjectByDate(String date){
        List<Project> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 连接数据库
            connection = DBUtil.getConnection();
            // 查询语句
            String sql = "select name,url,starCount,forkCount,"
                    + "openedIssueCount from project_table where date = ? order by starCount desc";
            statement = connection.prepareStatement(sql);
            statement.setString(1, date);
            // 获取查询结果集
            resultSet = statement.executeQuery();
            // 将查询结果集转化为对象
            while (resultSet.next()){
                Project project = new Project();
                project.setName(resultSet.getString("name"));
                project.setUrl(resultSet.getString("url"));
                project.setStarCount(resultSet.getInt("starCount"));
                project.setForkCount(resultSet.getInt("forkCount"));
                project.setOpenIssueCount(resultSet.getInt("openedIssueCount"));
                list.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DBUtil.close(connection, statement, resultSet);
        }
        return list;
    }


}
