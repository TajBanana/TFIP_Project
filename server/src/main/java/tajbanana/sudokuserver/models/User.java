package tajbanana.sudokuserver.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class User {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public static User populate(SqlRowSet rowSet) {
        final User user = new User();
        user.setUsername(rowSet.getString("username"));
        user.setPassword(rowSet.getString("password"));
        return user;
    }
}
