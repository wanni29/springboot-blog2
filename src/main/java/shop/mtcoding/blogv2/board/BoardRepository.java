package shop.mtcoding.blogv2.board;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/*
 *  JpaRepository 이안에 component 스캔이 들어가있어서 @Repository를 안써도 된다.
 *  save(), findById(), findAll(), count(), deleteById()
 *  JpaRepository => CRUD를 다 주는거라고 생각하면 된다.
*/

// 스프링이 실행될 때, BoardRepositroy의 구현체가 IoC 컨테이너에 생성된다.
// 의존성 주입의 핵심은 타입으로 찾는다. 그래서 싱글톤이다. 
public interface BoardRepository extends JpaRepository<Board, Integer> {

    // innerjoin = join
    // 객체 지향 쿼리
    // 실제로는 쿼리는 다음과 같이 날라간다.

    // select id, title, content, user_id, creatd_at from board_tb b inner join
    // user_tb u on b.user_id = u.id;
    // select b from Board b join b.user

    // fetch를 붙여야 * 를 한다
    // fetch를 붙여야 전체를 프로젝션한다.
    @Query(value = "select b from Board b join fetch b.user")
    List<Board> mFindAll();


    @Query("select b from Board b left join fetch b.replies r left join fetch r.user ru where b.id = :id")
    Optional<Board> mFindByIdJoinRepliesInUser(@Param("id") Integer id);

}
