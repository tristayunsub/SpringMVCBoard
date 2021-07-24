package hello.practiceprj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan(value = {"hello.practiceprj.mapper"})
@SpringBootApplication
public class PracticePrjApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticePrjApplication.class, args);
	}
}
