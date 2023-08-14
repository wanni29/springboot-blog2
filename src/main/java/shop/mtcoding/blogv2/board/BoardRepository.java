package shop.mtcoding.blogv2.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 *  JpaRepository 이안에 component 스캔이 들어가있어서 @Repository를 안써도 된다.
 *  save(), findById(), findAll(), count(), deleteById()
 *  JpaRepository => CRUD를 다 주는거라고 생각하면 된다.
*/

// 스프링이 실행될 때, BoardRepositroy의 구현체가 IoC 컨테이너에 생성된다.
// 의존성 주입의 핵심은 타입으로 찾는다. 그래서 싱글톤이다. 
public interface BoardRepository extends JpaRepository<Board, Integer> {

}
