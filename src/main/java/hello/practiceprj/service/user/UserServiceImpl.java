package hello.practiceprj.service.user;

import hello.practiceprj.domain.User;
import hello.practiceprj.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getUserList() {
        List<User> users = userMapper.allUsers();
        return users;
    }

    @Override
    public User getUser(String userId) {
        return userMapper.findById(userId);
    }

    @Override
    public void signup(User user) {
        userMapper.singup(user);
    }

    @Override
    public void updateUserInfo(User user) {
        userMapper.updateUserInfo(user);
    }

    @Override
    public void updatePassword(User user) {
        userMapper.updatePassword(user);
    }
}
