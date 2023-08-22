package shop.mtcoding.blogv2.reply;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.blogv2.user.User;

public class ReplyRequest {

    @Getter
    @Setter
    public static class SaveDTO {
        private String comment;
        private Integer boardId;
    }

 

}
