package hello.practiceprj.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
    private String userName;
    private int gender;
    private Date birth;
    private String email;
    private String phone;
    private Date signupDate;

    public void setBirth(String birth) throws ParseException {
        if(birth==""){
            this.birth = null;
        }else{
            SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
            Date date = fm.parse(birth);
            this.birth = date;
        }

    }

    public User() {
    }
}
