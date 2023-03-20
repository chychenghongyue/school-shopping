package com.example.work;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

@WebServlet("/user_infoServlet")
public class user_infoServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json;charset=utf-8");

        StringBuffer stringBuffer = new StringBuffer() ;
        InputStream inputStream = req.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader br = new BufferedReader(inputStreamReader);
        String s = "" ;
        while((s=br.readLine())!=null){
            stringBuffer.append(s) ;
        }
        user_info user_info=JSON.parseObject(String.valueOf(stringBuffer),user_info.class);

        if(!user_info.getSex().equals("null")) {
            HttpSession session = req.getSession();
            String name = session.getAttribute("user_name").toString();
            try {
                database db = new database();
                if (user_info.getAge() != null && user_info.getSex() != null && user_info.getHobby() != null && user_info.getLocation_1() != null && user_info.getLocation_2() != null) {
                    db.update_user_info(user_info, name);
                    System.out.println("已经更改");
                }
                user_info show = db.show_user_info(name);
                System.out.println("已经查询");
                System.out.println(show.toString());
                resp.getWriter().write(JSON.toJSONString(show));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        HttpSession session = req.getSession();
        String name = session.getAttribute("user_name").toString();
        try {
            database db = new database();
            user_info show = db.show_user_info(name);
            System.out.println("已经查询");
            System.out.println(show.toString());
            resp.getWriter().write(JSON.toJSONString(show));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
