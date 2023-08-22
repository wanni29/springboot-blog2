package shop.mtcoding.blogv2._core.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


// 제이슨 응답해줄라고 만듦
// 공통형태의 DTO
@Getter
@Setter
public class ApiUtil<T> {
    private boolean success; // insert 가 잘되면 true
    private T data; // 댓글쓰기 성공


    public ApiUtil(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

}
