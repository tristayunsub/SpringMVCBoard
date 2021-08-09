package hello.practiceprj.mapper;

import hello.practiceprj.domain.Board;
import hello.practiceprj.domain.Comment;
import hello.practiceprj.domain.Recommend;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface BoardMapper {
    @Select("SELECT x.ID, x.TITLE, x.WRITE_ID , x.WRITE_DATE , x.CONTENT, x.HIT, x.COMMENTCOUNT+COUNT(y.BOARD_ID) AS COMMENTCOUNT FROM (SELECT a.*, COUNT(b.BOARD_ID) AS COMMENTCOUNT FROM BOARD a, MYCOMMENT b WHERE a.ID = b.BOARD_ID(+) GROUP BY a.ID, a.TITLE,a.WRITE_ID,a.WRITE_DATE,a.CONTENT,a.HIT) x, REPLY y WHERE x.ID = y.BOARD_ID(+) GROUP BY x.ID, x.TITLE,x.WRITE_ID,x.WRITE_DATE,x.CONTENT,x.HIT, x.COMMENTCOUNT ORDER BY 1 DESC")
    List<Board> allBoard();

    //검색
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

    @Insert("INSERT INTO BOARD(ID, TITLE,WRITE_ID, CONTENT,HIT) VALUES ( #{id}, #{title}, #{writeId}, #{content}, 0)")
    void boardWrite(Board board);

    @Update("UPDATE BOARD SET HIT = HIT+1 WHERE ID = #{id}")
    void hit(int id);

    @Update("UPDATE BOARD SET TITLE = #{title}, CONTENT = #{content} WHERE ID = #{id}")
    void boardUpdate(Board board);

    @Delete("DELETE FROM BOARD WHERE ID = #{id}")
    void deleteBoard(int id);

    //추천
    @Select("SELECT BOARD_ID, LIKE_ID FROM RECOMMEND WHERE BOARD_ID = #{boardId}")
    List<Recommend> getRecommendList(int boardId);

    @Insert("INSERT INTO RECOMMEND VALUES(#{boardId}, #{likeId})")
    void addRecommend(Recommend recommend);


}
