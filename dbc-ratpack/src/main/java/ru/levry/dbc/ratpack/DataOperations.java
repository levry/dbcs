package ru.levry.dbc.ratpack;

import ratpack.exec.Blocking;
import ratpack.exec.Promise;
import ru.levry.dbc.support.DataCallback;
import ru.levry.dbc.support.DataSupport;

import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * @author levry
 */
public class DataOperations {

    private final DataSupport dataSupport;

    @Inject
    public DataOperations(DataSource dataSource) {
        this.dataSupport = new DataSupport(dataSource);
    }

    public <T> Promise<T> execute(DataCallback<T> callback) {
        return Blocking.get(() -> dataSupport.execute(callback));
    }
}
