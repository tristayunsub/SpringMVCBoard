package hello.practiceprj.service.user.login;

import hello.practiceprj.service.user.UserServiceImpl;
import hello.practiceprj.web.vo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserServiceImpl userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserInfo loginNon(String loginId, String password){
        UserInfo user = userService.loadUserByUsername(loginId);
        if(bCryptPasswordEncoder.matches(password, user.getPassword())){
            System.out.println("맞음");
            return null;
        }else{
            System.out.println("틀림");
        }
        return user;
    }


}
