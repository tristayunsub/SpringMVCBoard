package hello.practiceprj.service.board;

import hello.practiceprj.domain.Board;
import hello.practiceprj.domain.Comment;
import hello.practiceprj.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
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
        boardMapper.deleteComment(comment);
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
        if (boardMapper.commentNextId(boardId)==null){
            return 1;
        }else{
            return boardMapper.commentNextId(boardId);
        }
    }

    @Override
    public void boardSave(Board board) {
        boardMapper.boardWrite(board);
    }

    @Override
    public List<Comment> getCommentList(int id) {
        return boardMapper.boardComment(id);
    }

    @Override
    public void commentSave(Comment comment) {
        boardMapper.commentWrite(comment);
    }

}
