package hello.practiceprj.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Getter @Setter @ToString
public class Comment {
    private String cmtWriteId;
    @NotBlank
    private String content;
    private String cmtDate;
    private int boardId;
    private int commentId;

    public Comment() {
    }

    public Comment(String content) {
        this.content = content;
    }

    public Comment(int boardId, int commentId) {
        this.boardId = boardId;
        this.commentId = commentId;
    }

    public Comment(String content, int commentId) {
        this.content = content;
        this.commentId = commentId;
    }

    public Comment(String cmtWriteId, String content, Date cmtDate, int boardId, int commentId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        this.cmtWriteId = cmtWriteId;
        this.content = content;
        this.cmtDate = dateFormat.format(cmtDate);
        this.boardId = boardId;
        this.commentId = commentId;
    }

    public Comment(String cmtWriteId, String content, int boardId, int commentId) {
        this.cmtWriteId = cmtWriteId;
        this.content = content;
        this.boardId = boardId;
        this.commentId = commentId;
    }
}
