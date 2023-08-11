package shop.mtcoding.blogv2.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 *  JpaRepository 이안에 component 스캔이 들어가있어서 @Repository를 안써도 된다.
 *  save(), findById(), findAll(), count(), deleteById()
 *  JpaRepository => CRUD를 다 주는거라고 생각하면 된다.
*/
public interface BoardRepository extends JpaRepository<Board, Integer> {

}
