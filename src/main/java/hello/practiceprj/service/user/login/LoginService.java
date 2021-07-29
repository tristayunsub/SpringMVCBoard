package hello.practiceprj.service.user.login;

import hello.practiceprj.domain.User;
import hello.practiceprj.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserServiceImpl userService;

    public User login(String loginId, String password){
        User user = userService.getUser(loginId);
        if(user == null || user.getPassword() == password){
            return null;
        }
        return user;
    }
}
