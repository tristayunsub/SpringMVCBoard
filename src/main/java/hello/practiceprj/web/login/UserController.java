package hello.practiceprj.web.login;

import hello.practiceprj.domain.User;
import hello.practiceprj.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;




}
