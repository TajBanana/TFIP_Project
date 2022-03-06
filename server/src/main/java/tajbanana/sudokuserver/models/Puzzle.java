package tajbanana.sudokuserver.models;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Puzzle {
    private String puzzleSeed;
    private String difficulty;

    public String getPuzzleSeed() {
        return puzzleSeed;
    }

    public void setPuzzleSeed(String puzzleSeed) {
        this.puzzleSeed = puzzleSeed;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public static Puzzle populate(SqlRowSet rowSet) {
        final Puzzle user = new Puzzle();
        user.setPuzzleSeed(rowSet.getString("seed"));
        user.setDifficulty(rowSet.getString("difficulty"));
        return user;
    }
}
