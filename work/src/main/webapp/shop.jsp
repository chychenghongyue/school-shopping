<%@ page import="com.alibaba.fastjson2.JSON" %>
<%@ page import="java.util.Arrays" %><%--
  Created by IntelliJ IDEA.
  User: CHY
  Date: 2022/11/03
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>购物界面</title>
</head>
<body>
<input type="button" value="主页" id="homePage">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="购物车" id="shopping">
<p id="table"></p>
<script>
    document.getElementById("homePage").onclick=function () {
        window.location.href="homePage.jsp";
    }
    var info=[];
    var table="<table border=\"1\" width=\"40%\" height=\"20%\">\n" +
        "    <tr>\n" +
        "        <th>商品编号</th>\n" +
        "        <th>商品名称</th>\n" +
        "        <th>商品价格</th>\n" +
        "        <th>操作</th>\n" +
        "    </tr>\n";
    var product = {
        "id": 0,
        "name": "null",
        "price": 0.0
    }
    var json;
    var json_date;
    //创建对象
    var xmlHttp = new XMLHttpRequest();
    //发送请求
    xmlHttp.open("post", "http://localhost:8080/work_war_exploded/shopServlet");
    xmlHttp.setRequestHeader('Content-Type', 'application/json');
    //将用户输入的值序列化成字符串JSON.stringify
    xmlHttp.send(JSON.stringify(product));
    //获取响应
    xmlHttp.onreadystatechange = function () {
        //保存 XMLHttpRequest 的状态:4：请求已完成且响应已就绪,   返回请求的状态号200: "OK"
        if (this.readyState == 4 && this.status == 200) {
            json = JSON.parse(this.responseText);
            json_date=json;
            for (let i = 0; i < json.length; i++) {
                table += "    <tr>\n" +
                    "        <th>" + json[i].id + "</th>\n" +
                    "        <th>" + json[i].name + "</th>\n" +
                    "        <th>" + json[i].price + "</th>\n" +
                    "        <th>" + "<span></span>" + "</th>\n";
            }
            document.getElementById("table").innerHTML = table;
            var a = document.getElementsByTagName("span");
            for (let i = 0; i < a.length; i++) {
                var string = "add" + json[i].id;
                a[i].innerHTML = '<button id="' + string + '">加入购物车</button>';
            }
        }
    }
    window.onclick=function () {
        var but = document.getElementsByTagName("button");
        console.log("but:length" + but.length);
        console.log("info.length" + info.length);
        console.log(info);
        for (let i = 0; i < but.length; i++) {
            but[i].onclick = function () {
                info[i] = 1;
                console.log("点击了按钮" + ++i);
            }
        }
    }
        document.getElementById("shopping").onclick = function () {
            for (let i = 0; i < info.length; i++) {
                console.log("info的值",info[i])
            }
            var but = document.getElementsByTagName("button");
            var temp =new Array(but.length);
            for (let i = 0; i < but.length; i++) {
                if (info[i]==1)
                    temp[i] = [i + 1, info[i]];
                else
                    temp[i] = [i + 1,0];
            }
            var date=JSON.stringify(temp);
            console.log(temp);
            var table=JSON.stringify(json_date);
            window.location.href=encodeURI("shopping.jsp?"+date+"&"+table);
        }
</script>
</body>
</html>
