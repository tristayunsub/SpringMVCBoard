package hello.practiceprj.service.user.login;

import hello.practiceprj.domain.User;
import hello.practiceprj.service.user.UserServiceImpl;
import hello.practiceprj.web.security.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserServiceImpl userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserInfo login(String loginId, String password){
        UserInfo user = userService.getUser(loginId);
        if(user == null || !user.getPassword().equals(password)){
            return null;
        }
        return user;
    }


}
