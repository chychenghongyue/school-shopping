package com.example.work;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //设置服务器相应格式和编码
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String user_name=req.getParameter("user_name");
        String user_password=req.getParameter("user_password");
        System.out.println("获取了用户名和密码");
        try {
            if(user_name!=null&&user_name.length()>0&&user_password!=null&&user_password.length()>0) {
                database db = new database();
                user temp = db.select_login(user_name);
                if (temp!=null&& temp.getUser_password().equals(user_password)) {
                    System.out.println(temp.toString());
                    String two=req.getParameter("two");
                    if(two!=null&&"ok".equals(two)) {
                        Cookie cookie_1 = new Cookie("user_name", user_name);
                        cookie_1.setMaxAge(60);
                        resp.addCookie(cookie_1);
                        Cookie cookie_2 = new Cookie("user_password", user_password);
                        cookie_2.setMaxAge(60);
                        resp.addCookie(cookie_2);
                    }
                    //将登录后的用户对象存储到session里
                    HttpSession session= req.getSession();
                    session.setAttribute("user_name",user_name);
                    String contextPath=req.getContextPath();
                    resp.sendRedirect(contextPath+"/homePage.jsp");
                    System.out.println("登陆成功");
                }
                else {
                    HttpSession session=req.getSession();
                    session.setAttribute("login_error","用户名或密码错误");
                    resp.sendRedirect("login.jsp");
                    System.out.println("请检查用户名和密码后重新输入!");
                }
            }
            else {
                HttpSession session=req.getSession();
                session.setAttribute("login_error","用户名或密码错误");
                req.getRequestDispatcher("login.jsp").forward(req,resp);
                System.out.println("请检查用户名和密码后重新输入!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
