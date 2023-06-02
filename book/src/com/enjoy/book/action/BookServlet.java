package com.enjoy.book.action;

import com.enjoy.book.bean.Book;
import com.enjoy.book.biz.BookBiz;
import com.enjoy.book.biz.TypeBiz;
import com.enjoy.book.until.DateHelper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import sun.security.smartcardio.SunPCSC;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

@WebServlet("/book.let")
public class BookServlet extends HttpServlet {
    TypeBiz typeBiz = new TypeBiz();
    BookBiz bookBiz = new BookBiz();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        String type = req.getParameter("type");
        switch (type){
            case "add":
                try {
                    add(req,resp,out);
                } catch (Exception e) {
                    e.printStackTrace();
                    resp.sendError(500,"文件上传失败");
                }

                break;
            case "modifypre":
                long bookId = Long.parseLong(req.getParameter("id"));
                Book book = bookBiz.getById(bookId);
                req.setAttribute("book",book);
                req.getRequestDispatcher("book_modify.jsp").forward(req,resp);
                break;
            case "modify":
                try {
                    modify(req,resp,out);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case "remove":
                long removeId = Long.parseLong(req.getParameter("id"));
                try {
                    int count = bookBiz.remove(removeId);
                    if(count>0){
                        out.println("<script>alert('图书删除成功');location.href='book.let?type=query&pageIndex=1';</script>");
                    }else{
                        out.println("<script>alert('图书删除失败');location.href='book.let?type=query&pageIndex=1';</script>");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    out.println("<script>alert('"+e.getMessage()+"');location.href='book.let?type=query&pageIndex=1';</script>");
                }

                break;
            case "query":
                query(req,resp,out);

                break;
            case "details":
                details(req,resp,out);

                break;
            default:
                resp.sendError(404);
        }
    }

    private void modify(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) throws Exception {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024*9);
        File file = new File("c:\\temp");
        if(!file.exists()){
            file.mkdir();
        }
        factory.setRepository(file);
        ServletFileUpload fileUpload = new ServletFileUpload(factory);


        List<FileItem> fileItems = fileUpload.parseRequest(req);
        Book book = new Book();
        for(FileItem item:fileItems){
            if(item.isFormField()){
                String name = item.getFieldName();
                String value = item.getString("utf-8");
                switch (name){
                    case "id":
                        book.setId(Long.parseLong(value));
                        break;
                    case "pic":
                        book.setPic(value);
                        break;
                    case "typeId":
                        book.setTypeId(Long.parseLong(value));
                        break;
                    case "name":
                        book.setName(value);
                        break;
                    case "price":
                        book.setPrice(Double.parseDouble(value));
                        break;
                    case "desc":
                        book.setDesc(value);
                        break;
                    case "publish":
                        book.setPublish(value);
                        break;
                    case "author":
                        book.setAuthor(value);
                        break;
                    case "stock":
                        book.setStock(Long.parseLong(value));
                        break;
                    case "address":
                        book.setAddress(value);
                        break;

                }

            }else{
                String fileName = item.getName();
                if(fileName.trim().length()>0){
                    String filterName = fileName.substring(fileName.lastIndexOf("."));
                    fileName = DateHelper.getImageName()+filterName;
                    String path= req.getServletContext().getRealPath("/Images/cover");
                    String filePath = path+"/"+fileName;
                    String dbPath = "Images/cover/"+fileName;
                    book.setPic(dbPath);

                    item.write(new File(filePath));

                }

            }
        }
        int count = bookBiz.modify(book);
        if(count>0){
            out.println("<script>alert('修改书籍成功');location.href='book.let?type=query&pageIndex=1';</script>");

        }else{
            out.println("<script>alert('修改书籍失败');location.href='book.let?type=query&pageIndex=1';</script>");
        }
    }

    private void details(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) throws ServletException, IOException {
        long bookId = Long.parseLong(req.getParameter("id"));
        Book book = bookBiz.getById(bookId);
        req.setAttribute("book",book);
        req.getRequestDispatcher("book_details.jsp").forward(req,resp);

    }

    private void query(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) throws ServletException, IOException {
        int pageSize= 3;
        int pageCount = bookBiz.getPageCount(pageSize);
        int pageIndex = Integer.parseInt(req.getParameter("pageIndex"));
        if(pageIndex<1){
            pageIndex=1;
        }
        if(pageIndex>pageCount){
            pageIndex=pageCount;
        }
        List<Book> books = bookBiz.getByPage(pageIndex,pageSize);
        req.setAttribute("pageCount",pageCount);
        req.setAttribute("books",books);

        req.getRequestDispatcher("book_list.jsp?pageIndex="+pageIndex).forward(req,resp);

    }

    private void add(HttpServletRequest req, HttpServletResponse resp, PrintWriter out) throws Exception {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024*9);
        File file = new File("c:\\temp");
        if(!file.exists()){
            file.mkdir();
        }
        factory.setRepository(file);
        ServletFileUpload fileUpload = new ServletFileUpload(factory);


        List<FileItem> fileItems = fileUpload.parseRequest(req);
        Book book = new Book();
        for(FileItem item:fileItems){
            if(item.isFormField()){
                String name = item.getFieldName();
                String value = item.getString("utf-8");
                switch (name){
                    case "typeId":
                        book.setTypeId(Long.parseLong(value));
                        break;
                    case "name":
                        book.setName(value);
                        break;
                    case "price":
                        book.setPrice(Double.parseDouble(value));
                        break;
                    case "desc":
                        book.setDesc(value);
                        break;
                    case "publish":
                        book.setPublish(value);
                        break;
                    case "author":
                        book.setAuthor(value);
                        break;
                    case "stock":
                        book.setStock(Long.parseLong(value));
                        break;
                    case "address":
                        book.setAddress(value);
                        break;

                }

            }else{
                String fileName = item.getName();
                String filterName = fileName.substring(fileName.lastIndexOf("."));
                fileName = DateHelper.getImageName()+filterName;
                String path= req.getServletContext().getRealPath("/Images/cover");
                String filePath = path+"/"+fileName;
                String dbPath = "Images/cover/"+fileName;
                book.setPic(dbPath);

                item.write(new File(filePath));



            }
        }
        int count = bookBiz.add(book);
        if(count>0){
            out.println("<script>alert('添加书籍成功');location.href='book.let?type=query&pageIndex=1';</script>");

        }else{
            out.println("<script>alert('添加书籍失败');location.href='book_add.jsp';</script>");
        }



    }


}
