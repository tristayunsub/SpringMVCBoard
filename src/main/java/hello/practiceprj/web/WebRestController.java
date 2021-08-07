package hello.practiceprj.web;

import hello.practiceprj.service.board.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@AllArgsConstructor
public class WebRestController {
    private BoardService boardService;
    private Environment environment;

    @GetMapping("/profile")
    public String getProfile(){
        return Arrays.stream(environment.getActiveProfiles())
                .findFirst()
                .orElse("");
    }
}
