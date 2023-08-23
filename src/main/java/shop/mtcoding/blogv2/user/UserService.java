package shop.mtcoding.blogv2.user;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.vo.MyPath;
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

        // 게시물에 사진을 등록하는 기능을 구현하기 위해선 
        UUID uuid = UUID.randomUUID(); // 랜덤한 해시값을 만들어줌
        String fileName = uuid+"_"+joinDTO.getPic().getOriginalFilename();
        System.out.println("fileName : " + fileName);


        // 프로젝트 실행 파일변경 -> blogv2-1.0.jar
        // 해당 실행파일 경로에 images 폴더가 필요함
        Path filePath = Paths.get(MyPath.IMG_PATH + fileName);
        try {
            Files.write(filePath, joinDTO.getPic().getBytes());
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }

        User user = User.builder()
                .username(joinDTO.getUsername())
                .password(joinDTO.getPassword())
                .email(joinDTO.getEmail())
                .picUrl(fileName) // 디비에는 파일의 이름만 저장하고 경로는 저장하지말자. 아까전에 보았듯이 오에스의 환경마다 달라지니 에러가 뜬다.
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
