package tajbanana.sudokuserver;

import nu.pattern.OpenCV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        OpenCV.loadShared();
        SpringApplication.run(ServerApplication.class, args);
    }

}
