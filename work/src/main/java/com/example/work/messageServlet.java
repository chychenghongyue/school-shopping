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
import java.util.LinkedList;

@WebServlet("/messageServlet")
public class messageServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json;charset=utf-8");
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = req.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader br = new BufferedReader(inputStreamReader);
        String s = "";
        while ((s = br.readLine()) != null) {
            stringBuffer.append(s);
        }
        message message= JSON.parseObject(String.valueOf(stringBuffer),message.class);
        if((!message.getEndname().equals("null"))&&(!message.getInfo().equals("null"))){
            try {
                HttpSession session=req.getSession();
                String name = session.getAttribute("user_name").toString();
                message.setStartname(name);
                database db=new database();
                db.insert_message(message);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            HttpSession session=req.getSession();
            String name = session.getAttribute("user_name").toString();
            database db=new database();
            LinkedList<message>messages =db.show_message(name);
            resp.getWriter().write(JSON.toJSONString(messages));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
