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
import java.util.List;

@WebServlet("/idiomServlet")
public class idiomServlet extends HttpServlet{
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


        List<idiom> idioms=JSON.parseArray(String.valueOf(stringBuffer),idiom.class);
        try {
            database db=new database();
            List<idiom> idiom=db.select_idiom(idioms);
            if (idiom.size()==2||(idioms.get(0).getName().equals("null")&&idiom.size()==1)){
                idiom idiom1;
                if (idiom.size()==2)
                    idiom1=db.select_idiom_next(idiom.get(0),idiom.get(1));
                else
                    idiom1=db.select_idiom_next(new idiom("null","null"),idiom.get(0));
                System.out.println("结果"+idiom1.toString());
                if (idiom1!=null){
                    System.out.println(idiom1);
                    idiom1.setSpell("null");
                    resp.getWriter().write(JSON.toJSONString(idiom1));
                }
                else {
                    idiom info=new idiom("info","恭喜您获胜了!");
                    System.out.println("恭喜您获胜了!");
                    resp.getWriter().write(JSON.toJSONString(info));
                }
            }
            else
            {
                idiom info=new idiom("info","请您输入正确的成语!");
                System.out.println("请您输入正确的成语!");
                resp.getWriter().write(JSON.toJSONString(info));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
