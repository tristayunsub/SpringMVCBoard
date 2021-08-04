package hello.practiceprj.service.user;

import hello.practiceprj.domain.User;
import hello.practiceprj.web.security.UserInfo;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUserList();

    UserInfo getUser(String userId);

    Long signup(User user);

    void updateUserInfo(User user);

    void updatePassword(User user);
}
