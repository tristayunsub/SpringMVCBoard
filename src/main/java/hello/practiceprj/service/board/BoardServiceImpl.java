package hello.practiceprj.service.board;

import hello.practiceprj.domain.*;
import hello.practiceprj.mapper.BoardMapper;
import hello.practiceprj.mapper.CommentMapper;
import hello.practiceprj.mapper.FileMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final FileMapper fileMapper;
    private final CommentMapper commentMapper;

    public BoardServiceImpl(BoardMapper boardMapper, FileMapper fileMapper, CommentMapper commentMapper) {
        this.boardMapper = boardMapper;
        this.fileMapper = fileMapper;
        this.commentMapper = commentMapper;
    }

    @Override
    public void hit(int id) {
        boardMapper.hit(id);
    }

    @Override
    public void deleteBoard(int id) {
        boardMapper.deleteBoard(id);
    }

    @Override
    public void updateBoard(Board board) {
        boardMapper.boardUpdate(board);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentMapper.deleteComment(comment);
    }

    @Override
    public void uploadFile(UploadFile uploadFile) {
        fileMapper.uploadFile(uploadFile);
    }

    @Override
    public List<UploadFile> getFiles(int boardId) {
        return fileMapper.getFiles(boardId);
    }

    @Override
    public List<Board> getBoardList() {
        List<Board> boards = boardMapper.allBoard();
        return boards;
    }

    @Override
    public List<Board> getSearchTitle(String searchText) {
        List<Board> boards = boardMapper.searchTitle(searchText);
        return boards;
    }

    @Override
    public List<Board> getSearchContent(String searchText) {
        List<Board> boards = boardMapper.searchContent(searchText);
        return boards;
    }

    @Override
    public List<Board> getSearchWriter(String searchText) {
        List<Board> boards = boardMapper.searchWriter(searchText);
        return boards;
    }

    @Override
    public Board getBoard(int id) {
        return boardMapper.findById(id);
    }

    @Override
    public int boardNextId() {
        return boardMapper.boardNextId();
    }

    @Override
    public int commentNextId(int boardId){
        if (commentMapper.commentNextId(boardId)==null){
            return 1;
        }else{
            return commentMapper.commentNextId(boardId);
        }
    }

    @Override
    public void boardSave(Board board) {
        boardMapper.boardWrite(board);
    }

    @Override
    public List<Comment> getCommentList(int id) {
        return commentMapper.boardComment(id);
    }

    @Override
    public void commentSave(Comment comment) {
        commentMapper.commentWrite(comment);
    }

    @Override
    public List<Reply> getReplies(int boardId) {
        List<Reply> replies = commentMapper.replies(boardId);
        return replies;
    }

    @Override
    public void saveReply(Reply reply) {
        commentMapper.replyWrite(reply);
    }

    @Override
    public void deleteReply(int rplId) {
        commentMapper.replyDelete(rplId);
    }

    @Override
    public List<Recommend> getRecommendList(int boardId) {
        return boardMapper.getRecommendList(boardId);
    }

    @Override
    public void addRecommend(Recommend recommend) {
        boardMapper.addRecommend(recommend);
    }
}
