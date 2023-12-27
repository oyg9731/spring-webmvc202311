package com.spring.mvc.interceptor;

import com.spring.mvc.chap05.repository.BoardMapper;
import com.spring.mvc.util.LoginUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.spring.mvc.util.LoginUtils.*;


/*
     - 인터셉터: 컨트롤러에 요청이 들어가기 전, 후에
         공통적으로 처리할 코드나 검사할 일들을 정의해 놓는 클래스
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class BoardInterceptor implements HandlerInterceptor {

    private final BoardMapper boardMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        // 로그인을 안했으면 글쓰기, 글수정, 글삭제 요청을 튕겨낼 것
        if (!isLogin(session)) {
            log.info("this request ({}) is denied!!", request.getRequestURI());
            response.sendRedirect("/members/sign-in");
            return false;
        }

        // 삭제요청이 들어올 때 서버에서 다시한번 관리자인지 내가쓴글인지 체크

        // 현재 요청이 삭제요청인지 확인
        String uri = request.getRequestURI();
        if (uri.contains("delete")) {

            // 글번호는 어떻게 구함??   ->  쿼리스트링에서 구함 ?뒤에 붙어있음
            String bno = request.getParameter("bno");

            // 로그인한 계정명과 게시물의 계정명이 일치하는지 체크
            // ?? 로그인한 계정명은 어떻게 구함?   ->   세션에서 가져옴
            // ?? 게시물 계정명은 어떻게 구함??    ->   DB에서 가져옴
            String targetAccount = boardMapper.findOne(Integer.parseInt(bno)).getAccount();

            // 만약에 관리자라면  -->  삭제통과!
            if (isAdmin(session)) return true;

            // 만약에 내가 쓴 글이 아니면 -->  접근권한이 없다는 안내페이지로 이동시킴
            if (!isMine(session, targetAccount)) {
                response.sendRedirect("/access-deny");
                return false;
            }

        }

        return true;
    }
}
