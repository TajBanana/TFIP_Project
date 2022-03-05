package tajbanana.sudokuserver.repositories;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.*;

@Repository
public class SeedRepository {

      final String SQL_INSERT_SEED =
            "insert into seed(seed, difficulty) values(?,?)";

    private final JdbcTemplate jdbcTemplate;

    public SeedRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String[] seedFileName = {"simple", "easy", "intermediate", "expert"};

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
