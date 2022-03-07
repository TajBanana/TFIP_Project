package tajbanana.sudokuserver.controllers;

import com.google.j2objc.annotations.AutoreleasePool;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tajbanana.sudokuserver.repositories.UserRepository;
import tajbanana.sudokuserver.services.AuthenticateService;
import tajbanana.sudokuserver.services.EmailService;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
public class RegisterController {

    @Autowired
    AuthenticateService authSvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @PostMapping(path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postAuthenticate(@RequestBody String body) {
        JsonObject obj;

        try {
            JsonReader reader = Json.createReader(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)));
            obj = reader.readObject();

//            Check for existing user
            if (userRepository.findUserByName(obj.getString("username")).isEmpty()) {
                JsonObject registerSuccess = Json.createObjectBuilder()
                        .add("subject","success")
                        .add("token",obj.getString("username") + " successfully registered")
                        .build();
                userRepository.registerUser(obj.getString("username"), obj.getString("password"));
                userRepository.createUserTable(obj.getString("username"));
                System.out.println("creating user table");

                try {
                    emailService.sendEmail();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return ResponseEntity.ok(registerSuccess.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        response for unauthorized
        obj = Json.createObjectBuilder()
                .add("subject","error")
                .add("token","user already exists")
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(obj.toString());
    }
}
