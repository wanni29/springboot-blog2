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

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2.reply.Reply;
import shop.mtcoding.blogv2.reply.ReplyRepository;
import shop.mtcoding.blogv2.user.User;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

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
        Optional <Board> boardOP =  boardRepository.mFindByIdJoinRepliesInUser(id);
        if(boardOP.isPresent()) {
            return boardOP.get();
        } else {
            throw new MyException(id + "는 찾을 수 없습니다.");
        }
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
            throw new MyException(id + "는 찾을 수 없습니다.");
        }
        // 더티 체킹

    }

    public Board 게시물상세보기(Integer id) {
        // board 만 가져오면 된다!!
        Optional<Board> boardOP = boardRepository.mFindByIdJoinRepliesInUser(id);
        if (boardOP.isPresent()) {
            return boardOP.get();
        } else {
            throw new MyException(id + "는 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void 게시글삭제하기(Integer id) {
        List<Reply> replies = replyRepository.findByBoardId(id);
        for (Reply reply : replies) {
            reply.setBoard(null);
            replyRepository.save(reply);
        }
        try {
            // write 다잡기
            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new MyException(id + "를 찾을 수 없어요");
        }
    }

}
