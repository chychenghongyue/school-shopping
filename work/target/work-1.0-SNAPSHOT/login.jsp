<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>登陆界面</title>
</head>
<body>
<h1>登陆界面</h1><br/>
<div id="error"><h3>${sessionScope.login_error}</h3></div>
<script>
    let error = document.getElementById("error").style.color ='red';
</script>
<form method="post" action="loginServlet" >
    用户名:<input type="text" name="user_name" value="${cookie.user_name.value}" id="name"><br>
    <span id="name_error"></span><br>
    密&nbsp;&nbsp;&nbsp;码:<input type="password" name="user_password" value="${cookie.user_password.value}" id="password"><br>
    <span id="password_error"></span><br>
    <label><input type="radio" name="two" value="ok">两周内免登录</label><br><br>
    <input type="submit" value="登录" id="auto">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="reset" value="重新输入">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" value="注册页面" id="register">

    <script>
        window.onload=function () {
            var name_check=document.getElementById("name").value;
            if(name_check.length>=2&&name_check.length<=12){
                var password_check=document.getElementById("password").value;
                if (password_check.length >= 6 && password_check.length <= 12) {
                    document.getElementById("auto").click();
                }
            }
        }
        document.getElementById("register").onclick=function () {
            window.location.href="register.jsp";
        }
        var flag_1;
        var flag_2;
        {
            document.getElementById("name").onblur = function () {
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
            document.getElementById("password").onblur = function () {
                let password_value = this.value;
                if (password_value.length >= 6 && password_value.length <= 12) {
                    document.getElementById("password_error").style.color="";
                    document.getElementById("password_error").innerHTML="";
                    flag_2=true;
                } else {
                    document.getElementById("password_error").style.color="red";
                    document.getElementById("password_error").innerHTML="密码长度不符合规范";
                    flag_2=false;
                }
            };
        }
        document.getElementById("submit").onclick=function () {
            if (flag_1 && flag_2) {
                return true;
            }
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
