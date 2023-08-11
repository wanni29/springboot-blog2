package shop.mtcoding.blogv2.board;

import java.sql.Timestamp;

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

}
