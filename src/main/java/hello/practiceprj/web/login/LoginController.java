package hello.practiceprj.web.login;

import hello.practiceprj.domain.User;
import hello.practiceprj.service.user.UserServiceImpl;
import hello.practiceprj.service.user.login.LoginService;
import hello.practiceprj.web.vo.UserInfo;
import hello.practiceprj.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final UserServiceImpl userService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "user/loginForm";
    }

    @RequestMapping("/login/auth")
    public String login(@Validated @ModelAttribute LoginForm form,
                        BindingResult bindingResult, HttpServletRequest request,
                        @RequestParam(defaultValue = "/board/list") String redirectURL,@PathVariable String success) {
        System.out.println(success);
        System.out.println("오나?");
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "user/loginForm";
        }

        UserInfo user = userService.loadUserByUsername(form.getUserId());
        UserInfo loginUser = userService.login(user.getUsername(), user.getPassword());
        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "user/loginForm";
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);
        return "redirect:" + redirectURL;
    }

    @RequestMapping("/login/success")
    public String loginSuccess(){
        System.out.println("로그인 완료");
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
