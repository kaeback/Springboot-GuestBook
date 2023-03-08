package com.example.guestbook.controller;

import com.example.guestbook.model.entity.GuestBook;
import com.example.guestbook.model.entity.Member;
import com.example.guestbook.repository.GuestBookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("guestbooks")
@Controller
public class GuestBookController {

    private final GuestBookMapper guestBookMapper;

    // 방명록 전체 리스트
    @GetMapping("list")
    public String list() {
        return "guestbooks/list";
    }

    // 방명록 쓰기 페이지 이동
    @GetMapping("write")
    public String writeForm() {
        return "guestbooks/write";
    }

    // 방명록 쓰기
    @PostMapping("write")
    public String write(@SessionAttribute("loginMember") Member loginMember,
                        @ModelAttribute GuestBook guestBook) {
        guestBook.setMember_id(loginMember.getMember_id());
        log.info("guestBook: {}", guestBook);

        guestBookMapper.addGuestBook(guestBook);

        return "guestbooks/list";
    }

    // 방명록 수정 페이지 이동
    @GetMapping("update/{guestbook_id}")
    public String update(@SessionAttribute("loginMember") Member loginMember,
                         @PathVariable Long guestbook_id,
                         Model model) {

        GuestBook guestBook = guestBookMapper.findGuestBook(guestbook_id);
        model.addAttribute("guestBook", guestBook);

        return "guestbooks/update";
    }

    // 방명록 수정


    // 방명록 삭제


}
