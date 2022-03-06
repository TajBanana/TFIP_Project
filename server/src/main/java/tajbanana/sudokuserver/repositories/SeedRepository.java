package tajbanana.sudokuserver.repositories;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import tajbanana.sudokuserver.models.Puzzle;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class SeedRepository {

      final String SQL_INSERT_SEED =
            "insert into seed(seed, difficulty) values(?,?)";
//      final String SQL_GET_PUZZLE_BY_DIFFICULTY = "select * from seed where difficulty = '?' ";

    private final JdbcTemplate jdbcTemplate;

    public SeedRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String[] seedFileName = {"simple", "easy", "intermediate", "expert"};

    public Puzzle getPuzzlesByDifficulty(String difficulty) {
        List<Puzzle> puzzleList = new ArrayList<>();
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(
                        "select * from seed where difficulty = '" + difficulty +"'");
        while (rs.next()) {
            puzzleList.add(Puzzle.populate(rs));
        }
        Random rand = new Random();
        return puzzleList.get(rand.nextInt(puzzleList.size()));
    }

    public void createSeedDB() {
        try {
            for (String difficulty : seedFileName) {

                InputStream inputStream = new ClassPathResource(
                        "seed/" + difficulty + ".txt").getInputStream();

                System.out.println("seed/" + difficulty + ".txt");
                System.out.println(inputStream);

                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream));

                String seed = null;

                while ((seed = bufferedReader.readLine()) != null) {
/*                    System.out.println(seed);
                    System.out.println(difficulty);*/
                    jdbcTemplate.update(SQL_INSERT_SEED,seed,difficulty);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
