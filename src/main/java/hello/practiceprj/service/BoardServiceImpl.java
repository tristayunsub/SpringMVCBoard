package hello.practiceprj.service;

import hello.practiceprj.domain.Board;
import hello.practiceprj.domain.Comment;
import hello.practiceprj.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {
    private BoardMapper boardMapper;

    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    public List<Board> getBoardList() {
        List<Board> boards = boardMapper.allBoard();
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
    public Integer commentNextId(int boardId){
        if (boardMapper.commentNextId(boardId)==null){
            return 0;
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
