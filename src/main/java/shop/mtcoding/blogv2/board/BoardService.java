package shop.mtcoding.blogv2.board;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2.board.BoardRequest.DetailDTO;
import shop.mtcoding.blogv2.user.User;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void 글쓰기(BoardRequest.SaveDTO saveDTO, int sessionUserId) {
        Board board = Board.builder()
                .title(saveDTO.getTitle())
                .content(saveDTO.getContent())
                .user(User.builder().id(sessionUserId).build())
                .build();

        boardRepository.save(board);
    }

    public Page<Board> 게시글목록보기(Integer page) {
        Pageable pageable = PageRequest.of(page, 3, Sort.Direction.DESC, "id");
        return boardRepository.findAll(pageable);
    }

    public Board 상세보기(Integer id) {
        // board 만 가져오면 된다!!
        return boardRepository.findById(id).get();
    }

    @Transactional
    public void 게시글수정하기(Integer id, BoardRequest.DetailDTO detailDTO) {
        // 1. 조회 (영속화)
        Optional<Board> boardOP = boardRepository.findById(id);
        if (boardOP.isPresent()) {
            Board board = boardOP.get();
            board.setTitle(detailDTO.getTitle());
            board.setContent(detailDTO.getContent());
        } else {
            throw new RuntimeException(id + "는 찾을 수 없습니다.");
        }
        // 더티 체킹

    }

    public Board 게시물상세보기(Integer id) {
        // board 만 가져오면 된다!!
        Optional<Board> boardOP = boardRepository.findById(id);
        if (boardOP.isPresent()) {
            return boardOP.get();
        } else {
            throw new RuntimeException(id + "는 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void 게시글삭제하기(Integer id) {
        try {
            // write 다잡기
            boardRepository.deleteById(6);
        } catch (Exception e) {
            throw new RuntimeException("6번은 없어요");
        }
    }

}
