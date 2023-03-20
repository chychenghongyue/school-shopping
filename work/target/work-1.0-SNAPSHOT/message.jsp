
<%--
  Created by IntelliJ IDEA.
  User: CHY
  Date: 2022/11/04
  Time: 13:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>留言板</title>
</head>
<body>
<input type="button" value="主页" id="homePage"><br>
请输入收件人:<br>
<input type="text" id="endname"><br>
请输入留言内容:<br>
<textarea rows="8" cols="60"  id="info"></textarea><br>
<input type="button" value="确定" id="enter"><br>
<hr>
<p id="table1"></p><p id="table2"></p><br>

<script>
    var table1;
    var table2;
    var mess={
        "startname":"null",
        "endname":"null",
        "info":"null"
    }
    var xmlmessage = new XMLHttpRequest();
    //发送请求
    xmlmessage.open("post", "http://localhost:8080/work_war_exploded/messageServlet");
    xmlmessage.setRequestHeader('Content-Type', 'application/json');
    //将用户输入的值序列化成字符串JSON.stringify
    xmlmessage.send(JSON.stringify(mess));
    document.getElementById("enter").onclick=function (){
        xmlmessage.open("post", "http://localhost:8080/work_war_exploded/messageServlet");
        xmlmessage.setRequestHeader('Content-Type', 'application/json');
        mess.endname=document.getElementById("endname").value;
        mess.info=document.getElementById("info").value;
        //将用户输入的值序列化成字符串JSON.stringify
        xmlmessage.send(JSON.stringify(mess));
    }
    xmlmessage.onreadystatechange = function () {
        //保存 XMLHttpRequest 的状态:4：请求已完成且响应已就绪,   返回请求的状态号200: "OK"
        if (this.readyState == 4 && this.status == 200) {
            var name='<%=request.getSession().getAttribute("user_name")%>';
            console.log(name);
            table1="<table border=\"2\" width=\"20%\" height=\"10%\">\n"+
            "    <tr>\n" +
            "        <th>发件箱</th>\n" +
            "    </tr>\n";
            table2="<table border=\"2\" width=\"20%\" height=\"10%\">\n"+
                "    <tr>\n" +
                "        <th>收件箱</th>\n" +
                "    </tr>\n";
            var mess = JSON.parse(this.responseText);
            for (let i=0; i < mess.length; i++) {
                if (mess[i].startname===name){
                    table1 += "<tr>" +
                        "<th>发件人:" + mess[i].startname + "<br>" +
                        "收件人:" + mess[i].endname + "<br>" +
                        "信息:" + mess[i].info + "</th></tr>";
                    }
                }
            for (let j=0;j<mess.length;j++){
                if (mess[j].startname!==name) {
                    table2 += "<tr>" +
                        "<th>发件人:" + mess[j].startname + "<br>" +
                        "收件人:" + mess[j].endname + "<br>" +
                        "信息:" + mess[j].info + "</th></tr>";
                }
            }
            document.getElementById("table1").innerHTML = table1;
            document.getElementById("table2").innerHTML = table2;
        }
    }

    document.getElementById("homePage").onclick=function () {
        window.location.href="homePage.jsp";
    }

</script>
</body>
</html>
