package hello.practiceprj.mapper;

import hello.practiceprj.domain.Comment;
import hello.practiceprj.domain.Reply;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("SELECT * FROM(SELECT COMMENT_ID+1 AS COMMENT_ID FROM MYCOMMENT WHERE BOARD_ID = #{boardId} ORDER BY 1 DESC) WHERE ROWNUM=1")
    Integer commentNextId(int boardId);

    @Select("SELECT * FROM MYCOMMENT WHERE BOARD_ID = #{id} ORDER BY COMMENT_ID DESC")
    List<Comment> boardComment(int id);

    @Insert("INSERT INTO MYCOMMENT (CMT_WRITE_ID, CONTENT, BOARD_ID, COMMENT_ID) VALUES (#{cmtWriteId}, #{content}, #{boardId}, #{commentId})")
    void commentWrite(Comment comment);

    @Select("SELECT a.ID, COUNT(*) AS CMT FROM BOARD a, MYCOMMENT b WHERE a.ID = b.BOARD_ID GROUP BY a.ID")
    HashMap<Integer, Integer> commentCount();

    @Delete("DELETE FROM MYCOMMENT WHERE BOARD_ID = #{boardId} AND COMMENT_ID = #{commentId}")
    void deleteComment(Comment comment);

    //대댓글

    @Select("SELECT * FROM REPLY WHERE BOARD_ID = #{boardId} ORDER BY RPL_DATE DESC")
    List<Reply> replies(int boardId);

    @Insert("INSERT INTO REPLY (RPL_WRITE_ID, CONTENT, BOARD_ID, COMMENT_ID) VALUES (#{rplWriteId}, #{content}, #{boardId}, #{commentId})")
    void replyWrite(Reply reply);

    @Delete("DELETE FROM REPLY WHERE RPL_ID = #{rplId}")
    void replyDelete(int rplId);
}
