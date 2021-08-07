package hello.practiceprj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;

@MapperScan(value = {"hello.practiceprj.mapper"})
@SpringBootApplication
public class PracticePrjApplication {

	public static final String APPLICATION_LOCATIONS_WIN = "spring.config.location=classpath:application.properties,C:/Users/user/real-application.yml";
	public static final String APPLICATION_LOCATIONS_EC2 = "spring.config.location=classpath:application.properties,/app/config/SpringMVCBoard/real-application.yml";

	public static void main(String[] args) {
		try{
			new SpringApplicationBuilder(PracticePrjApplication.class).properties(APPLICATION_LOCATIONS_EC2).run(args);
		}catch(ConfigDataResourceNotFoundException e){
			new SpringApplicationBuilder(PracticePrjApplication.class).properties(APPLICATION_LOCATIONS_WIN).run(args);
		}
	}
}
