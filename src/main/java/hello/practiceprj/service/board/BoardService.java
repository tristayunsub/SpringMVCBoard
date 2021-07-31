package hello.practiceprj.service.board;

import hello.practiceprj.domain.Board;
import hello.practiceprj.domain.Comment;
import hello.practiceprj.web.validation.BoardDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BoardService {
    List<Board> getBoardList();

    List<Board> getSearchTitle(String searchText);

    List<Board> getSearchContent(String searchText);

    List<Board> getSearchWriter(String searchText);

    Board getBoard(int id);

    int boardNextId();

    int commentNextId(int boardId);

    void boardSave(Board board);

    List<Comment> getCommentList(int id);

    void commentSave(Comment comment);

    void hit(int id);

    void deleteBoard(int id);

    void updateBoard(Board board);

    void deleteComment(Comment comment);
}
