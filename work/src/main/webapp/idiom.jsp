<%--
  Created by IntelliJ IDEA.
  User: CHY
  Date: 2022/11/10
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>成语接龙</title>
</head>
<body>
<input type="button" value="主页" id="homePage">
<script>
    document.getElementById("homePage").onclick=function () {
        window.location.href="homePage.jsp";
    }
</script>
<h2>成语接龙</h2>
<h4>请您输入一个成语</h4><br>
<input type="text" id="name">
<input type="button" value="确定" id="button"><br><br>
<input type="button" value="投降" id="defeat"><br><br>
<span id="info"></span><br>
<hr>
    <div id="idiom"></div>
<script>
    var idiom_json_1={
        "name":"null",
        "spell":"null"
    };
    var num=1;
    var table="<table border=\"1\" width=\"20%\" height=\"20%\">\n" +
        "    <tr>\n" +
        "        <th>序号</th>\n" +
        "        <th>成语</th>\n" +
        "    </tr>\n";
    document.getElementById("defeat").onclick=function () {
        document.getElementById("info").innerHTML="您认输了!";
        document.getElementById("info").style.color="red";
        document.getElementById("name").onreset;
        document.getElementById("name").disabled="disabled";
    }
    document.getElementById("button").onclick=function () {
        var name = document.getElementById("name").value;
        if (name.length >= 4) {
            var idiom_json_2={
                "name":name,
                "spell":"null"
            };
            document.getElementById("info").innerHTML="";
            document.getElementById("info").style.color="";
            idiom_json_2.name=name;
            //创建对象
            var xmlHttp = new XMLHttpRequest();
            //发送请求
            xmlHttp.open("post", "http://localhost:8080/work_war_exploded/idiomServlet");

            //获取响应
            xmlHttp.onreadystatechange = function () {
                //保存 XMLHttpRequest 的状态:4：请求已完成且响应已就绪,   返回请求的状态号200: "OK"
                if (this.readyState == 4 && this.status == 200) {
                    var json=JSON.parse(this.responseText);
                    if(json.name!="info"){
                        idiom_json_1.name=json.name;
                        idiom_json_1.spell=json.spell;
                        document.getElementById("info").innerHTML="";
                        document.getElementById("info").style.color="";
                        table=table+"    <tr>\n" +
                            "<th>"+num+++ "</th>\n"+
                            "<th>"+idiom_json_2.name+"</th>\n"+
                            "    </tr>\n"+
                            "    <tr>\n" +
                            "<th>"+num+++"</th>\n"+
                            "<th>"+idiom_json_1.name+"</th>\n"+
                            "    </tr>\n";
                        document.getElementById("idiom").innerHTML=table;
                    }
                    else {
                        document.getElementById("info").innerHTML=json.spell;
                        document.getElementById("info").style.color="red";
                    }
                }
            }
            xmlHttp.setRequestHeader('Content-Type','application/json');
            //将用户输入的值序列化成字符串JSON.stringify
            xmlHttp.send(JSON.stringify([idiom_json_1,idiom_json_2]));
        }
        else {
            document.getElementById("info").innerHTML="请输入正确的成语!";
            document.getElementById("info").style.color="red";
        }
    }

</script>
</body>
</html>
