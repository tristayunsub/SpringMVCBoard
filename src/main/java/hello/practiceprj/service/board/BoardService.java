package hello.practiceprj.service.board;

import hello.practiceprj.domain.*;

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

    List<Reply> getReplies(int boardId);

    void saveReply(Reply reply);

    void deleteReply(int rplId);

    List<Recommend> getRecommendList(int boardId);

    void addRecommend(Recommend recommend);
}
