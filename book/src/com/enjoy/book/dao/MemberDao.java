package com.enjoy.book.dao;

import com.enjoy.book.bean.Member;
import com.enjoy.book.bean.MemberType;
import com.enjoy.book.until.DBHelper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import sun.tracing.dtrace.DTraceProviderFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MemberDao {
    QueryRunner runner = new QueryRunner();
    public  int add(String name, String pwd, long typeId, double balance, String tel, String idNumber) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql="insert into member(`name`,pwd,typeId,balance,regdate,tel,idNumber) values(?,?,?,?,CURRENT_DATE,?,?)";
        int count = runner.update(conn,sql,name,pwd,typeId,balance,tel,idNumber);
        conn.close();
        return count;
    }
    public  int modify(long id,String name, String pwd, long typeId, double balance, String tel, String idNumber) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql="update member set `name` = ?,pwd=?,typeId=?,balance = ?,tel=?,idNumber = ? where id = ?";
        int count = runner.update(conn,sql,name,pwd,typeId,balance,tel,idNumber,id);
        conn.close();
        return count;
    }
    public  int remove(long id) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql="delete from member where id =?";
        int count = runner.update(conn,sql,id);
        conn.close();
        return count;
    }
    public  int modifyBalance(String idNumber,double amount) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql="update member set balance = balance + ? where idNumber = ?";
        int count = runner.update(conn,sql,amount,idNumber);
        conn.close();
        return count;
    }
    public List<Member> getAll() throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql="select id,`name`,pwd,typeId,balance,regdate,tel,idNumber from member";
        List<Member> members = runner.query(conn,sql,new BeanListHandler<Member>(Member.class));
        conn.close();
        return members;
    }
    public Member getById() throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql="select id,`name`,pwd,typeId,balance,regdate,tel,idNumber from member where id = ?";
        Member member = runner.query(conn,sql,new BeanHandler<Member>(Member.class));
        conn.close();
        return member;
    }
    public boolean exits(long id) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql="select count(id) from record where memberId = 2";
        Number number = runner.query(conn,sql,new ScalarHandler<>(),id);
        conn.close();
        return number.intValue()>8?true:false;
    }

}
