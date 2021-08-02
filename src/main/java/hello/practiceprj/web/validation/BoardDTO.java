package hello.practiceprj.web.validation;

import hello.practiceprj.domain.UploadFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class BoardDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private List<MultipartFile> imageFiles;
}
