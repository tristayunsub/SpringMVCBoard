package hello.practiceprj.service.user;

import hello.practiceprj.domain.User;
import hello.practiceprj.mapper.UserMapper;
import hello.practiceprj.web.security.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
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
    public UserInfo getUser(String userId) {
        return userMapper.findById(userId);
    }

    @Override
    public Long signup(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userMapper.signup();
    }

    @Override
    public void updateUserInfo(User user) {
        userMapper.updateUserInfo(user);
    }

    @Override
    public void updatePassword(User user) {
        userMapper.updatePassword(user);
    }

    @Override
    public UserInfo loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = getUser(username);
        if(user == null){
            throw new UsernameNotFoundException("ID : "+username + "는 존재하지 않습니다.");
        }
        return user;
    }
}
