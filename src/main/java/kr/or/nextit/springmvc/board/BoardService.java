package kr.or.nextit.springmvc.board;

import kr.or.nextit.springmvc.common.SearchVO;
import kr.or.nextit.springmvc.member.Member;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private final BoardMapper mapper;
    public BoardService(BoardMapper mapper) {
        this.mapper = mapper;
    }

    public int getBoardListCount(SearchVO vo) {
        return mapper.getBoardListCount(vo);
    }

    public List<BoardVO> getBoardList(SearchVO vo) {
        return mapper.getBoardList(vo);
    }

    public BoardVO getBoard(int searchNo) {
        // 게시글 조회시 카운트가 증가할 때 조회수에 따른 등급이나  인기글로 설정하는 등
        // 민감한 사항은 정책을 세워서 이곳(service)에서 작업해준다.

        mapper.updateHits(searchNo);
        return mapper.getBoard(searchNo);
    }

    public int insertBoard(BoardVO vo) {
        mapper.insertBoard(vo);
        return vo.getNo();
    }

    public int updateBoard(BoardVO vo) {
        return mapper.updateBoard(vo);
    }

    public int deleteBoard(int deleteNo) {
        return mapper.deleteBoard(deleteNo);
    }
}
