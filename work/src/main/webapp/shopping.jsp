<%--
  Created by IntelliJ IDEA.
  User: CHY
  Date: 2022/11/17
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的购物车</title>
</head>
<body>
<input type="button" value="主页" id="homePage">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="商品页面" id="shop"><br>
<p id="ta"></p><br>
总价:<p id="price"></p>
<input type="button" value="确定" id="enter">
<script>
    document.getElementById("homePage").onclick=function () {
        window.location.href="homePage.jsp";
    }
    document.getElementById("shop").onclick=function () {
        window.location.href="shop.jsp";
    }
</script>
<script>
    //获取参数json
    var str=window.location.search;
    var str1=str.substring(str.indexOf("?")+1,str.indexOf("&"));
    var str2=str.substring(str.indexOf("&")+1);
    var date=JSON.parse(decodeURI(str1));
    var table_info=JSON.parse(decodeURI(str2));
    console.log("date");
    console.log(date);
    console.log("table_info");
    console.log(table_info);
    var table="<table border=\"1\" width=\"40%\" height=\"20%\">\n" +
        "    <tr>\n" +
        "        <th>商品编号</th>\n" +
        "        <th>商品名称</th>\n" +
        "        <th>商品价格</th>\n" +
        "        <th>操作</th>\n" +
        "    </tr>\n";
    var json_info=[];
    var temp_info=[];
    var price_info=0;
    //创建对象
    var xmlshopping = new XMLHttpRequest();
    //发送请求
    xmlshopping.open("post", "http://localhost:8080/work_war_exploded/shoppingServlet");
    xmlshopping.setRequestHeader('Content-Type', 'application/json');
    //将用户输入的值序列化成字符串JSON.stringify
    xmlshopping.send(JSON.stringify(date));
    document.getElementById("enter").onclick=function (){
        for (let i = 0; i < temp_info.length; i++) {
            date[i][1]=0;
            date[i][1]=temp_info[i][1]-json_info[i][1];
        }
        console.log(temp_info);
        console.log(json_info);
        console.log(date);
        xmlshopping = new XMLHttpRequest();
        //发送请求
        xmlshopping.open("post", "http://localhost:8080/work_war_exploded/shoppingServlet");
        xmlshopping.setRequestHeader('Content-Type', 'application/json');
        xmlshopping.send(JSON.stringify(date));
    }
    //获取响应
    xmlshopping.onreadystatechange = function () {
        //保存 XMLHttpRequest 的状态:4：请求已完成且响应已就绪,   返回请求的状态号200: "OK"
        if (this.readyState == 4 && this.status == 200) {
            json_info = JSON.parse(this.responseText);
            for (let i = 0; i < json_info.length; i++) {
                temp_info[i]=[i+1,json_info[i][1]];
            }
            for (let i = 0; i < json_info.length; i++) {
                if (json_info[i][1]>0){
                    table += "    <tr>\n" +
                        "        <th>" + table_info[i].id + "</th>\n" +
                        "        <th>" + table_info[i].name + "</th>\n" +
                        "        <th>" + table_info[i].price + "</th>\n" +
                        "        <th>" + "<span></span>" + "</th>\n";
                    price_info+=table_info[i].price * json_info[i][1];
                }
            }
            console.log("json_info");
            console.log(json_info);
            console.log("temp_info");
            console.log(temp_info);
            document.getElementById("ta").innerHTML = table;
            document.getElementById("price").innerHTML=price_info;
            var sp = document.getElementsByTagName("span");
            for (let i = 0; i < sp.length; i++) {
                sp[i].innerHTML = '<button>-</button>'+'<a></a>'+'<button>+</button>';
            }
            var a=document.getElementsByTagName("a");
            var i=0;
            var j=0;
            while(i < a.length) {
                if (temp_info[j][1]>0) {
                    console.log("i");
                    console.log(i);
                    console.log("j");
                    console.log(j);
                    a[i++].innerHTML=temp_info[j++][1];
                }
                else j++;
            }
        }
    }
    window.onclick=function (){
        var a=document.getElementsByTagName("a");
        var but=document.getElementsByTagName("button");
        console.log("button数量"+but.length);
        for (let i = 0; i < but.length; i++) {
            but[i].onclick=function (){
                console.log("点击按钮 "+ i);
                var m = 0;//计算是哪一个商品
                var n = 0;//
                m=Math.floor(i/2);
                if (i%2===1){//按钮是加号
                    var t=0;
                    for (; n < temp_info.length; n++) {
                        if (temp_info[n][1]>0) {
                            t++;
                            if (t === m + 1) {
                                temp_info[n][1] += 1;
                                a[m].innerHTML=temp_info[n][1];
                                price_info+=table_info[n].price;
                                document.getElementById("price").innerHTML=price_info;
                                if (temp_info[n][1] > 0) {
                                    but[i-1].disabled = false;
                                }
                                break;
                            }
                        }
                    }
                }
                else {
                    var t=0;
                    for (; n < temp_info.length; n++) {
                        if (temp_info[n][1]>0) {
                            t++;
                            if (t === m + 1) {
                                temp_info[n][1] -= 1;
                                a[m].innerHTML=temp_info[n][1];
                                price_info-=table_info[n].price;
                                document.getElementById("price").innerHTML=price_info;
                                if (temp_info[n][1]===0 && i%2===0){
                                    but[i].disabled = true;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
</script>
</body>
</html>
