package kr.or.nextit.springmvc.board;

import kr.or.nextit.springmvc.common.PaginationInfo;
import kr.or.nextit.springmvc.common.SearchVO;
import kr.or.nextit.springmvc.file.FileService;
import kr.or.nextit.springmvc.file.FileVO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/")
public class BoardController {
    private final BoardService service;
    private final FileService fileService;

    private final Path filePath = Paths.get("c:", "attach");


    @GetMapping("list")
    public String boardList(SearchVO vo, @RequestParam(value = "currentPageNo", defaultValue = "1") int currentPageNo, Model model) {
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(currentPageNo);
        paginationInfo.setRecordCountPerPage(10);
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
        List<FileVO> files = fileService.selectFileList(searchNo);
        model.addAttribute("board", vo);
        model.addAttribute("files", files);
        return "board/view";
    }

    @GetMapping("add")
    public String boardAddView() {
        return "board/add";
    }

    @PostMapping("add")
    public String boardAdd(BoardVO vo, Model model, List<MultipartFile> files) {
        /*
         * 스프링은 첨부파일을 MultipartFile 을 지원한다.
         * */
        vo.setWriter("a001");
        int insertBoard = service.insertBoard(vo);
        List<FileVO> fileList = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            FileVO f = new FileVO();

            // filePath를 toString으로 문자열로 변환해줘야 함.
            f.setFilePath(filePath.toString());
            f.setOriginalName(file.getOriginalFilename());
            f.setFileSize(file.getSize());
            String fileName = UUID.randomUUID().toString();
            f.setFileName(fileName);
            // 나중에 uuid로만 보여지는 파일에 실제 파일명을 붙여서 이름을 설정
            fileName = fileName + "_" + file.getOriginalFilename();
            fileList.add(f);

            // 첨부파일을 실제 경로에 등록
            // 첨부파일 등록할 경로를 체크해서 없으면 폴더 생성
            if (Files.notExists(filePath)) {
                try {
                    Files.createDirectories(filePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            // 파일 경로와 파일명을 결합하여 실제 파일을 저장
            try {
                file.transferTo(Paths.get(filePath.toString(), fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // DB에 저장
        fileService.saveFiles(insertBoard, fileList);


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

    @GetMapping("download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable("fileId") int fileId) {
        FileVO file = fileService.selectFile(fileId);
        /*
        Spring MVC에서 지원하는 응답객체(ResponseEntity)를 사용하면 편리하게 파일을 보낼 수 있다.
        요청 객체(RequestEntity)와 응답 객체(ResponseEntity)가 무엇인지 공부해보기
        => Builder Pattern 형식으로 데이터를 저장할 수 있다.
        */

        FileUrlResource resource;
        Path path = Paths.get(filePath.toString(), file.getFileName() + "_" + file.getOriginalName());

        try {
            resource = new FileUrlResource(path.toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        String fileName = URLEncoder.encode(file.getOriginalName(), StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + fileName + "\"")
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.getFileSize()))
                .body(resource);
    }
}
