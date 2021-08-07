package hello.practiceprj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.builder.SpringApplicationBuilder;

@MapperScan(value = {"hello.practiceprj.mapper"})
@SpringBootApplication
public class PracticePrjApplication {

//	public static final String APPLICATION_LOCATIONS = "spring.config.location=classpath:application.properties,C:/Users/user/real-application.yml";
	public static final String APPLICATION_LOCATIONS = "spring.config.location=classpath:application.properties,/app/config/SpringMVCBoard/real-application.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(PracticePrjApplication.class).properties(APPLICATION_LOCATIONS).run(args);
	}
}
