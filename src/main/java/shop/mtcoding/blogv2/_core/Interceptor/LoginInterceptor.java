package shop.mtcoding.blogv2._core.Interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;
import shop.mtcoding.blogv2.user.User;

public class LoginInterceptor implements HandlerInterceptor {
    // 인터페이스 안에 default가 붙어 있으면 구체적인 메소드를 만들수 있다.
    // 강제성이 안붙는다. => 왜 그렇게 구성했을까?
    // 인터페이스에서 강제성이 있는 메소드가 3개가 있는데 필요한건 1개인 상황
    // 3개때문에 다 구현할수없으니까

    /// 인터셉터 생성
    @Override // return => true : 컨트롤러 메소드 진입 return => false : 요청이 종료된다.|컨트롤러에 진입도 안한다.|
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("LoginInterceptor preHandle");
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("sessionUser");

        System.out.println("테스트 : " + request.getRequestURI());
        String startEndPoint = request.getRequestURI().split("/")[1];
        System.out.println("테스트 : " + startEndPoint);

        if (sessionUser == null) {
            if (startEndPoint.equals("api")) {
                response.setHeader("Content-Type", "application/json; charset=utf-8");
                PrintWriter out = response.getWriter();
                ApiUtil<String> apiUtil = new ApiUtil<>(false, "인증이 필요합니다");
                String responseBody = new  ObjectMapper().writeValueAsString(apiUtil);
                System.out.println("테스트 : " + responseBody);
                out.println(responseBody);
            } else {
                response.setHeader("Content-Type", "text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println(Script.href("/loginForm", "인증이 필요합니다."));
            }

            return false;
  
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        System.out.println("LoginInterceptor PostHandle");

    }

}
