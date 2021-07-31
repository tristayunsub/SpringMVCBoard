package hello.practiceprj.mapper;

import hello.practiceprj.domain.Board;
import hello.practiceprj.domain.Comment;
import hello.practiceprj.web.validation.BoardDTO;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {
    @Select("SELECT a.*, COUNT(b.BOARD_ID) AS COMMENTCOUNT FROM BOARD a, MYCOMMENT b WHERE a.ID = b.BOARD_ID(+) GROUP BY a.ID, a.TITLE,a.WRITE_ID,a.WRITE_DATE,a.CONTENT,a.HIT ORDER BY 1 DESC")
    List<Board> allBoard();

//    제목으로 찾기
    @Select("SELECT a.*, COUNT(b.BOARD_ID) AS COMMENTCOUNT FROM BOARD a, MYCOMMENT b WHERE a.ID = b.BOARD_ID(+) AND a.TITLE LIKE #{searchText} GROUP BY a.ID, a.TITLE,a.WRITE_ID,a.WRITE_DATE,a.CONTENT,a.HIT ORDER BY 1 DESC")
    List<Board> searchTitle(String searchText);

    @Select("SELECT * FROM (SELECT a.*, COUNT(b.BOARD_ID) AS COMMENTCOUNT FROM BOARD a, MYCOMMENT b WHERE a.ID = b.BOARD_ID(+) GROUP BY a.ID, a.TITLE,a.WRITE_ID,a.WRITE_DATE,a.CONTENT,a.HIT ORDER BY 1 DESC) WHERE TITLE LIKE #{searchText} OR CONTENT LIKE #{searchText}")
    List<Board> searchContent(String searchText);

    @Select("SELECT a.*, COUNT(b.BOARD_ID) AS COMMENTCOUNT FROM BOARD a, MYCOMMENT b WHERE a.ID = b.BOARD_ID(+) AND a.WRITE_ID = #{searchText} GROUP BY a.ID, a.TITLE,a.WRITE_ID,a.WRITE_DATE,a.CONTENT,a.HIT ORDER BY 1 DESC")
    List<Board> searchWriter(String searchText);

    @Select("SELECT * FROM BOARD WHERE ID = #{id}")
    Board findById(int id);

    @Select("SELECT * FROM(SELECT ID FROM BOARD ORDER BY 1 DESC) WHERE ROWNUM=1")
    int boardNextId();

    @Select("SELECT * FROM(SELECT COMMENT_ID+1 AS COMMENT_ID FROM MYCOMMENT WHERE BOARD_ID = #{boardId} ORDER BY 1 DESC) WHERE ROWNUM=1")
    Integer commentNextId(int boardId);

    @Insert("INSERT INTO BOARD(ID, TITLE,WRITE_ID, CONTENT,HIT) VALUES ( #{id}, #{title}, #{writeId}, #{content}, 0)")
    void boardWrite(Board board);

    @Select("SELECT * FROM MYCOMMENT WHERE BOARD_ID = #{id} ORDER BY COMMENT_ID DESC")
    List<Comment> boardComment(int id);

    @Insert("INSERT INTO MYCOMMENT (CMT_WRITE_ID, CONTENT, BOARD_ID, COMMENT_ID) VALUES (#{cmtWriteId}, #{content}, #{boardId}, #{commentId})")
    void commentWrite(Comment comment);

    @Select("SELECT a.ID, COUNT(*) AS CMT FROM BOARD a, MYCOMMENT b WHERE a.ID = b.BOARD_ID GROUP BY a.ID")
    HashMap<Integer, Integer> commentCount();

    @Update("UPDATE BOARD SET HIT = HIT+1 WHERE ID = #{id}")
    void hit(int id);

    @Update("UPDATE BOARD SET TITLE = #{title}, CONTENT = #{content} WHERE ID = #{id}")
    void boardUpdate(Board board);

    @Delete("DELETE FROM BOARD WHERE ID = #{id}")
    void deleteBoard(int id);

    @Delete("DELETE FROM MYCOMMENT WHERE BOARD_ID = #{boardId} AND COMMENT_ID = #{commentId}")
    void deleteComment(Comment comment);


}
