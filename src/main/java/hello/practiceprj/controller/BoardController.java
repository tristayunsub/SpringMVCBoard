package hello.practiceprj.controller;

import hello.practiceprj.mapper.TimeMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {
    @RequestMapping("/list")
    public String list(){
        return "/boardlist";
    }
}
