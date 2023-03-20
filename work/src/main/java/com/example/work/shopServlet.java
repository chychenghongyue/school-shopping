package com.example.work;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.LinkedList;

@WebServlet("/shopServlet")
public class shopServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("shopServlet执行了");
        resp.setContentType("text/json;charset=utf-8");
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = req.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader br = new BufferedReader(inputStreamReader);
        String s = "";
        while ((s = br.readLine()) != null) {
            stringBuffer.append(s);
        }
        product product=JSON.parseObject(String.valueOf(stringBuffer),product.class);
        if (true) {
            try {
                database db = new database();
                LinkedList<product> linkedList = db.show_product();
                System.out.println(linkedList);
                if (linkedList != null) {
                    resp.getWriter().write(JSON.toJSONString(linkedList));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
