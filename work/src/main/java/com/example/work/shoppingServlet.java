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
import java.util.Arrays;

@WebServlet("/shoppingServlet")
public class shoppingServlet extends HttpServlet {
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
        Integer[][] info= JSON.parseObject(String.valueOf(stringBuffer),Integer[][].class);
        HttpSession httpSession=req.getSession();
        String name= httpSession.getAttribute("user_name").toString();
        System.out.println(name);
        try {
            database db=new database();
            String show_String=db.select_product_info(name);
            Integer[][] show=JSON.parseObject(show_String,Integer[][].class);
            if(show!=null) {
                for (int i = 0; i < show.length; i++) {
                    show[i][1]+=info[i][1];
                }
            }
            else show=info;
            show_String=Arrays.deepToString(show);
            db.update_product_info(name,show_String);

            String end_String=db.select_product_info(name);
            Integer[][] end=JSON.parseObject(end_String,Integer[][].class);

            resp.getWriter().write(JSON.toJSONString(end));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
