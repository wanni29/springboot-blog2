package shop.mtcoding.blogv2.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "/board/saveForm";
    }

    // 남의 것을 굳이 만들지마라. 코드가 충돌나면 터진다.
    // 나중에 그얘가 만든 index 페이지가 머지되서 뷰가 뜬다.

    // 1. 데이터 받기 (OK)
    // 2. 인증 체크 (:TODO)
    // 3. 유효성 검사 (:TODO)
    // 4. 핵심로직 호출 (핵심로직은 서비스에서 한다. 서비스가 트랜잭션을 처리한다.)
    // 5. view or data 응답
    // 비지니스 로직또한 서비스가 한다. 비지니스 로직 ? 은행에서 출금될때 돈이 있는 지 확인하는과정 생각
    // @PostMapping("/board/save")
    // public String save(BoardRequest.SaveDTO saveDTO) {
    // Board board = Board.builder()
    // .title(saveDTO.getTitle())
    // .content(saveDTO.getContent())
    // .user(User.builder().id(1).build())
    // .build();

    // boardRepository.save(board);
    // return "redirect:/";
    // }

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO saveDTO) {
        boardService.글쓰기(saveDTO, 1);
        return "redirect:/";
    }

    // localhost:8080?page=1&keyword=바나나
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "0") Integer page, HttpServletRequest request) {
        Page<Board> boardPG = boardService.게시글목록보기(page);
        request.setAttribute("boardPG", boardPG);
        request.setAttribute("prevPage", boardPG.getNumber() - 1);
        request.setAttribute("nextPage", boardPG.getNumber() + 1);
        return "/index";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Board board = boardService.상세보기(id);
        model.addAttribute("board", board); // request 랑 똑같은 역할임
        return "/board/detail";
    }

}
