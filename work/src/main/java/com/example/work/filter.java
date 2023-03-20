package com.example.work;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class filter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("过滤器执行了!");
        //登录，注册要放行
        String urls[]={"login.jsp","register.jsp","login","register"};
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        String  url= request.getRequestURI();
        System.out.println(url);
        for (int i = 0; i < urls.length; i++) {
            if (url.contains(urls[i])) {
                System.out.println("登录和注册界面放行");
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session=httpServletRequest.getSession();
        if(session.getAttribute("user_name") != null&&String.valueOf(session.getAttribute("user_name")).length()>0){
            System.out.println(session.getAttribute("user_name"));
            System.out.println("已经登陆放行");
            filterChain.doFilter(servletRequest,servletResponse);
        }
        else {
            session.setAttribute("login_error","请输入用户名和密码登录!");
            System.out.println("请输入用户名和密码登录!");
            String contextPath=request.getContextPath();
            HttpServletResponse response= (HttpServletResponse) servletResponse;
            response.sendRedirect(contextPath+"/login.jsp");
        }
    }
    @Override
    public void destroy() {
        Filter.super.destroy();
    }


}
