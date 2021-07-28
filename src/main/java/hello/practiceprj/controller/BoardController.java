package hello.practiceprj.controller;

import hello.practiceprj.domain.Board;
import hello.practiceprj.domain.Comment;
import hello.practiceprj.web.validation.SaveBoardForm;
import hello.practiceprj.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
    BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping("/list")
    public String list(Model model, @RequestParam (value = "p", required = false, defaultValue = "1") int queryNum) {
        List<Board> boards = boardService.getBoardList();
        int lastNum = (boards.size()/15)+1;
        model.addAttribute("boards", boards);
        model.addAttribute("lastNum", lastNum);
        model.addAttribute("query", queryNum);
        model.addAttribute("startNum",(boards.size()-(queryNum-1)*15)-14);
        model.addAttribute("endNum", boards.size()-(queryNum-1)*15);
        return "boardlist";
    }

    @GetMapping("/list/{boardId}")
    public String board(@PathVariable int boardId,
                        Model model) {
        Board board = boardService.getBoard(boardId);
        List<Board> boards = boardService.getBoardList();
        int queryNum = ((boards.size()-boardId)/15)+1;
        int lastNum = (boards.size()/15)+1;
        int startNum = (boards.size()-(queryNum-1)*15)-14;
        int endNum = boards.size()-(queryNum-1)*15;
        List<Comment> comments = boardService.getCommentList(boardId);
        int commentCnt = comments.size();
        model.addAttribute("board", board);
        model.addAttribute("boards", boards);
        model.addAttribute("comments", comments);
        model.addAttribute("commentCnt", commentCnt);
        model.addAttribute("lastNum", lastNum);
        model.addAttribute("query", queryNum);
        model.addAttribute("startNum", startNum);
        model.addAttribute("endNum", endNum);
        return "board";
    }

    @PostMapping("/list/{boardId}")
    public String commentWrite(@PathVariable int boardId,
                               @RequestParam String content,
                               RedirectAttributes redirectAttributes){
        int commentNextId = boardService.commentNextId(boardId)+1;
        Comment comment = new Comment("test2", content, boardId, commentNextId);
        boardService.commentSave(comment);
        redirectAttributes.addAttribute("boardId", boardId);
        return "redirect:/board/list/{boardId}";
    }



    @GetMapping("/write")
    public String boardWriteForm(Model model){
        Board board = new Board();
        model.addAttribute("board",board);
        return "writeForm";
    }

    @PostMapping("/write")
    public String Boardwrite(@RequestParam String title,
                             @RequestParam String content,
                             @Validated @ModelAttribute("board") SaveBoardForm form,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
//            log.info(bindingResult.toString());
            return "writeForm";
        }
        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
        int nextId = boardService.boardNextId()+1;
        board.setId(nextId);
        board.setWriteId("hsp0404");
        board.setHit(0);
        boardService.boardSave(board);
        model.addAttribute("board", board);
        redirectAttributes.addAttribute("nextId", nextId);
        return "redirect:/board/list/{nextId}";
    }
}
