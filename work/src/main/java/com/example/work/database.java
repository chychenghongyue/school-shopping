package com.example.work;

import com.alibaba.fastjson2.JSON;

import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class database {
    Connection connection;

    public database() throws SQLException, ClassNotFoundException {
        //获取链接
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url="jdbc:mysql://localhost:3306";
        String username="root";
        String password="123456";
        connection= DriverManager.getConnection(url,username,password);
    }
    public int insert_user(String user_name, String user_password) throws SQLException {
        System.out.println("执行了insert");
        String sql_1 = "insert into java.user values(?,?)";
        PreparedStatement preparedStatement_1 = connection.prepareStatement(sql_1);
        preparedStatement_1.setString(1, user_name);
        preparedStatement_1.setString(2, user_password);
        int count_1=preparedStatement_1.executeUpdate();
        String sql_2 = "insert into java.user_info (name) values(?)";
        PreparedStatement preparedStatement_2 = connection.prepareStatement(sql_2);
        preparedStatement_2.setString(1, user_name);
        int count_2=preparedStatement_2.executeUpdate();

        String sql_3="insert into java.product_info (user_name)values(?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql_3);
        preparedStatement.setString(1,user_name);
        int count_3=preparedStatement.executeUpdate();

        return count_1+count_2+count_3;
    }
    public void delete_user(String user_name) throws SQLException {
        String sql_1="delete from java.user where name=?";
        PreparedStatement preparedStatement_1=connection.prepareStatement(sql_1);
        preparedStatement_1.setString(1,user_name);
        int count_1=preparedStatement_1.executeUpdate();
        String sql_2 = "delete from java.user_info where name=?";
        PreparedStatement preparedStatement_2 = connection.prepareStatement(sql_2);
        preparedStatement_2.setString(1, user_name);
        int count_2=preparedStatement_2.executeUpdate();
    }
    public void update_user_info(user_info user_info,String name) throws SQLException {
        String sql="update java.user_info set age=?,sex=?,hobby=?,location_1=?,location_2=? where name=?";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1,user_info.getAge());
        preparedStatement.setString(2,user_info.getSex());
        preparedStatement.setString(3,Arrays.toString(user_info.getHobby()));
        preparedStatement.setString(4,user_info.getLocation_1());
        preparedStatement.setString(5,user_info.getLocation_2());
        preparedStatement.setString(6,name);
        int count=preparedStatement.executeUpdate();
    }
    public user_info show_user_info(String name) throws SQLException {
        //定义sql语句
        String sql="select age, sex, hobby, location_1, location_2 from java.user_info where name= ?";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        //设置问号的值
        preparedStatement.setString(1,name);
        ResultSet resultSet=preparedStatement.executeQuery();
        if (resultSet.next()){
            return new user_info(resultSet.getString(1),resultSet.getString(2),JSON.parseObject(resultSet.getString(3),int[].class),resultSet.getString(4),resultSet.getString(5));
        }
        return null;
    }
    public user select_login(String name) throws SQLException {
        String sql="select * from java.user where name=?";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        ResultSet resultSet=preparedStatement.executeQuery();
        if(resultSet.next()){
            return new user(resultSet.getString(1),resultSet.getString(2));
        }
        else
            return null;
    }
    public LinkedList<String> showAll_user(String name) throws SQLException {
        //定义sql语句
        String sql="select name from java.user where name !=?";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        ResultSet resultSet=preparedStatement.executeQuery();
        LinkedList<String> linkedList=new LinkedList<>();
        while(resultSet.next()){
            linkedList.add(resultSet.getString(1));
        }
        return linkedList;
    }
    public LinkedList<idiom> select_idiom(List<idiom> idioms) throws SQLException {
        LinkedList<idiom> linkedList = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            String sql = "select * from java.idiom where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idioms.get(i).getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                linkedList.add(new idiom(resultSet.getString(1), resultSet.getString(2)));
            }
        }
        return linkedList;
    }
    public idiom select_idiom_next(idiom idiom1,idiom idiom2) throws SQLException {
        String spell1=idiom1.getSpell();
        String spell2=idiom2.getSpell();
        int num1=spell1.lastIndexOf(" ");
        spell1=(spell1.substring(num1+1));
        int num2=spell2.indexOf(" ");
        spell2=(spell2.substring(0,num2));
        System.out.println(spell1);
        System.out.println(spell2);
        if(idiom1.getName().equals("null")||(idiom1.getName().substring(3)).equals(idiom2.getName().substring(0,1))||spell1==spell2||(spell1.equals(spell2))) {
            String name = idiom2.getName();
            String spell = idiom2.getSpell();
            idiom2.setName(name.substring(3));//成语最后一个字的位置
            int num = spell.lastIndexOf(" ");//成语拼音最后一次出现空格的位置
            idiom2.setSpell(spell.substring(num + 1));//最后一个字的拼音从最后一个空格的后一位开始


            String sql_name = "SELECT * FROM java.idiom where name like ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql_name);
            preparedStatement.setString(1, idiom2.getName() + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("接上龙1");
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                return new idiom(resultSet.getString(1), resultSet.getString(2));
            } else {
                String sql_spell = "SELECT * FROM java.idiom where spell like ?";
                preparedStatement = connection.prepareStatement(sql_spell);
                preparedStatement.setString(1, idiom2.getSpell() + "%");
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    System.out.println("接上龙2");
                    System.out.println(resultSet.getString(1));
                    System.out.println(resultSet.getString(2));
                    return new idiom(resultSet.getString(1), resultSet.getString(2));
                } else
                    return null;
            }
        }
        else {
            System.out.println("接不上龙");
            return null;
        }
    }
    public LinkedList<product> show_product() throws SQLException {
        String sql="select id, name, price from java.product";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        ResultSet resultSet=preparedStatement.executeQuery();
        LinkedList<product> linkedList=new LinkedList<>();
        while(resultSet.next()){
            linkedList.add(new product(resultSet.getInt(1),resultSet.getString(2),resultSet.getFloat(3)));
        }
        return linkedList;
    }
    public String select_product_info(String user_name) throws SQLException {
        String sql="select info from java.product_info where user_name=?";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1,user_name);
        ResultSet resultSet=preparedStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        else return null;
    }
    public void update_product_info(String user_name,String info) throws SQLException {
        String sql="update java.product_info set info=? where user_name=?";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1,info);
        preparedStatement.setString(2,user_name);
        int count=preparedStatement.executeUpdate();
    }
    public void insert_message(message message) throws SQLException {
        String sql = "insert into java.message values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, message.getStartname());
        preparedStatement.setString(2, message.getEndname());
        preparedStatement.setString(3, message.getInfo());
        int count=preparedStatement.executeUpdate();
    }

    public LinkedList<message> show_message(String name) throws SQLException {
        String sql="select startname, endname, info from java.message where startname=? or endname=?";
        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, name);
        ResultSet resultSet=preparedStatement.executeQuery();
        LinkedList<message> linkedList=new LinkedList<>();
        while(resultSet.next()){
           linkedList.add(new message(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3)));
        }
        return linkedList;
    }
    public void close() throws SQLException {
        connection.close();
    }
}
