package hello.practiceprj.mapper;

import hello.practiceprj.domain.User;
import hello.practiceprj.web.vo.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM MEMBER")
    List<User> allUsers();

    @Select("SELECT * FROM MEMBER WHERE USERID = #{userId}")
    UserInfo findById(String userId);

    @Insert("INSERT INTO MEMBER(USERID, PASSWORD, NICKNAME, GENDER, BIRTH, EMAIL, PHONE) VALUES(#{userId}, #{password}, #{nickname}, #{gender}, #{birth}, #{email}, #{phone})")
    void signup(User user);

    @Update("UPDATE MEMBER SET NICKNAME = #{nickname}, GENDER = #{gender}, BIRTH = #{birth}, EMAIL = #{email}, PHONE = #{phone} WHERE USERID = #{userid}")
    void updateUserInfo(User user);

    @Update("UPDATE MEMBER SET USERPW = #{password} WHERE USERID = #{userId}")
    void updatePassword(User user);
}
