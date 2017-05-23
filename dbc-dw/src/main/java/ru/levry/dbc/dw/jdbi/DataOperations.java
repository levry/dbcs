package ru.levry.dbc.dw.jdbi;

import org.skife.jdbi.v2.DBI;
import ru.levry.dbc.support.DataCallback;

/**
 * @author levry
 */
public class DataOperations {

    private final DBI jdbi;

    public DataOperations(DBI jdbi) {
        this.jdbi = jdbi;
    }

    public <T> T execute(DataCallback<T> callback) {
        return jdbi.withHandle(handle -> callback.execute(handle.getConnection()));
    }
}
