package hello.practiceprj.web.board;

import hello.practiceprj.domain.Comment;
import hello.practiceprj.domain.Reply;
import hello.practiceprj.domain.User;
import hello.practiceprj.service.board.BoardServiceImpl;
import hello.practiceprj.web.argumentResolver.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        comment.setContent(" ");
        List<Comment> commentList = boardService.getCommentList(boardId);
        List<Reply> replies = boardService.getReplies(boardId);
        String commentDeleteIcon = "<a onclick=\"deleteComment(this)\" class=\"bi bi-trash\"></a>";
        String replyDeleteIcon = "<a onclick=\"deleteReply(this)\" class=\"bi bi-trash\"></a>";
        model.addAttribute("commentDeleteIcon", commentDeleteIcon);
        model.addAttribute("replyDeleteIcon", replyDeleteIcon);
        model.addAttribute("comments", commentList);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("comment", comment);
        model.addAttribute("replies", replies);
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
        List<Reply> replies = boardService.getReplies(boardId);

        String commentDeleteIcon = "<a onclick=\"deleteComment(this)\" class=\"bi bi-trash\"></a>";
        String replyDeleteIcon = "<a onclick=\"deleteReply(this)\" class=\"bi bi-trash\"></a>";
        model.addAttribute("commentDeleteIcon", commentDeleteIcon);
        model.addAttribute("replyDeleteIcon", replyDeleteIcon);
        model.addAttribute("comments", commentList);
        model.addAttribute("commentDeleteIcon", commentDeleteIcon);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("replies", replies);
        return "board :: #commentDiv";
    }

    @PostMapping("/reply/add/{boardId}/{commentId}")
    public String addReply(@RequestBody Reply reply, @PathVariable int boardId,
                           @PathVariable int commentId, @Login User loginUser, Model model,
                           HttpServletResponse response, @ModelAttribute Comment comment) throws IOException {
        if(loginUser==null){
            response.sendError(500);
            return null;
        }
        reply.setBoardId(boardId);
        reply.setCommentId(commentId);
        reply.setRplWriteId(loginUser.getUserId());
        boardService.saveReply(reply);
        List<Comment> commentList = boardService.getCommentList(boardId);
        List<Reply> replies = boardService.getReplies(boardId);
        String commentDeleteIcon = "<a onclick=\"deleteComment(this)\" class=\"bi bi-trash\"></a>";
        String replyDeleteIcon = "<a onclick=\"deleteReply(this)\" class=\"bi bi-trash\"></a>";
        model.addAttribute("commentDeleteIcon", commentDeleteIcon);
        model.addAttribute("replyDeleteIcon", replyDeleteIcon);
        model.addAttribute("comments", commentList);
        model.addAttribute("comment", comment);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("replies", replies);
        return "board :: #commentDiv";
    }

    @PostMapping("/reply/delete/{boardId}/{rplId}")
    public String deleteReply(@PathVariable int rplId, @PathVariable int boardId,
                            @Login User loginUser, Model model,
                           HttpServletResponse response, @ModelAttribute Comment comment) throws IOException {
        if(loginUser==null){
            response.sendError(500);
            return null;
        }
        boardService.deleteReply(rplId);

        List<Comment> commentList = boardService.getCommentList(boardId);
        List<Reply> replies = boardService.getReplies(boardId);
        String commentDeleteIcon = "<a onclick=\"deleteComment(this)\" class=\"bi bi-trash\"></a>";
        String replyDeleteIcon = "<a onclick=\"deleteReply(this)\" class=\"bi bi-trash\"></a>";
        model.addAttribute("commentDeleteIcon", commentDeleteIcon);
        model.addAttribute("replyDeleteIcon", replyDeleteIcon);
        model.addAttribute("comments", commentList);
        model.addAttribute("comment", comment);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("replies", replies);
        return "board :: #commentDiv";
    }


}
