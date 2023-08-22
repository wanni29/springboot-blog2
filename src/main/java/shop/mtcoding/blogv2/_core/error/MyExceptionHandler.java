package shop.mtcoding.blogv2._core.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;

// ControllerAdvice => 응답을 뷰로 해줄꺼냐? // RestControllerAdvice => 응답을 데이터로 해줄꺼냐?
@RestControllerAdvice // 익셉션이 터지면 여기로 전부다 모인다.
public class MyExceptionHandler {

    @ExceptionHandler(MyException.class)
    public String error(MyException e) {
        return Script.back(e.getMessage());
    }

    
    @ExceptionHandler(MyApiException.class)
    public ApiUtil<String> error(MyApiException e) {
        return new ApiUtil<String>(false, e.getMessage());
    }


}


