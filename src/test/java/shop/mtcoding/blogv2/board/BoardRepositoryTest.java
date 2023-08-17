package shop.mtcoding.blogv2.board;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blogv2.user.User;

@DataJpaTest // 모든 repository, entitymanager 만 메모리에 뜬다.
public class BoardRepositoryTest {

    // 실험 대상 : save(), findById(), findAll(), count(), deleteById()

    @Autowired
    private BoardRepository boardRepository;

    // 객체를 만들어서 넣으면 쿼리로 만들어줌!
    @Test
    public void save_test() {
        // 비영속 객체 -> 영속화 되지 않은 객체 -> 디비에 저장되기 전의 객체

        Board board = Board.builder()
                .title("제목6")
                .content("내용6")
                .user(User.builder().id(1).build())
                .build();

        System.out.println("테스트 : " + board.getId());

        // 영속 객체 -> 영속화 된 객체 -> 디비에 저장된 객체
        boardRepository.save(board);
        System.out.println("테스트 : " + board.getId());

    }

    @Test
    public void findAll_test() {
        System.out.println("조회 직전");
        List<Board> boardList = boardRepository.findAll();
        System.out.println("조회 후 : LAZY");
        // Lazy로 댕겨왔을때!
        // 행 : 5개 - > 객체 : 5개
        // 각행 : Board(id = 1, title = 제목, content = 내용1, created_at = 날짜, User(id=1))
        System.out.println(boardList.get(0).getId()); // 1번 (조회 x)
        System.out.println(boardList.get(0).getUser().getId()); // 1번 (조회 x)

        // 예상 : 안된다 왜? -> 레이지잖아! null이 나오겠지 어? 아니네 ?
        // Lazy Loading - 지연로딩
        // 이유 `연관된 객체에 null을 참조하려고 하면 조회가 일어남` (조회 o)
        System.out.println(boardList.get(0).getUser().getUsername());
    }

    @Test
    public void mFindAll_test() {
        boardRepository.mFindAll();
    }

    @Test // ObjectMapper는 boardPG의 겟터를 때리면서 JSON을 만든다.
    public void findAll_paging_test() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.DESC, "id");
        Page<Board> boardPG = boardRepository.findAll(pageable);
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(boardPG); // 자바 객체를 JSON으로 반환
        System.out.println(json);
    }

    @Test
    public void findById_test() {
        Optional<Board> boardOP = boardRepository.findById(5);

        if (boardOP.isPresent()) { // Board 가 존재하면 !!(null 안정성)
            System.out.println("테스트 : board가 있습니다.");
            Board board = boardOP.get();
            board.getUser().getEmail(); // LazyLoading
        }
    }

    @Test
    public void deleteById_test() {
        boardRepository.deleteById(6);
    }

}
