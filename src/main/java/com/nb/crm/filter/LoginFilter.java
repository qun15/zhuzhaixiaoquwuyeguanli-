package com.nb.crm.filter;

import com.nb.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入到有没有登录的过滤器");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();
        if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }

        }
    }

    @Override
    public void destroy() {

    }
}
