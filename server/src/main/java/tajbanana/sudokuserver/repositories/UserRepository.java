package tajbanana.sudokuserver.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import tajbanana.sudokuserver.models.User;

import java.util.Optional;

@Repository
public class UserRepository {
    private static final String SQL_SELECT_USER_BY_USERNAME =
            "select * from userlist where username = ?";

    private static final String SQL_COMPARE_PASSWORDS_BY_USERNAME =
            "select count(*) as user_count from userlist where username = ? and password = sha1(?)";

    private JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
