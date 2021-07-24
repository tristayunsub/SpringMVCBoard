package hello.practiceprj.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Board {
    private int id;
    private String title;
    private String writeId;
    private Date writeDate;
    private String content;
    private int hit;

    public Board() {
    }

    public Board(int id, String title, String writeId, int hit) {
        this.id = id;
        this.title = title;
        this.writeId = writeId;
        this.hit = hit;
    }

    public Board(int id, String title, String writeId, Date writeDate, String content, int hit) {
        this.id = id;
        this.title = title;
        this.writeId = writeId;
        this.writeDate = writeDate;
        this.content = content;
        this.hit = hit;
    }
}
