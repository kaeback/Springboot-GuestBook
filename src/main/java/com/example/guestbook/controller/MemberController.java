package com.example.guestbook.controller;

import com.example.guestbook.model.dto.LoginDto;
import com.example.guestbook.model.dto.MemberDto;
import com.example.guestbook.model.entity.Member;
import com.example.guestbook.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RequestMapping("members")
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberMapper memberMapper;

    // 회원가입 페이지 이동
    @GetMapping("join")
    public String joinForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "members/join";
    }

    // 회원가입
    @PostMapping("join")
    public String join(@Validated @ModelAttribute MemberDto memberDto,
                       BindingResult result) {
        log.info("memberDto: {}", memberDto);

        if (result.hasErrors()) {
            return "members/join";
        }

        // 동일한 아이디로 가입된 회원정보가 있는지 확인
        Member findMember = memberMapper.findMember(memberDto.getId());
        if (findMember != null) {
            result.reject("duplicatedId", "이미 사용중인 아이디 입니다.");
            return "members/join";
        }

        // 회원가입
        Member member = new Member();
        member.setMember_id(memberDto.getId());
        member.setPassword(memberDto.getPassword());
        member.setName(memberDto.getName());
        memberMapper.addMember(member);

        return "redirect:/";
    }

    // 로그인 페이지 이동
    @GetMapping("login")
    public String loginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "members/login";
    }

    // 로그인
    @PostMapping("login")
    public String login(@Validated @ModelAttribute LoginDto loginDto,
                        BindingResult result,
                        HttpServletRequest request) {

        if (result.hasErrors()) {
            return "members/login";
        }

        // 로그인 검증
        Member findMember = memberMapper.findMember(loginDto.getId());
        if (findMember == null || !findMember.getPassword().equals(loginDto.getPassword())) {
            result.reject("loginFailed", "아이디 또는 패스워드가 맞지 않습니다");
            return "members/login";
        }

        // 로그인 처리
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", findMember);

        return "redirect:/";
    }

    // 로그아웃
    @GetMapping("logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

}
