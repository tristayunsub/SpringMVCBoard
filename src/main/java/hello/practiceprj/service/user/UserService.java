package hello.practiceprj.service.user;

import hello.practiceprj.domain.User;
import hello.practiceprj.web.vo.UserInfo;

import java.util.List;

public interface UserService {
    List<User> getUserList();

    UserInfo getUser(String userId);

    void signup(User user);

    void updateUserInfo(User user);

    void updatePassword(User user);
}
