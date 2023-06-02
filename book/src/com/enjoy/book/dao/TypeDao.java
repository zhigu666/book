package com.enjoy.book.dao;


import com.enjoy.book.until.DBHelper;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.commons.dbutils.QueryRunner;
import com.enjoy.book.bean.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;


public class TypeDao {
    //构建querrunner对象
    QueryRunner runner = new QueryRunner();

    //添加图书类型
    public int add(String name, long parentId) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "insert into type values (null,?,?)";
        int count = runner.update(conn,sql,name,parentId);
        conn.close();
        return count;


    }

    public List<Type> getAll() throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "select id,name,parentId from type";
        List<Type> types = runner.query(conn,sql,new BeanListHandler<>(Type.class));


        conn.close();
        return types;
    }


    public  Type getById(long typeId) throws SQLException {
        Connection conn = DBHelper.getConnection();
        Type type=null;
        String sql = "select id,name,parentId from type where id=?";
        type = runner.query(conn,sql,new BeanHandler<>(Type.class),typeId);
        conn.close();
        return type;
    }


    public  int modify(long id, String name, long parentId) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "update type set name=?,parentId=? where id=?";
        int count = runner.update(conn, sql,name,parentId,id);
        conn.close();
        return count;
    }


    public  int remove(long id) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "delete from type where id=?";
        int count = runner.update(conn, sql,id);
        conn.close();
        return count;
    }
    public static void main(String[] args){
        List<Type> types = null;
        try {
             int i = new TypeDao().add("文",0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(types);
    }

}
