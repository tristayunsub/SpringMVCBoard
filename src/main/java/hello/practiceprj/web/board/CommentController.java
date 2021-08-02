package hello.practiceprj.web.board;

import hello.practiceprj.domain.Comment;
import hello.practiceprj.domain.User;
import hello.practiceprj.service.board.BoardServiceImpl;
import hello.practiceprj.web.argumentResolver.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final BoardServiceImpl boardService;

    @GetMapping("/list/{boardId}")
    public String commentList(@PathVariable int boardId){
        List<Comment> commentList = boardService.getCommentList(boardId);
        return "board";
    }

    @PostMapping("/add/{boardId}")
    public String commentAdd(@Validated @RequestBody Comment comment, BindingResult bindingResult,
                             @PathVariable int boardId, @Login User loginUser, Model model,
                             HttpServletResponse response) throws IOException {
        if(loginUser==null){
            response.sendError(500);
            return null;
        }
        int commentNextId = boardService.commentNextId(boardId);
        comment.setBoardId(boardId);
        comment.setCmtWriteId(loginUser.getUserId());
        comment.setCommentId(commentNextId);
        if(!bindingResult.hasErrors()){
            boardService.commentSave(comment);
        }
        List<Comment> commentList = boardService.getCommentList(boardId);
        String icon = "<a th:href onclick=\"deleteCommentConfirm(this)\" class=\"bi bi-trash\"></a>";
        model.addAttribute("comments", commentList);
        model.addAttribute("icon", icon);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("comment", comment);
        if(bindingResult.hasErrors()){
            return "board :: #commentDiv";
        }
        return "board :: #commentDiv";
    }

    @PostMapping("/delete/{boardId}")
    public String deleteComment(@RequestBody Comment comment, @Login User loginUser, Model model,
                                HttpServletResponse response, @PathVariable int boardId) throws IOException {
        if(loginUser==null){
            response.sendError(500);
            return null;
        }
        boardService.deleteComment(comment);
        List<Comment> commentList = boardService.getCommentList(boardId);

        String icon = "<a th:href onclick=\"deleteCommentConfirm(this)\" class=\"bi bi-trash\"></a>";
        model.addAttribute("comments", commentList);
        model.addAttribute("icon", icon);
        model.addAttribute("loginUser", loginUser);
        return "board :: #commentDiv";
    }


}
