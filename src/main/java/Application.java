import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  SpringBootApplication = EnableAutoConfiguration + Component + ComponentScan
 */
@SpringBootApplication(scanBasePackages = {"user"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
