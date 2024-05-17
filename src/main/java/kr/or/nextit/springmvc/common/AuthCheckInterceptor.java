package kr.or.nextit.springmvc.common;

import kr.or.nextit.springmvc.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class AuthCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // return: true => 사용자가 원래 가려던 사이트로 이동
        // return: false => 개발자가 의도한 사이트로 이동시킨다.(리다이렉트 해줘야한다.),
        // -> 비로그인 상태 시 로그인 페이지로 이동하는게 개발자의 의도
        log.debug("request URI : {}",request.getRequestURI());

        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");
        if(member == null){
            response.sendRedirect("/login?location=" + requestURI);
            return false;
        }
        return true;
    }


}
