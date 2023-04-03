package com.zzh.riggie.filter;

import com.alibaba.fastjson.JSON;
import com.zzh.riggie.common.BaseContext;
import com.zzh.riggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* 检查用户是否登陆
* */
@WebFilter(filterName = "loginCheakFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheakFilter implements Filter {
    //路径匹配器
    private static final AntPathMatcher PATH_MATCHER= new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        log.info("当前url: " + uri);

        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/upload",
                "/common/download",
                "/user/login",
                "/user/sendMsg"
        };

        boolean cheak = cheak(uri, urls);

        if(cheak){
            filterChain.doFilter(request, response);
            return;
        }

        if(request.getSession().getAttribute("employee")!=null) {
            log.info("当前用户id: " + request.getSession().getAttribute("employee"));
            Long employeeId = (Long)request.getSession().getAttribute("employee");

            BaseContext.setThreadLocal(employeeId);

            filterChain.doFilter(request, response);
            return;
        }

        if(request.getSession().getAttribute("user")!=null) {
            log.info("当前用户id: " + request.getSession().getAttribute("user"));
            Long UserId = (Long)request.getSession().getAttribute("user");

            BaseContext.setThreadLocal(UserId);

            filterChain.doFilter(request, response);
            return;
        }

        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    public boolean cheak(String uri,String[] urls){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, uri);
            if(match) {
                return true;
            }
        }
        return false;
    }
}
