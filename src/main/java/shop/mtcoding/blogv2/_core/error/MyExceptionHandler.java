package shop.mtcoding.blogv2._core.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.blogv2._core.util.Script;

@RestControllerAdvice // 익셉션이 터지면 여기로 전부다 모인다.
// ControllerAdvice => 응답을 뷰로 해줄꺼냐? // RestControllerAdvice => 응답을 데이터로 해줄꺼냐?
public class MyExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String error(RuntimeException e) {
        return Script.back(e.getMessage());
    }

}
