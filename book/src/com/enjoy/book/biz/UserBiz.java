package com.enjoy.book.biz;

import com.enjoy.book.bean.User;
import com.enjoy.book.dao.UserDao;

import java.sql.SQLException;

public class UserBiz {
    //构建userdao的对象
    UserDao userDao = new UserDao();
    public User getUser(String name, String pwd){
        User user = null;
        try {
            user = userDao.getUser(name,pwd);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
}
