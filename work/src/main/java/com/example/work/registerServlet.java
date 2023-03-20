package com.example.work;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registerServlet")
public class registerServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        String user_name=req.getParameter("user_name");
        String user_password_1=req.getParameter("user_password_1");
        String user_password_2=req.getParameter("user_password_2");
        if(user_name!=null&&user_name.length()>0&&user_password_1!=null&&user_password_1.length()>0&&user_password_2!=null&&user_password_2.length()>0&&user_password_1.equals(user_password_2)){
            try {
                database db = new database();
                user temp=db.select_login(user_name);
                System.out.println("temp:"+temp);
                if(temp!=null){
                    req.setAttribute("register_info","该账号已存在");
                    System.out.println("该账号已存在");
                    req.getRequestDispatcher("register.jsp").forward(req,resp);
                }
                else {
                    int count=db.insert_user(user_name,user_password_1);
                    if(count==3) {
                        req.setAttribute("register_info","恭喜您，注册成功");
                        System.out.println("恭喜您，注册成功");
                        req.getRequestDispatcher("register.jsp").forward(req,resp);
                    }
                    else {
                        req.setAttribute("register_info","注册失败");
                        System.out.println("注册失败");
                        req.getRequestDispatcher("register.jsp").forward(req,resp);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            req.setAttribute("register_info","请检查用户名和密码后重新输入!");
            System.out.println("请检查用户名和密码后重新输入!");
            req.getRequestDispatcher("register.jsp").forward(req,resp);
        }
    }
}
