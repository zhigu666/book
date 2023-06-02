package com.enjoy.book.action;


import com.enjoy.book.bean.User;
import com.enjoy.book.biz.UserBiz;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user.let")
public class UserServlet extends HttpServlet {
    //构件userbiz对象
    UserBiz userBiz = new UserBiz();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        //1.判断用户请求命令
        String method = req.getParameter("type");
        switch (method) {
            case "login":
                //2.从login.html中获取用户名和密码
                String name = req.getParameter("name");
                String pwd = req.getParameter("pwd");


                //3.调用userbiz的getuser方法，根据用户名和密码获取对应的用户对象
                User user = userBiz.getUser(name, pwd);

                //4.判断用户对象是否为null
                if (user == null) {
                    out.println("<script>alert('用户名或密码不存在');location.href = 'login.html';</script>");
                    //5.如果是null表示用户名或密码不正确，提示错误，回到登录界面

                } else {
                    out.println("<script>alert('登录成功');location.href = 'index.jsp';</script>");
                }
                break;

            case "exit":
                //1.清除session


                //2.跳转到login界面
                    break;

        }
    }
}
