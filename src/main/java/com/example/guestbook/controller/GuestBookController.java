package com.example.guestbook.controller;

import com.example.guestbook.model.entity.GuestBook;
import com.example.guestbook.model.entity.Member;
import com.example.guestbook.repository.GuestBookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("guestbooks")
@Controller
public class GuestBookController {

    private final GuestBookMapper guestBookMapper;

    // 방명록 전체 리스트
    @GetMapping("list")
    public String list(Model model) {
    	List<GuestBook> guestbooks = guestBookMapper.findAllGuestBooks();
    	model.addAttribute("guestbooks", guestbooks);
    	log.info("guestbooks: {}", guestbooks);
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

        return "redirect:/guestbooks/list";
    }

    // 방명록 수정 페이지 이동
    @GetMapping("update/{guestbook_id}")
    public String updateForm(@SessionAttribute("loginMember") Member loginMember,
                         @PathVariable Long guestbook_id,
                         Model model) {

        GuestBook guestBook = guestBookMapper.findGuestBook(guestbook_id);
        log.info("guestBook: {}", guestBook);
        model.addAttribute("guestBook", guestBook);

        return "guestbooks/update";
    }

    // 방명록 수정
    @PostMapping("update/{guestbook_id}")
    public String update(@SessionAttribute("loginMember") Member loginMember,
    					 @PathVariable Long guestbook_id,
    					 @ModelAttribute GuestBook updateGuestBook) {
    	
    	log.info("updateGuestBook: {}", updateGuestBook);
    	// 수정하려는 게시물의 작성자가 로그인 사용자 아이디와 같은지 확인
    	GuestBook guestBook = guestBookMapper.findGuestBook(guestbook_id);
    	if (guestBook.getMember_id().equals(loginMember.getMember_id())) {
    		updateGuestBook.setGuestbook_id(guestbook_id);
    		guestBookMapper.updateGuestBook(updateGuestBook);;
    	}
    	
    	return "redirect:/";
    }


    // 방명록 삭제
    @GetMapping("remove")
    public String remove(@SessionAttribute("loginMember") Member loginMember,
    					 @RequestParam Long guestbook_id) {
    	log.info("guestbook_id: {}", guestbook_id);
    	
    	// 삭제하려는 게시물의 작성자가 로그인 사용자 아이디와 같은지 확인
    	GuestBook guestBook = guestBookMapper.findGuestBook(guestbook_id);
    	if (guestBook.getMember_id().equals(loginMember.getMember_id())) {
    		guestBookMapper.deleteGuestBook(guestbook_id);
    	}
    	
    	return "redirect:/";
    }


}
