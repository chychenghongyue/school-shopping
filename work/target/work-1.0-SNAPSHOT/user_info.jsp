<%--
  Created by IntelliJ IDEA.
  User: CHY
  Date: 2022/11/04
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户信息界面</title>
</head>
<body>
<input type="button" value="主页" id="homePage">
<h1>用户信息界面</h1>
    年龄:<input type="text" name="age" id="age"><br>
    性别:
        <label><input type="radio" name="sex" value="男">男</label>
        <label><input type="radio" name="sex" value="女">女<br></label><br>
    爱好:
        <label><input type="checkbox" name="hobby" value="0">唱歌</label>
        <label><input type="checkbox" name="hobby" value="1">跳舞</label>
        <label><input type="checkbox" name="hobby" value="2">rap</label>
        <label><input type="checkbox" name="hobby" value="3">篮球</label><br>
    家庭住址:
    <select name="location_1" id="location_1">
        <option value="西安">西安</option>
        <option value="北京">北京</option>
        <option value="上海">上海</option>
        <option value="广州">广州</option>
        <option value="深圳">深圳</option>
    </select>
    <br>
    详细住址:<br>
    <label>
        <textarea rows="2" cols="20" name="location_2" id="location_2"></textarea>
    </label><br>
    <input type="submit" value="提交" id="enter">
    <input type="reset" value="重新输入">
<script>
    var info={
        "age":0,
        "sex":"null",
        "hobby":[],
        "location_1":"null",
        "location_2":"null"
    }
    var xmluser_info = new XMLHttpRequest();
    //发送请求
    xmluser_info.open("post", "http://localhost:8080/work_war_exploded/user_infoServlet");
    xmluser_info.setRequestHeader('Content-Type', 'application/json');
    //将用户输入的值序列化成字符串JSON.stringify
    xmluser_info.send(JSON.stringify(info));

    xmluser_info.onreadystatechange = function () {
        //保存 XMLHttpRequest 的状态:4：请求已完成且响应已就绪,   返回请求的状态号200: "OK"
        if (this.readyState == 4 && this.status == 200) {
            info = JSON.parse(this.responseText);
            console.log(info);
            var age=document.getElementsByName("age");
            for (let i = 0; i < age.length; i++) {
                age[i].value=info.age;
            }
            var sex=document.getElementsByName("sex");
            for (let i = 0; i < sex.length; i++) {
                if (sex[i].value===info.sex){
                    sex[i].checked=true;
                }
            }
            var hobby=document.getElementsByName("hobby");
            for (let i = 0; i < info.hobby.length; i++) {
                hobby[info.hobby[i]].checked=true;
            }
            var location_1=document.getElementsByName("location_1");
            for (let i = 0; i < location_1.length; i++) {
                if (location_1[i].value===info.location_1){
                    location_1[i].selected=true;
                }
            }
            var location_2=document.getElementsByName("location_2");
            for (let i = 0; i < location_2.length; i++) {
                location_2[i].value=info.location_2;
            }
        }
    }

    document.getElementById("enter").onclick=function (){
        xmluser_info = new XMLHttpRequest();
        //发送请求
        xmluser_info.open("post", "http://localhost:8080/work_war_exploded/user_infoServlet");
        xmluser_info.setRequestHeader('Content-Type', 'application/json');
        //将用户输入的值序列化成字符串JSON.stringify
        info.age=document.getElementById("age").value;
        var sex=document.getElementsByName("sex");
        for (let i = 0; i < sex.length; i++) {
            if (sex[i].checked){
                info.sex=sex[i].value;
            }
        }
        var hobby=document.getElementsByName("hobby");
        var temp=[];
        var j=0;
        for (let i = 0; i < hobby.length; i++) {
            if (hobby[i].checked){
                temp[j++]=hobby[i].value;
            }
        }
        info.hobby=temp;
        var lo=document.getElementsByName("location_1");
        info.location_1=lo[0].value;
        info.location_2=document.getElementById("location_2").value;
        console.log(info);
        xmluser_info.send(JSON.stringify(info));
    }



    document.getElementById("homePage").onclick=function () {
        window.location.href="homePage.jsp";
    }
</script>
</body>
</html>
