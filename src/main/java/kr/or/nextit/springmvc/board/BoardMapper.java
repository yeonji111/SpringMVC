package kr.or.nextit.springmvc.board;

import kr.or.nextit.springmvc.common.SearchVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    int getBoardListCount(SearchVO vo);

    List<BoardVO> getBoardList(SearchVO vo);

    BoardVO getBoard(int searchNo);

    int insertBoard(BoardVO vo);

    int updateBoard(BoardVO vo);

    void updateHits(int boardNo);

    int deleteBoard(int deleteNo);
}
