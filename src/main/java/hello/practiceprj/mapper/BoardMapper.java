package hello.practiceprj.mapper;

import hello.practiceprj.domain.Board;
import hello.practiceprj.domain.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {
    @Select("SELECT a.*, COUNT(b.BOARD_ID) AS COMMENTCOUNT FROM BOARD a, MYCOMMENT b WHERE a.ID = b.BOARD_ID(+) GROUP BY a.ID, a.TITLE,a.WRITE_ID,a.WRITE_DATE,a.CONTENT,a.HIT ORDER BY 1 DESC")
    List<Board> allBoard();

    @Select("SELECT * FROM BOARD WHERE ID = #{id}")
    Board findById(int id);

    @Select("SELECT * FROM(SELECT ID FROM BOARD ORDER BY 1 DESC) WHERE ROWNUM=1")
    int boardNextId();

    @Select("SELECT * FROM(SELECT COMMENT_ID FROM MYCOMMENT WHERE BOARD_ID = #{boardId} ORDER BY 1 DESC) WHERE ROWNUM=1")
    Integer commentNextId(int boardId);

    @Insert("INSERT INTO BOARD(ID, TITLE,WRITE_ID, CONTENT,HIT) VALUES ( #{id}, #{title}, #{writeId}, #{content}, 0)")
    void boardWrite(Board board);

    @Select("SELECT * FROM MYCOMMENT WHERE BOARD_ID = #{id} ORDER BY COMMENT_ID")
    List<Comment> boardComment(int id);

    @Insert("INSERT INTO MYCOMMENT (CMT_WRITE_ID, CMT_CONTENT, BOARD_ID, COMMENT_ID) VALUES (#{writerId}, #{content}, #{boardId}, #{commentId})")
    void commentWrite(Comment comment);

    @Select("SELECT a.ID, COUNT(*) AS CMT FROM BOARD a, MYCOMMENT b WHERE a.ID = b.BOARD_ID GROUP BY a.ID")
    HashMap<Integer, Integer> commentCount();
}
