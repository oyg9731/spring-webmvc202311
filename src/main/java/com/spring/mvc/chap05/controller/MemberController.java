package com.spring.mvc.chap05.controller;


import com.spring.mvc.chap05.dto.request.LoginRequestDTO;
import com.spring.mvc.chap05.dto.request.SignUpRequestDTO;
import com.spring.mvc.chap05.service.LoginResult;
import com.spring.mvc.chap05.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/members")
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 가입 양식 요청
    @GetMapping("/sign-up")
    public String signUp() {
        log.info("/members/sign-up GET : forwarding to sign-up.jsp");
        return "members/sign-up";
    }

    // 아이디, 이메일 중복체크 비동기 요청 처리
    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<?> check(String type, String keyword) {
        log.info("/members/check?type={}&keyword={} ASYNC GET", type, keyword);
        boolean flag = memberService.checkDuplicateValue(type, keyword);
        log.debug("중복체크 결과 : {}", flag);
        return ResponseEntity.ok().body(flag);
    }

    // 회원가입 처리
    @PostMapping("/sign-up")
    public String signUp(SignUpRequestDTO dto) {
        log.info("/member/sign-up POST !");
        log.debug("parameter: {}", dto);
        boolean flag = memberService.join(dto);
        return flag ? "redirect:/board/list" : "redirect:/members/sign-up";
    }

    // 로그인 양식 요청
    @GetMapping("/sign-in")
    public String string(){
        log.info("/members/sign-in GET - forwading to sign-in.jsp");

        return "members/sign-in";
    }

    // 로그인 검증 요청
    @PostMapping("/sign-in")
    public String signIn(LoginRequestDTO dto
                         // Model에 담긴 데이터는 리다이렉트시 jsp로 가지 않는다
                         // 왜냐면 리다이렉트는 요청이 2번들어가서 첫번째 요청시 보관한 데이터가 소실된다.
            , RedirectAttributes ra){
        log.info("/members/sing-in POST! ");
        log.debug("parameter: {}", dto);

        LoginResult result = memberService.authenticate(dto);
        log.debug("login result: {}", result);

        ra.addFlashAttribute("msg", result);

        if (result == LoginResult.SUCCESS){ // 로그인 성공시
            return "redirect:/board/list";
        }

        return "redirect:/members/sign-in"; // 로그인 실패시
    }

}
