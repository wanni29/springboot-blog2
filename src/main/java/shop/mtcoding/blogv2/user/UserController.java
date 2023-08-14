package shop.mtcoding.blogv2.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.util.Script;

//SRP => 책임을 확실히하고 과한 책임을 맡으면 안된다.
// controller의 srp는 다음과 같다.
// 핵심로직 처리, 트랜잭션 관리, 예외 처리
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    // C - V
    // M => repository, db관련 모든것을 모델이라고 부른다.
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    // M - V - C
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        userService.회원가입(joinDTO); // 이코드가 회원가입 비지니스로직은 서비스쪽에 위임한거임!
        return "user/loginForm"; // persist초기화 em.clear
        // => 초기화가 되니까 pc는 완전히 비어있는 상태에서 또다른 트랜잭션이 들어오면
        // 깨끗한 백지상태에서 시작
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    // session은 서버측 저장소이고
    // request는
    @PostMapping("/login")
    public @ResponseBody String login(UserRequest.LoginDTO loginDTO) {
        User sessionUser = userService.로그인(loginDTO);
        if (sessionUser == null) { // 틀린것을 조건에 달고 코드를짜면 가독성이 우수해진다. else는 지양하자!
            return Script.back("로그인 실패");
        }
        session.setAttribute("sessionUser", sessionUser);
        return Script.href("/");
    }

    @GetMapping("/user/updateForm")
    public String update(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessionUser.getId());
        request.setAttribute("user", user);
        return "/user/updateForm";
    }

    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO updateDTO) {
        // 1. 회원 수정(서비스)
        // 2. 세션 동기화
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원수정(updateDTO, sessionUser.getId()); // 조회 -> 수정 -> flush
        session.setAttribute("sessionUser", user); // 회원수정을 하고 동기화를 시켜준다.
        return "redirect:/";
    }

}
