package shop.mtcoding.blogv2.user;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void findByUsername_test() {
        User user = userRepository.findByUsername("hell");
        Assertions.assertThat(user).isNull();
    }

    @Test
    public void mSave_test() {
        userRepository.mSave("love", "1234", "love@nate.com");
    }

    // 회원가입
    @Test
    public void save_test() {

        User user = User.builder()
                .username("love")
                .password("1234")
                .email("wanni@wakanda.com")
                .build();

        User user1 = userRepository.save(user);

        System.out.println("테스트 : " + user1.getId());
        System.out.println("테스트 : " + user1.getUsername());
        System.out.println("테스트 : " + user1.getPassword());
        System.out.println("테스트 : " + user1.getPassword());
        System.out.println("테스트 : " + user1.getCreatedAt());
    }

    // 회원정보조회
    @Test
    public void findById_test() {
        Optional<User> userOP = userRepository.findById(1); // 랜덤박스

        if (userOP.isPresent()) { // Optional 강제성이 있다는거
            User user = userOP.get();
            System.out.println(user.getEmail());
        } else {
            System.out.println("해당 id를 찾을 수 없습니다.");
        }
    }

    // 회원정보조회 2
    @Test
    public void optional_test() {
        User user = User.builder().id(1).username("ssar").build();

        Optional<User> userOP = Optional.of(user);

        if (userOP.isPresent()) {
            User u = userOP.get();
            System.out.println("user가 null이 아닙니다.");
        } else {
            System.out.println("user가 null이에요.");
        }
    }

    // 회원정보수정
    @Test
    public void update_test() {
        User user1 = User.builder()
                .username("power")
                .password("1234")
                .email("wanni@wakanda.com")
                .build();
        userRepository.save(user1);

        User user = userRepository.findById(3).get();
        user.setPassword("5678");
        userRepository.save(user);
        em.flush();
    }
}
