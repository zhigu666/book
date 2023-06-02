package com.enjoy.book.dao;


import com.enjoy.book.bean.*;
import com.enjoy.book.until.DBHelper;
import jdk.nashorn.internal.codegen.types.BooleanType;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookDao {
    QueryRunner runner = new QueryRunner();

    public List<Book> getBooksByTypeId(long typeId) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "select * from book where typeId = ?";
        List<Book> books = runner.query(conn, sql, new BeanListHandler<Book>(Book.class), typeId);
        conn.close();
        return books;
    }

    public int add(long typeId, String name, double price, String desc, String pic, String publish, String author, long stock, String address) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "insert into book(typeId,`name`,price,`desc`,pic,publish,author,stock,address) values(?,?,?,?,?,?,?,?,?)";
        int count = runner.update(conn, sql, typeId, name, price, desc, pic, publish, author, stock, address);
        conn.close();
        return count;
    }

    public int modify(long id, long typeId, String name, double price, String desc, String pic, String publish, String author, long stock, String address) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "update book set typeId = ?, `name`=?,price = ?,`desc`=?,pic=?, " +
                "publish = ?,author= ?,stock=?,address=? " +
                "where id=?";
        int count = runner.update(conn, sql, typeId, name, price, desc, pic, publish, author, stock, address, id);
        conn.close();
        return count;
    }

    public int remove(long id) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "delete from book where id = ?";
        int count = runner.update(conn, sql, id);
        conn.close();
        return count;
    }

    public List<Book> getByPage(int pageIndex, int pageSize) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql=" select * from book limit ?,?";
        int offset = (pageIndex-1)*pageSize;
        List<Book> books = runner.query(conn,sql,new BeanListHandler<Book>(Book.class),offset,pageSize);
        conn.close();
        return books;
    }

    public Book getById(long id) throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "select * from book where id = ?";
        Book book = runner.query(conn, sql, new BeanHandler<Book>(Book.class), id);
        conn.close();
        return book;
    }

    public int getCount() throws SQLException {
        Connection conn = DBHelper.getConnection();
        String sql = "select count(id) from book";
        Object data = runner.query(conn, sql, new ScalarHandler<>());
        System.out.println(data.getClass());
        int count = (int) ((long) data);
        conn.close();
        return count;
    }

    public static void main(String[] args) {
        try {
            //List<Book> books = new BookDao().getBooksByTypeId(0);
            //System.out.println(books.size());
            //for (Book book : books) {
            //   System.out.println(book);
            //}
            BookDao bookDao = new BookDao();
            //int count = bookDao.getCount();
            List<Book> books = bookDao.getByPage(1,3);
            //System.out.println(count);
            for(Book book : books){
                System.out.println(book);
            }
        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }
    }
}
