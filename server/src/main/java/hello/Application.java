package hello;

import org.parse4j.Parse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Parse.initialize("JuieptN1QwyYM4iR1Q3qloBQh7OZMSznjCUbXMSu", "tlJQNmIGEw9OsOT7R4TrDnkL1iMfTc4RqKOUbBFM");
        SpringApplication.run(Application.class, args);
    }
}
