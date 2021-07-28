package hello.practiceprj.web.validation;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SaveBoardForm {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
