package shop.mtcoding.blogv2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2.user.UserRequest.JoinDTO;
import shop.mtcoding.blogv2.user.UserRequest.LoginDTO;
import shop.mtcoding.blogv2.user.UserRequest.UpdateDTO;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void 회원가입(JoinDTO joinDTO) {
        // if (joinDTO.getUsername() ==
        // userRepository.findByUsername(joinDTO.getUsername()).getUsername()) {
        // throw new MyApiException("중복된 유저네임입니다.");
        // } else { }
        User user = User.builder()
                .username(joinDTO.getUsername())
                .password(joinDTO.getPassword())
                .email(joinDTO.getEmail())
                .build();
        userRepository.save(user); // 실제로는 persist() 메소드가 발동

    }

    // 쓰기를 안하니까 트랜잭션을 달필요가 없어!
    public User 로그인(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());

        // 1. 유저네임 검증
        if (user == null) {
            throw new MyException("유저네임이 없습니다.");
        }

        // 2. 패스워드 검증
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new MyException("패스워드가 잘못되었습니다.");
        }

        // 3, 로그인 성공
        return user;

    }

    public User 회원정보보기(Integer id) {
        return userRepository.findById(id).get();
    }

    @Transactional // transactional 을 안붙이면 flush 가 안된다.
    public User 회원수정(UpdateDTO updateDTO, Integer id) {
        // 1. 조회 (영속화)
        User user = userRepository.findById(id).get();

        // 2. 변경
        user.setPassword(updateDTO.getPassword());

        return user;
        // 3. flush
    }

    public void 중복체크(String username) {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            throw new MyApiException("아이디가 중복되었습니다.");
        } 
        
    }
}
