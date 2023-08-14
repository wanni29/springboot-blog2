package shop.mtcoding.blogv2.user;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserQueryRepository.class) // 이거 붙여야한다.
@DataJpaTest // JpaRepository 만 메모리(IoC)에 띄운다 이것만 붙이면 안되고
public class UserQueryRepositoryTest {

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private EntityManager em;

    // persist(영속화)
    @Test
    public void save_test() {
        User user = User.builder()
                .username("love")
                .password("1234")
                .email("love@nate.com")
                .build();

        userQueryRepository.save(user);
    }

    // em.clear() => 1차 캐싱 실험
    @Test
    public void findById_test() {
        // pc 는 비어있다.

        System.out.println("1, pc는 비어있다.");

        userQueryRepository.findById(1);
        // pc에 먼저가서 1번 객체가 있는 확인하고 디비로 넘어가는
        // 셀렉트 쿼리 발동 -> pc에 값을 들고온다음 자바의 세계로 넘어간다.
        // pc 는 user 1의 객체가 영속화 되어 있다.
        System.out.println("2. pc의 user 1은 영속화 되어 있다.");
        em.clear();
        userQueryRepository.findById(1);
        System.out.println("???????????????????????????");

    }

    // em.flush()
    @Test
    public void update_test() {
        // update 알고리즘
        // 1. 업데이트 할 객체를 영속화
        // 2. 객체 상태 변경
        // 3. em.flush() or @Transactional 정상 종료

        User user = userQueryRepository.findById(1); // 영속화된 객체 : user
        user.setEmail("ssarmango@nate.com");
        em.flush();

        // 실제 코드에서는 Transactional이 발동하기때문에
        // 플러쉬를 할 필요없다.

    } // roll back

}
