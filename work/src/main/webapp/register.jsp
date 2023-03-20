<%--
  Created by IntelliJ IDEA.
  User: CHY
  Date: 2022/11/02
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册界面</title>
</head>
<body>
<h1>注册界面</h1>
<div id="error"><h3>${register_info}</h3></div>
<script>
  let error = document.getElementById("error").style.color ='red';
</script>
<form method="post" action="registerServlet">
  请输入用户名:<input type="text" name="user_name" id="name"><br>
  <span id="name_error"></span><br>
  请您输入密码:<input type="text" name="user_password_1" id="password_1"><br>
  <span id="password_error_1"></span><br>
  请您再次输入:<input type="text" name="user_password_2" id="password_2"><br>
  <span id="password_error_2"></span><br>
  <input type="submit" value="提交" id="submit">&nbsp;&nbsp;&nbsp;
  <input type="reset" value="重新输入">&nbsp;&nbsp;&nbsp;
  <input type="button" value="登录页面" id="login">
  <script>
    document.getElementById("login").onclick=function () {
      window.location.href="login.jsp";
    }
    var flag_1;
    var flag_2;
    var flag_3;
    {
      document.getElementById("name").onchange = function () {
        let name_value = this.value;
        if(name_value.length>=2&&name_value.length<=12){
          document.getElementById("name_error").style.color="";
          document.getElementById("name_error").innerHTML="";
          flag_1=true;
        }
        else{
          document.getElementById("name_error").style.color="red";
          document.getElementById("name_error").innerHTML="用户名长度不符合规范";
          flag_1=false;
        }
      }
    }
    {
      document.getElementById("password_1").onchange = function () {
        let password_value = this.value;

        if (password_value.length >= 6 && password_value.length <= 12) {
          document.getElementById("password_error_1").style.color="";
          document.getElementById("password_error_1").innerHTML="";
          flag_2=true;
        } else {
          document.getElementById("password_error_1").style.color="red";
          document.getElementById("password_error_1").innerHTML="密码长度不符合规范";
          flag_2=false;
        }
      }
    }
    {
      document.getElementById("password_2").onchange = function () {
        let password_value = this.value;
        let password_value_1=document.getElementById("password_1").value;
        if (password_value==password_value_1) {
          document.getElementById("password_error_2").style.color="";
          document.getElementById("password_error_2").innerHTML="";
          flag_3=true;
        } else {
          document.getElementById("password_error_2").style.color="red";
          document.getElementById("password_error_2").innerHTML="两次输入的密码不一致";
          flag_3=false;
        }
      }
    }
    document.getElementById("submit").onclick=function () {
      if(flag_1&&flag_2&&flag_3)
        return true;
      else {
        document.getElementById("error").innerHTML="请仔细检查您输入的用户名和密码";
        document.getElementById("error").style.color="red";
        return false;
      }
    }
  </script>
</form>
</body>
</html>
