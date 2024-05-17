package kr.or.nextit.springmvc.common;

import kr.or.nextit.springmvc.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class WriterCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("writer request URI:{}",request.getRequestURI());
//        return false;
//        시도중
//        String requestURI = request.getRequestURI();
//        HttpSession session = request.getSession();
//        Member member = (Member) session.getAttribute("member");
//        if (member.equals("admin")) {
//            response.sendRedirect("/update?location=" + requestURI);
//            return true;
//        }
        return false;
    }
}

