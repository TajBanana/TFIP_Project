package tajbanana.sudokuserver.controllers;

import jakarta.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tajbanana.sudokuserver.models.Puzzle;
import tajbanana.sudokuserver.repositories.SeedRepository;
import tajbanana.sudokuserver.services.SudokuSolverService;
import tajbanana.sudokuserver.services.opencv.SudokuImageSolverService;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


@Controller
@RequestMapping(path = "/sudoku")
public class PuzzleController {

    private final int[][] sudokuArray = new int[9][9];

    @Autowired
    SeedRepository seedRepository;

    @Autowired
    SudokuSolverService sudokuSolver;

    @Autowired
    SudokuImageSolverService imageService;

    @PostMapping(path = "/solveimage")
    public ResponseEntity<String> solveImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);

        byte[] imageBytes = file.getBytes();
        int[][] sudokuMatrix =  imageService.getSudokuMatrix(imageBytes);
        int[][] sudokuSolution = sudokuSolver.getSudokuSolution(sudokuMatrix);

        JsonObject solutionObj = solutionToJson(sudokuSolution);

        return ResponseEntity.ok(solutionObj.toString());
    }

    @PostMapping(path = "/solve",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> solveSudoku(@RequestBody String body) {
        JsonObject obj;
        try {
            JsonReader reader = Json.createReader(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)));
            obj = reader.readObject();
            System.out.println(obj);
            System.out.println("");
            System.out.println(obj.get("puzzle").asJsonArray());
            JsonArray puzzleArray = obj.get("puzzle").asJsonArray();

            int i = 0;
            for (JsonValue puzzleRow : puzzleArray) {

                int j = 0;
                JsonArray puzzleRowIterable = puzzleRow.asJsonArray();
                for (JsonValue value : puzzleRowIterable) {
                    int num = Integer.parseInt(String.valueOf(value));
                    sudokuArray[i][j] = num;
                    j++;
                }
                i++;
            }

            int[][] sudokuSolution = sudokuSolver.getSudokuSolution(sudokuArray);
            System.out.println(Arrays.deepToString(sudokuSolution));

            JsonObject solutionObj = solutionToJson(sudokuSolution);

            return ResponseEntity.ok(solutionObj.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(path = "{difficulty}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRandomPuzzle(@PathVariable String difficulty) {

        Puzzle puzzle = seedRepository.getPuzzlesByDifficulty(difficulty);

        int i = 0;
        int k = 0;
        JsonArrayBuilder puzzleArrayBuilder = Json.createArrayBuilder();

        while (k < 9) {
            int j = 0;
            JsonArrayBuilder puzzleRowBuilder = Json.createArrayBuilder();
            while (j < 9) {
                int seedNumber = Integer.parseInt(
                        String.valueOf(
                                puzzle
                                        .getPuzzleSeed()
                                        .charAt(i)));

                puzzleRowBuilder.add(seedNumber);
                j++;
                i++;
            }
            puzzleArrayBuilder.add(puzzleRowBuilder);
            k++;
        }

        JsonObject puzzleObj = Json.createObjectBuilder()
                .add("puzzle",puzzleArrayBuilder)
                .add("difficulty", puzzle.getDifficulty())
                .build();

        return ResponseEntity.ok(puzzleObj.toString());

    }

    private JsonObject solutionToJson(int[][] sudokuSolution) {
        JsonObjectBuilder solutionObjBuilder = Json.createObjectBuilder();
        JsonArrayBuilder solutionArrayBuilder = Json.createArrayBuilder();

        for (int[] row : sudokuSolution) {
            JsonArrayBuilder rowArrayBuilder = Json.createArrayBuilder();
            for (int value: row) {
                rowArrayBuilder.add(value);
            }
            solutionArrayBuilder.add(rowArrayBuilder);
        }

        return solutionObjBuilder
                .add("solution",solutionArrayBuilder)
                .build();
    }
}
