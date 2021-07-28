package hello.practiceprj.service;

import hello.practiceprj.domain.Board;
import hello.practiceprj.domain.Comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BoardService {
    List<Board> getBoardList();

    Board getBoard(int id);

    int boardNextId();

    Integer commentNextId(int boardId);

    void boardSave(Board board);

    List<Comment> getCommentList(int id);

    void commentSave(Comment comment);
}
