package com.o2o.action.server.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// url이 /manager일 때
@WebFilter(urlPatterns = "/manager/*")
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        System.out.println("ManagerFilter for req : " + req.getServletPath() + "," + req.getMethod());

        boolean checkAuth = true;
        // if (req.getMethod().equalsIgnoreCase("get")) {
        // checkAuth = true;
        // }

        if (checkAuth) { // 세션을 받아서 
            HttpSession httpSession = req.getSession();
            if (httpSession == null) { // null이면 /login으로
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            String userId = (String) httpSession.getAttribute("userId");
            if (userId == null || userId.length() <= 0) { // null이지만 아무 값이 아닐 때?
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

        }
        chain.doFilter(request, response);
    }

}
