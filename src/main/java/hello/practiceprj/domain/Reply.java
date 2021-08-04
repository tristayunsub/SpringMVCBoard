package hello.practiceprj.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
@Getter @Setter @ToString
public class Reply {
    private String rplWriteId;
    private String content;
    private String rplDate;
    private int boardId;
    private int commentId;
    private int rplId;

    public Reply() {
    }

    public Reply(String content) {
        this.content = content;
    }

    public Reply(int boardId, int commentId) {
        this.boardId = boardId;
        this.commentId = commentId;
    }

    public Reply(String rplWriteId, String content, int boardId, int commentId) {
        this.rplWriteId = rplWriteId;
        this.content = content;
        this.boardId = boardId;
        this.commentId = commentId;
    }

    public Reply(String rplWriteId, String content, String rplDate, int boardId, int commentId, int rplId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        this.rplWriteId = rplWriteId;
        this.content = content;
        this.rplDate = dateFormat.format(rplDate);
        this.boardId = boardId;
        this.commentId = commentId;
        this.rplId = rplId;
    }
}
