package tajbanana.sudokuserver.services;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tajbanana.sudokuserver.repositories.UserRepository;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class AuthenticateService {

    @Autowired
    private UserRepository userRepo;
    private SecretKey signKey;

    @PostConstruct
    public void init() {
        String passphrase = "v9y$B&E)H@McQfTjWnZr4u7x!z%C*F-J";
        System.out.println("pass phrase >>> " + passphrase);

        try {
            signKey = Keys.hmacShaKeyFor(passphrase.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Creating HMAC Sign key", e);
        }
    }

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(signKey).build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Optional<JsonObject> authenticate(String username, String password) {

        if (!userRepo.validateUser(username, password))
            return Optional.empty();

        String token = Jwts.builder()
                .setSubject(username)
                .signWith(signKey)
                .compact();

        return Optional.of(Json.createObjectBuilder()
                .add("subject", username)
                .add("token", token)
                .build());
    }


}
