package tajbanana.sudokuserver.controllers;

import jakarta.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tajbanana.sudokuserver.models.Puzzle;
import tajbanana.sudokuserver.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/secure", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserProfileController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/{username}")
    public ResponseEntity<String> postUserProfile(@PathVariable String username) {
        List<Puzzle> puzzleList = new ArrayList<>();

        System.out.println(username);
        System.out.println(userRepository.getUserPuzzles(username).isPresent());

        if (userRepository.getUserPuzzles(username).isPresent()) {
            System.out.println("entered if");
            puzzleList= userRepository.getUserPuzzles(username).get();
        }

        System.out.println(puzzleList);

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (Puzzle puzzle : puzzleList) {
            JsonObject jsonObject = Json.createObjectBuilder()
                    .add("puzzle",puzzle.getPuzzleSeed())
                    .add("difficulty", puzzle.getDifficulty())
                    .build();
            jsonArrayBuilder.add(jsonObject);
        }

        return ResponseEntity.ok(jsonArrayBuilder.build().toString());

    }
}
