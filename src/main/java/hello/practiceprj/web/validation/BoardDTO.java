package hello.practiceprj.web.validation;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BoardDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
