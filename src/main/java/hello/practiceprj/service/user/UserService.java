package hello.practiceprj.service.user;

import hello.practiceprj.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUserList();

    User getUser(String userId);

    void signup(User user);

    void updateUserInfo(User user);

    void updatePassword(User user);
}
