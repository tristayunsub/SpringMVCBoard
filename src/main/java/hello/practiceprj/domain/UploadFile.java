package hello.practiceprj.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UploadFile {
    private int fileNum;
    private int boardId;
    private String uploadFileName;
    private String storeFileName;
    private long fileSize;

    public UploadFile() {
    }

    public UploadFile(String uploadFileName, String storeFileName, long fileSize) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.fileSize = fileSize;
    }

    public UploadFile(int fileNum, int boardId, String uploadFileName, String storeFileName, long fileSize) {
        this.fileNum = fileNum;
        this.boardId = boardId;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.fileSize = fileSize;
    }
}
