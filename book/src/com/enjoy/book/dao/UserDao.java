package com.enjoy.book.dao;

import com.enjoy.book.bean.User;
import com.enjoy.book.until.DBHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDao {
    //创建querrunner 对象
    QueryRunner runner = new QueryRunner();
    public User getUser(String name, String pwd) throws SQLException {
        //1.调用dbhelper获取连接对象
        Connection conn = DBHelper.getConnection();
        //准备执行的sql语句

        String sql="select * from user where name=? and pwd=? and state = 1";
        //3.调用查询方法，将查询的数据封装成user对象
        User user = runner.query(conn,sql,new BeanHandler<User>(User.class),name,pwd);
        //4.关闭连接对象

        conn.close();
        //5.返回user
        return user;

    }

    public static  void main(String[] args){
        User user = null;
        try {
            user = new UserDao().getUser("super","123");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(user);
    }
}
