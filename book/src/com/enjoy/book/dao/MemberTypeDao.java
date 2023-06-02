package com.enjoy.book.dao;

import com.enjoy.book.bean.MemberType;
import com.enjoy.book.until.DBHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MemberTypeDao {
    QueryRunner runner = new QueryRunner();
    public List<MemberType> getAll() throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql="select * from membertype";
        List<MemberType> memberTypes = runner.query(conn,sql,new BeanListHandler<MemberType>(MemberType.class));
        conn.close();
        return memberTypes;
    }
    public MemberType getById(long id) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql="select * from membertype where id = ?";
        MemberType memberType = runner.query(conn,sql,new BeanHandler<MemberType>(MemberType.class),id);
        conn.close();
        return memberType;
    }
}
