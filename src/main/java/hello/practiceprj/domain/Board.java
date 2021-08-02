package hello.practiceprj.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Getter @Setter @ToString
public class Board {
    private int id;
    private String title;
    private String writeId;
    private String writeDate;
    private String content;
    private int hit;
    private int commentCount;
//    private List<UploadFile> imageFiles;
//    private String storeFilename;
//    private String uploadFilename;

    public Board() {
    }

    public Board(int id, String title, String writeId, int hit) {
        this.id = id;
        this.title = title;
        this.writeId = writeId;
        this.hit = hit;
    }

    public Board(int id, String title, String writeId, String content, int hit) {
        this.id = id;
        this.title = title;
        this.writeId = writeId;
        this.content = content;
        this.hit = hit;
    }

    public Board(int id, String title, String writeId, String writeDate, String content,
                 int hit, int commentCount) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        this.id = id;
        this.title = title;
        this.writeId = writeId;
        this.writeDate = dateFormat.format(writeDate);
        this.content = content;
        this.hit = hit;
        this.commentCount = commentCount;
//        this.storeFilename = storeFilename;
//        this.uploadFilename = uploadFilename;
    }
}
