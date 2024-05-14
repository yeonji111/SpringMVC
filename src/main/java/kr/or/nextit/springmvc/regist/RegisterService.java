package kr.or.nextit.springmvc.regist;

import kr.or.nextit.springmvc.exception.DuplicateMemberException;
import kr.or.nextit.springmvc.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class RegisterService {
    private final RegisterMapper mapper;
    public RegisterService(RegisterMapper mapper) {
        this.mapper = mapper;
    }
    public List<Member> selectAll() {
        return mapper.selectAll();
    }

    public void register(RegisterRequest request) {
        log.debug("id: {}", request.getId());
        Member member = mapper.selectById(request.getId());
        if (member != null) {
            throw new DuplicateMemberException("아이디 중복: " + request.getId());
        }
        mapper.insert(request);
    }
}
