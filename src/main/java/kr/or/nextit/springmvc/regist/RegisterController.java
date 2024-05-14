package kr.or.nextit.springmvc.regist;

import kr.or.nextit.springmvc.exception.DuplicateMemberException;
import kr.or.nextit.springmvc.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/register/")
@RequiredArgsConstructor
@Slf4j
public class RegisterController {
    private final RegisterService service;

    @GetMapping("step1")
    public String step1() {
        return "register/step1";
    }
    @PostMapping("step2")
    public String step2(boolean agree) {
        log.debug("agree: {}", agree);
        if (agree){
            return "register/step2";
        }
        return "redirect:/register/step1";
    }
    @PostMapping("step3")
    public String step3(@ModelAttribute RegisterRequest register) {
        // 회원 등록 서비스
        try {
            service.register(register);
            return "register/step3";
        } catch (DuplicateMemberException e) {
            return "register/step2";
        }
    }

    @GetMapping("list")
    public String selectAll(Model model) {
        List<Member> members = service.selectAll();
        model.addAttribute("members", members);
        return "register/list";
    }
}
