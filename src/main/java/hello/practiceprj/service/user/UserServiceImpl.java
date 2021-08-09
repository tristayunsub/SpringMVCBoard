package hello.practiceprj.service.user;

import hello.practiceprj.domain.User;
import hello.practiceprj.mapper.UserMapper;
import hello.practiceprj.web.vo.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> getUserList() {
        List<User> users = userMapper.allUsers();
        return users;
    }

    @Override
    public UserInfo getUser(String userId) {
        UserInfo user = userMapper.findById(userId);
        return user;
    }

    @Override
    public void signup(User user) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userMapper.signup(user);
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

//    public UserInfo login(String loginId, String password){
//        UserInfo user = loadUserByUsername(loginId);
//        if(bCryptPasswordEncoder.matches(password, user.getPassword())){
//            System.out.println("맞음");
//            return null;
//        }else{
//            System.out.println("틀림");
//        }
//        return user;
//    }
}
