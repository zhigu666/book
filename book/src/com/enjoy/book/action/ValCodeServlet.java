package com.enjoy.book.action;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
//生成验证码模块

@WebServlet(urlPatterns = "/code.let",loadOnStartup = 1)
public class ValCodeServlet extends HttpServlet {
    Random random = new Random();
    //随机生成字符串

    private String getRandomStr(){
        String str="23456789qwertyupasdfghjkzxcvbnm";//1,0，o,i
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<4;i++){
            int index = random.nextInt(str.length());
            char letter  = str.charAt(index);
            sb.append(letter);
        }
        return sb.toString();
    }

//背景色
    private Color getBackColor(){
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return new Color(red,green,blue);
    }

    //前景色
    private Color getForeColor(Color bgColor){
        int red = 255 - bgColor.getRed();
        int blue = 255 - bgColor.getBlue();
        int green = 255 - bgColor.getGreen();

        return new Color(red,green,blue);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1.设置响应格式为图片：jpg
        resp.setContentType("image/jpeg");
        //2.图片对象
        BufferedImage bufferedImage = new BufferedImage(80,30,BufferedImage.TYPE_INT_RGB);
        //3.获取画布对象
        Graphics g = bufferedImage.getGraphics();
        //4.设置背景颜色
        Color bgColor = getBackColor();
        //5.画背景
        g.fillRect(0,0,80,30);
        //6.设置前景色
        Color foreColor = getForeColor(bgColor);
        g.setColor(foreColor);

        //设置字体
        g.setFont(new Font("黑体",Font.BOLD,26));
        //7.存入随机字符串
        String randomStr = getRandomStr();
        HttpSession session = req.getSession();
        session.setAttribute("code",randomStr);
        g.drawString(randomStr,10,28);

        //8.噪点

        for(int i=0;i<30;i++){
            g.setColor(Color.white);
            int x = random.nextInt(80);
            int y = random.nextInt(30);
            g.fillRect(x,y,1,1);
        }

        //9.将图片输出到响应流
        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(bufferedImage,"jpeg",sos);

    }
}
