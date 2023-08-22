package shop.mtcoding.blogv2.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;

//SRP => 책임을 확실히하고 과한 책임을 맡으면 안된다.
// controller의 srp는 다음과 같다.
// 핵심로직 처리, 트랜잭션 관리, 예외 처리
@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

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
    public @ResponseBody String join(UserRequest.JoinDTO joinDTO) {
        // 1. 인증체크
        

        // 2. 핵심 로직
        userService.회원가입(joinDTO);

        // 3. 응답
        return Script.href("/loginForm", "회원가입 완료");
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

    // 브라우저 GET /logout 요청을 함 (request 1)
    // 서버는 / 주소를 응답의 헤더에 담음(Location), 상태코드 302를 응답
    // 브라우저는 GET / 로 재요청을 함 (request 2)
    // index 페이지 응답받고 렌더링함
    @GetMapping("/logout")
    public String logout() { // 로그아웃 할시 script해서 얼랏트 창 띄울수 있음, 선택할수있음
        session.invalidate();
        return "redirect:/";
    }


    @PostMapping("/api/user/join")
    public @ResponseBody ApiUtil check(@RequestBody String username) {
        userService.중복체크(username);
        return new ApiUtil<String>(true, "이 아이디는 생성가능한 아이디입니다.");
    }

}
