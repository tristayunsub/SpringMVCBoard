package hello.practiceprj.web.board;

import hello.practiceprj.domain.*;
import hello.practiceprj.file.FileStore;
import hello.practiceprj.service.board.BoardServiceImpl;
import hello.practiceprj.web.vo.UserInfo;
import hello.practiceprj.web.vo.BoardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardServiceImpl boardService;
    private final FileStore fileStore;

    @RequestMapping("/list")
    public String list(Model model,
                       @RequestParam (value = "p", required = false, defaultValue = "1") int queryNum,
                       @AuthenticationPrincipal UserInfo loginUser) {
        List<Board> boards = boardService.getBoardList();
        int lastNum = (boards.size()/15)+1;
        model.addAttribute("boards", boards);
        model.addAttribute("lastNum", lastNum);
        model.addAttribute("query", queryNum);
        model.addAttribute("startNum",(boards.size()-(queryNum-1)*15)-14);
        model.addAttribute("endNum", boards.size()-(queryNum-1)*15);
        if(loginUser != null){
            model.addAttribute("loginUser", loginUser);
        }
        return "boardlist";
    }

    @GetMapping("/list/{boardId}")
    public String board(@PathVariable int boardId,
                        @ModelAttribute Comment comment,
                        @ModelAttribute Reply reply,
                        Model model, HttpServletResponse response,
                        HttpServletRequest request,
                        @AuthenticationPrincipal UserInfo loginUser) {
        Board board = boardService.getBoard(boardId);
        List<Board> boards = boardService.getBoardList();
        List<Reply> replies = boardService.getReplies(boardId);
        List<Comment> comments = boardService.getCommentList(boardId);
        List<UploadFile> uploadFiles = boardService.getFiles(boardId);
        List<Recommend> recommendList = boardService.getRecommendList(boardId);
        boardListModel(boardId, comment, reply, model, board, boards, replies, comments);
        model.addAttribute("recommends", recommendList);
//        if(recommendList != null){
//        }
        if(loginUser != null){
            model.addAttribute("loginUser", loginUser);
        }
        if(uploadFiles != null){
            model.addAttribute("files", uploadFiles);
        }
        if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("viewCookie")){
                    String[] boardIdCookies = cookie.getValue().split("-");
                    for (String boardIdCookie : boardIdCookies) {
                        if (boardIdCookie.equals(String.valueOf(boardId))) {
                            return "board";
                        }
                    }
                    String prevCookieValue = cookie.getValue();
                    cookie.setValue(prevCookieValue+"-"+boardId);
                    response.addCookie(cookie);
                    boardService.hit(boardId);
                    return "redirect:/board/list/"+boardId;
                }
            }
        }
        boardService.hit(boardId);
        Cookie viewCookie = new Cookie("viewCookie", String.valueOf(boardId));
        viewCookie.setMaxAge(600);
        response.addCookie(viewCookie);
        return "redirect:/board/list/"+boardId;
    }

    private void boardListModel(int boardId, Comment comment, Reply reply, Model model, Board board, List<Board> boards, List<Reply> replies, List<Comment> comments) {
        String commentDeleteIcon = "<a onclick=\"deleteComment(this)\" class=\"bi bi-trash\"></a>";
        String replyDeleteIcon = "<a onclick=\"deleteReply(this)\" class=\"bi bi-trash\"></a>";
        int queryNum = ((boards.size()- boardId)/15)+1;
        int lastNum = (boards.size()/15)+1;
        int startNum = (boards.size()-(queryNum-1)*15)-14;
        int endNum = boards.size()-(queryNum-1)*15;
        model.addAttribute("board", board);
        model.addAttribute("boards", boards);
        model.addAttribute("comments", comments);
        model.addAttribute("lastNum", lastNum);
        model.addAttribute("query", queryNum);
        model.addAttribute("startNum", startNum);
        model.addAttribute("commentDeleteIcon", commentDeleteIcon);
        model.addAttribute("replyDeleteIcon", replyDeleteIcon);
        model.addAttribute("comment", comment);
        model.addAttribute("reply", reply);
        model.addAttribute("replies", replies);
    }


    @GetMapping("/write")
    public String boardWriteForm(Model model){
        BoardDTO board = new BoardDTO();
        model.addAttribute("board",board);
        return "writeForm";
    }

    @PostMapping("/write")
    public String Boardwrite(@Validated @ModelAttribute("board") BoardDTO form,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal UserInfo loginUser) throws IOException {

        if (bindingResult.hasErrors()) {
            return "writeForm";
        }
        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
        int nextId = boardService.boardNextId()+1;
        board.setId(nextId);
        board.setWriteId(loginUser.getUserId());
        board.setHit(0);
        boardService.boardSave(board);
        uploadFileDB(form, nextId);
        model.addAttribute("board", board);
        redirectAttributes.addAttribute("nextId", nextId);
        return "redirect:/board/list/{nextId}";
    }

    private void uploadFileDB(BoardDTO form, int nextId) throws IOException {
        if(!form.getImageFiles().isEmpty()){
            List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());
            int index = 1;
            for (UploadFile storeImageFile : storeImageFiles) {
                storeImageFile.setBoardId(nextId);
                storeImageFile.setFileNum(index);
                boardService.uploadFile(storeImageFile);
                index++;
            }
        }
    }

    @DeleteMapping("/delete")
    public String test(int boardId) {
        boardService.deleteBoard(boardId);
        return "redirect:/board/list";
    }

    @GetMapping("/edit/{boardId}")
    public String boardEditForm(Model model, @PathVariable int boardId,
                                @AuthenticationPrincipal UserInfo loginUser, HttpServletResponse response) throws IOException {
        Board board = boardService.getBoard(boardId);
        if(!loginUser.getUserId().equals(board.getWriteId())){
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println("<script>alert('잘못된 접근입니다.'); history.go(-1);</script>");
            writer.flush();
            return null;
        }
        model.addAttribute("board", board);
        return "editForm";
    }
    @PostMapping("/edit/{boardId}")
    public String boardEdit(@Validated @ModelAttribute("board") BoardDTO form,
                            BindingResult bindingResult, Model model, @PathVariable int boardId){
        if (bindingResult.hasErrors()) {
            return "writeForm";
        }
        Board board = boardService.getBoard(boardId);
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
        boardService.updateBoard(board);
        model.addAttribute("board", board);
        return "redirect:/board/list/{boardId}";
    }

    @RequestMapping("/search")
    public String search(Model model,
                         @AuthenticationPrincipal UserInfo loginUser, @RequestParam(value = "searchText") String searchText,
                         @RequestParam(value = "searchCategory") String searchCategory,
                         @RequestParam (value = "p", required = false, defaultValue = "1") int queryNum) {
        List<Board> boards = new ArrayList<>();
        switch (searchCategory){
            case "1":
                boards = boardService.getSearchTitle("%"+searchText+"%");
                break;
            case "2":
                boards = boardService.getSearchContent("%"+searchText+"%");
                break;
            case "3":
                boards = boardService.getSearchWriter(searchText);
                break;
        }
        int lastNum = (boards.size()/15)+1;
        String curURI = "/board/search?searchText="+searchText+"&searchCategory="+searchCategory;
        model.addAttribute("boards", boards);
        model.addAttribute("lastNum", lastNum);
        model.addAttribute("query", queryNum);
        model.addAttribute("startNum",(boards.size()-(queryNum-1)*15)-14);
        model.addAttribute("endNum", boards.size()-(queryNum-1)*15);
        model.addAttribute("curURI", curURI);
        if(loginUser != null){
            model.addAttribute("loginUser", loginUser);
        }
        return "searchList";
    }

    @PostMapping("/recommend")
    public String addRecommend(@RequestBody Recommend recommend, @AuthenticationPrincipal UserInfo loginUser, Model model){
        if (loginUser != null) {
            model.addAttribute("loginUser", loginUser);
        }
        List<Recommend> recommendList = boardService.getRecommendList(recommend.getBoardId());
        model.addAttribute("recommends", recommendList);
        if (recommendList != null){
            for (Recommend r : recommendList) {
                if(r.getLikeId().equals(loginUser)){
                    return "board :: #recommendDiv";
                }
            }
        }
        boardService.addRecommend(recommend);
        recommendList = boardService.getRecommendList(recommend.getBoardId());
        model.addAttribute("recommends", recommendList);
        return "board :: #recommendDiv";
    }


    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:"+fileStore.getFullPath(filename));
    }
}
