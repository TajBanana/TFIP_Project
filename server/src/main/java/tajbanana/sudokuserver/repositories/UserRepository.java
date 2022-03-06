package tajbanana.sudokuserver.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import tajbanana.sudokuserver.models.Puzzle;
import tajbanana.sudokuserver.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private static final String SQL_SELECT_USER_BY_USERNAME =
            "select * from userlist where username = ?";

    private static final String SQL_COMPARE_PASSWORDS_BY_USERNAME =
            "select count(*) as user_count from userlist where username = ? and password = sha1(?)";

    private static final String SQL_REGISTER_USER =
            "insert into userlist(username, password) values(?,sha1(?))";

    private static final String SQL_GET_USER_PUZZLES =
            "select * from ?";

    private JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean registerUser(String username, String password) {
        return jdbcTemplate.update(SQL_REGISTER_USER, username, password) > 0;
    }

    public void createUserTable(String username) {
        jdbcTemplate.execute(
                "create table " + username + " (seed VARCHAR(81), difficulty VARCHAR(12), PRIMARY KEY(seed))");
    }

    public Optional<List<Puzzle>> getUserPuzzles(String username) {
        List<Puzzle> puzzleList = new ArrayList<>();
        final SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from " + username);
        while (rs.next()) {
            puzzleList.add(Puzzle.populate(rs));
        }
        return Optional.of(puzzleList);
    }

    public Optional<User> findUserByName(String username) {
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_USER_BY_USERNAME, username);
        if (rs.next())
            return Optional.of(User.populate(rs));
        return Optional.empty();
    }

    public boolean validateUser(String username, String password) {
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_COMPARE_PASSWORDS_BY_USERNAME, username, password);
        if (!rs.next())
            return false;

        return rs.getInt("user_count") > 0;
    }
}
