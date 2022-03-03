package tajbanana.sudokuserver.repositories;

public interface SQL {
    public static final String SQL_INSERT_SEED =
            "insert into seed(seed, difficulty) values(?,?)";
}
