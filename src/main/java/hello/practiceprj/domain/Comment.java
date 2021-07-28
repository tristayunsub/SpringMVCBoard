package hello.practiceprj.domain;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter @Setter
public class Comment {
    private String WriterId;
    private String Content;
    private String writeDate;
    private int boardId;
    private int commentId;

    public Comment(String writerId, String content, Date writeDate, int boardId, int commentId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        WriterId = writerId;
        Content = content;
        this.writeDate = dateFormat.format(writeDate);
        this.boardId = boardId;
        this.commentId = commentId;
    }

    public Comment(String writerId, String content, int boardId, int commentId) {
        WriterId = writerId;
        Content = content;
        this.boardId = boardId;
        this.commentId = commentId;
    }
}
