package hello.practiceprj.web.login;

import hello.practiceprj.domain.User;
import hello.practiceprj.service.user.UserService;
import hello.practiceprj.service.user.UserServiceImpl;
import hello.practiceprj.service.user.login.LoginService;
import hello.practiceprj.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final UserServiceImpl userService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "user/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm form,
                        BindingResult bindingResult, HttpServletRequest request,
                        @RequestParam(defaultValue = "/board/list") String redirectURL) {
        if (bindingResult.hasErrors()) {
            return "user/loginForm";
        }
        User loginUser = loginService.login(form.getUserId(), form.getPassword());
        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "user/loginForm";
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);
        System.out.println(redirectURL);
        return "redirect:" + redirectURL;
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
    public String signupForm(@ModelAttribute("user") User user){
        return "user/signupForm";
    }

    @PostMapping("/signup")
    public String signup(@Validated @ModelAttribute User user,
                         BindingResult bindingResult, HttpServletRequest request,
                         @RequestParam(defaultValue = "/") String redirectURL ) throws ParseException {
        if (bindingResult.hasErrors()) {
            return "user/signupForm";
        }
        userService.signup(user);
        User loginUser = loginService.login(user.getUserId(), user.getPassword());
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);
        return "redirect:/board/list";
    }

}
