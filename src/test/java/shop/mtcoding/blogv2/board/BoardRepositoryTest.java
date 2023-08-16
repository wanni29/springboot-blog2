package shop.mtcoding.blogv2.board;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
        System.out.println(boardList.get(0).getId()); // 1번
        System.out.println(boardList.get(0).getUser().getId()); // 1번

        // 예상 : 안된다 왜? -> 레이지잖아! null이 나오겠지
        // Lazy Loading - 지연로딩
        // 연관된 객체에 null을 참조하려고 하면 조회가 일어남
        System.out.println(boardList.get(0).getUser().getUsername());
    }

    @Test
    public void mFindAll_test() {
        boardRepository.mFindAll();
    }

}
