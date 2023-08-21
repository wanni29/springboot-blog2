package shop.mtcoding.blogv2.reply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.util.Script;

@Controller
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @PostMapping("/reply/{id}/delete")
    public String ReplyDelete(@PathVariable Integer id) {
        replyService.댓글삭제(id);
        return "redirect:/board/{id}";
    }

    @PostMapping("/reply/save")
    public String save(ReplyRequest.SaveDTO saveDTO) {
        replyService.댓글등록(saveDTO, 1);
        return "redirect:/board/" + saveDTO.getBoardId();
    }

}
