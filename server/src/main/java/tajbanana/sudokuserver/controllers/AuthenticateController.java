package tajbanana.sudokuserver.controllers;

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

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
public class AuthenticateController {

    @Autowired
    AuthenticateService authSvc;

    @Autowired
    UserRepository userRepository;

    @PostMapping(path = "/authenticate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postAuthenticate(@RequestBody String body) {
        JsonObject obj;

        try {

            JsonReader reader = Json.createReader(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)));
            obj = reader.readObject();

//            Check for existing user
            if (userRepository.findUserByName(obj.getString("username")).isEmpty()) {

                JsonObject noUser = Json.createObjectBuilder()
                        .add("error","no such user, please sign up :)")
                        .build();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(noUser.toString());
            }

//            Authenticate login details
            Optional<JsonObject> opt = authSvc.authenticate(obj.getString("username"), obj.getString("password"));
            if (opt.isPresent())
                return ResponseEntity.ok(opt.get().toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        response for unauthorized
        obj = Json.createObjectBuilder()
                .add("error","Cannot authenticate")
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(obj.toString());
    }
}
