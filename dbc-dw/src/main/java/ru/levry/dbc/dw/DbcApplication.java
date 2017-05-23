package ru.levry.dbc.dw;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import ru.levry.dbc.dw.jdbi.DataOperations;
import ru.levry.dbc.dw.resources.DbcResource;

/**
 * @author levry
 */
public class DbcApplication extends Application<DbcConfiguration> {

    public static void main(String[] args) throws Exception {
        new DbcApplication().run(args);
    }

    @Override
    public void run(DbcConfiguration config, Environment env) throws Exception {
        DataOperations dataOperations = createDataOperations(config, env);
        env.jersey().register(new DbcResource(dataOperations));
    }

    private DataOperations createDataOperations(DbcConfiguration config, Environment env) {
        DBIFactory factory = new DBIFactory();
        DBI jdbi = factory.build(env, config.getDatabase(), "db");
        return new DataOperations(jdbi);
    }

}
