package hello.practiceprj.web.login;

import hello.practiceprj.domain.User;
import hello.practiceprj.service.user.UserServiceImpl;
import hello.practiceprj.web.vo.UserInfo;
import hello.practiceprj.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserServiceImpl userService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm form,
                            @RequestParam(value = "error", required = false) String error, Model model) {
        if(error != null){
            model.addAttribute("error", 1);
        }
        return "user/loginForm";
    }


    @RequestMapping("/login/success")
    public String loginSuccess(){
        return null;
    }

    @GetMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/board/list";
    }
    @GetMapping("/signup")
    public String signupForm(@ModelAttribute("user") UserInfo user){
        return "user/signupForm";
    }

    @PostMapping("/signup")
    public String signup(@Validated @ModelAttribute User user,
                         BindingResult bindingResult, HttpServletRequest request) {
        List<User> userList = userService.getUserList();
        for (User users : userList) {
            if (user.getUserId().equals(users.getUserId())) {
                bindingResult.rejectValue("userId","existed");
                return "user/signupForm";
            }
        }
        if (bindingResult.hasErrors()) {
            return "user/signupForm";
        }
        userService.signup(user);
//        User loginUser = loginService.login(user.getUserId(), user.getPassword());
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, user);
        return "redirect:/board/list";
    }

}
