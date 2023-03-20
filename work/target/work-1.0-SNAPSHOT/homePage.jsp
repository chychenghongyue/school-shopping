<%--
  Created by IntelliJ IDEA.
  User: CHY
  Date: 2022/11/09
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>主页</title>
</head>
<body>
<h3 id="info">${sessionScope.user_name},欢迎登录!</h3><br>
<input type="button" value="用户信息" id="user_info"><br><br>
<input type="button" value="成语接龙" id="idiom"><br><br>
<input type="button" value="购物界面" id="shop"><br><br>
<input type="button" value="留言板" id="message"><br>
<script>
    document.getElementById("user_info").onclick=function () {
        window.location.href="user_info.jsp";
    }
    document.getElementById("idiom").onclick=function () {
        window.location.href="idiom.jsp";
    }
    document.getElementById("shop").onclick=function () {
        window.location.href="shop.jsp";
    }
    document.getElementById("message").onclick=function () {
        window.location.href="message.jsp";
    }
</script>
</body>
</html>
