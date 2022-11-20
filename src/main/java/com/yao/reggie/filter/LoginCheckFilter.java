package com.yao.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.yao.reggie.common.BaseContext;
import com.yao.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.Line;
import java.io.IOException;

/**
 * @author yao
 * @create 2022-11-02 12:25
 */
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURL = request.getRequestURI();
        log.info("拦截请求：{}",requestURL);
        String[] url = new String[]{
            "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        boolean check = check(url, requestURL);
        if (check){
            log.info("可以放行");
            filterChain.doFilter(request,response);
            return;
        }
        if (request.getSession().getAttribute("employee") != null){ //改了不等于null，先直接通过
            log.info("可以放行");
            Long empId = (Long) request.getSession().getAttribute("employee");
//            Long id = 1L;
            BaseContext.set(empId);
            filterChain.doFilter(request,response);
            return;
        }
        //针对移动端用户
        if (request.getSession().getAttribute("user") != null){ //改了不等于null，先直接通过
            log.info("移动可以放行");
            Long userId = (Long) request.getSession().getAttribute("user");
//            Long id = 1L;
            BaseContext.set(userId);
            log.info("设置base：" + BaseContext.get());
            filterChain.doFilter(request,response);
            return;
        }
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("不可以放行");
        return;


    }
    public boolean check(String[] urls,String url){
        for (String u : urls) {
            if(PATH_MATCHER.match(u,url)){
                return true;
            }
        }
        return false;
    }
}
