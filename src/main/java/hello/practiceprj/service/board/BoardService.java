package hello.practiceprj.service.board;

import hello.practiceprj.domain.Board;
import hello.practiceprj.domain.Comment;
import hello.practiceprj.domain.UploadFile;

import java.util.List;

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

    void uploadFile(UploadFile uploadFile);

    List<UploadFile> getFiles(int boardId);
}
