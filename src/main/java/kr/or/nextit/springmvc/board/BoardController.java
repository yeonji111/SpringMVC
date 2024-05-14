package kr.or.nextit.springmvc.board;

import kr.or.nextit.springmvc.common.PaginationInfo;
import kr.or.nextit.springmvc.common.SearchVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board/")
public class BoardController {
    private final BoardService service;
    public BoardController(BoardService service) {
        this.service = service;
    }

    @GetMapping("list")
    public String boardList(SearchVO vo, @RequestParam(value = "currentPageNo", defaultValue = "1") int currentPageNo, Model model) {
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(currentPageNo);
        paginationInfo.setRecordCountPerPage(3);
        paginationInfo.setPageSize(5);

        int totalCount = service.getBoardListCount(vo);
        paginationInfo.setTotalRecordCount(totalCount);
        // 페이징된 게시글 목록을 가져오기 위해
        vo.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
        vo.setLastRecordIndex(paginationInfo.getLastRecordIndex());

        List<BoardVO> list = service.getBoardList(vo);
        model.addAttribute("boards", list);
        model.addAttribute("pagination", paginationInfo);

        return "board/list";
    }

    @GetMapping("view")
    public String boardView(@RequestParam(value = "no") int searchNo, Model model) {
        BoardVO vo = service.getBoard(searchNo);
        model.addAttribute("board", vo);
        return "board/view";
    }

    @GetMapping("add")
    public String boardAddView() {
        return "board/add";
    }

    @PostMapping("add")
    public String boardAdd(BoardVO vo, Model model) {
        vo.setWriter("a001");
        int insertBoard = service.insertBoard(vo);
        if (insertBoard > 0) {
            // 등록 성공
            return "redirect:/board/list";
        } else {
            // 등록 실패
            model.addAttribute("msg", "등록 실패");
            return "board/add";
        }
    }

    @GetMapping("update")
    public String boardUpdateView(@RequestParam("no") int searchNo, Model model) {
        BoardVO vo = service.getBoard(searchNo);
        model.addAttribute("board", vo);
        return "board/update";
    }

    @PostMapping("update")
    public String boardUpdate(BoardVO vo, Model model) {
        vo.setWriter("a001");
        int updated = service.updateBoard(vo);
        if (updated > 0) {
            // 등록 성공
            return "redirect:/board/list";
        } else {
            // 등록 실패
            model.addAttribute("msg", "수정 실패");
            return "/board/update";
        }
    }
    @GetMapping("delete")
    public String boardDelete(@RequestParam(value = "no") int deleteNo, Model model) {
        int deletedBoard = service.deleteBoard(deleteNo);
        if (deletedBoard > 0) {
            return "redirect:/board/list";
        } else {
            model.addAttribute("msg", "삭제 실패");
            return "board/update";
        }
    }
}
