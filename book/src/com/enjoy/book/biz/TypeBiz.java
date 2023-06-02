package com.enjoy.book.biz;

import com.enjoy.book.bean.*;
import com.enjoy.book.dao.BookDao;
import com.enjoy.book.dao.TypeDao;
import com.enjoy.book.bean.*;

import java.sql.SQLException;
import java.util.List;


public class TypeBiz {
    TypeDao typeDao = new TypeDao();
    public List<Type> getAll(){
        try {
            return typeDao.getAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }
    public  int add(String name,long parentId){
        int count = 0;
        try {
            count = typeDao.add(name,parentId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;

    }
    public int modify(long id,String name,long parentId){
        int count = 0;
        try {
            count=typeDao.modify(id,name,parentId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;

    }
    public int remove(long id) throws Exception {

        BookDao bookDao = new BookDao();
        int count = 0;
        try {
            List<Book> books = bookDao.getBooksByTypeId(id);
            if(books.size()>0){
                throw new Exception("删除的类型有子信息，删除失败");
            }
            count = typeDao.remove(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;



    }
    public Type getById(long id){
        try {
            return typeDao.getById(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }


    }

    public static void main(String[] args) {
        List<Type> types = null;

            int i = new TypeBiz().add("文wen",0);

        System.out.println(types);
    }


}
