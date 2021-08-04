package hello.practiceprj.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Getter @Setter @ToString
public class User {
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
    private int gender;
    private String birth;
    @Email
    private String email;
    private String phone;
    private Date signupDate;

    public User(String userId, String password, String nickname, int gender, String birth, String email, String phone, Date signupDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.birth = dateFormat.format(birth);
        this.email = email;
        this.phone = phone;
        this.signupDate = signupDate;
    }

    public User() {
    }
}
