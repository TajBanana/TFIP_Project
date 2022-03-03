package tajbanana.sudokuserver.controllers;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tajbanana.sudokuserver.repositories.SeedRepository;
import tajbanana.sudokuserver.services.QuoteService;

@Controller
@RequestMapping(path = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

    @Autowired
    QuoteService quoteService;

    @Autowired
    SeedRepository seedRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping(path = "/quote")
    public ResponseEntity<String> getHome() {

//        add quote into json and send over
        String quote = quoteService.getQuote();
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("quote", quote)
                .build();
        return ResponseEntity.ok(jsonObject.toString());
    }

    @GetMapping(path = "/loadDb")
    public ResponseEntity<String> loadDb() {
        seedRepository.createSeedDB();
        return null;
    }
}
