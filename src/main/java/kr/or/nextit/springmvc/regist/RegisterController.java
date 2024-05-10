package kr.or.nextit.springmvc.regist;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register/")
public class RegisterController {

//    @GetMapping("/test")
//    public ModelAndView mnv(){
//        ModelAndView modelAndView = new ModelAndView("test");
//        modelAndView.addObject("msg","test");
//        return modelAndView;
//    }

    @GetMapping("step1")
    public String step1() {
        return "register/step1";
    }

    @PostMapping("step2")
    public String step2(@RequestParam(value="agree",defaultValue = "false") boolean agreement) {
        System.out.println("agree: "+agreement);
        if(agreement){
            return "register/step2";
        }
        return "redirect:/register/step1";
    }

    @PostMapping("step3")
    public String step3(RegisterRequest register) {
        System.out.println("이름: " + register.getName());
        System.out.println("이메일: " + register.getEmail());
        System.out.println("비밀번호: " + register.getPassword());
        System.out.println("비밀번호 확인: " + register.getConfirmPassword());

        return "register/step3";
    }
}
