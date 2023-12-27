package com.spring.mvc.interceptor;

import com.spring.mvc.util.LoginUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
@Slf4j
public class AfterLoginInterceptor implements HandlerInterceptor {

    // 로그인 한 이후 비회원만 접근할 수 있는 페이지 접근 차단

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        log.info("after login interector excute!!!");

        HttpSession session = request.getSession();

        if (LoginUtils.isLogin(session)){
            response.sendRedirect("/");
            return false; //내보내기
        }
        return true; // 들여보내기

    }
}
