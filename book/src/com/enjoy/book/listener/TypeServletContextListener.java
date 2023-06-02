package com.enjoy.book.listener;

import com.enjoy.book.bean.*;
import com.enjoy.book.biz.TypeBiz;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import java.util.List;

@WebListener
public class TypeServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        TypeBiz biz = new TypeBiz();
        List<Type> types = biz.getAll();

        ServletContext application = servletContextEvent.getServletContext();

        application.setAttribute("types",types);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
