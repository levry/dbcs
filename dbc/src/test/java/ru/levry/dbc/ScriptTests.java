package ru.levry.dbc;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import ru.levry.dbc.support.DataCallback;
import ru.levry.dbc.support.DataSupport;

import javax.sql.DataSource;
import java.net.URL;

/**
 * @author levry
 */
class ScriptTests {

    private DataSupport dataSupport;

    @BeforeEach
    void setUp() {
        DataSource dataSource = createDataSource();
        dataSupport = new DataSupport(dataSource);
    }

    private JdbcDataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        String file = getResource("/data/testdb");
        dataSource.setURL("jdbc:h2:" + file + ";DB_CLOSE_ON_EXIT=FALSE;IFEXISTS=TRUE;IGNORECASE=TRUE");
        dataSource.setUser("admin");
        return dataSource;
    }

    private String getResource(String name) {
        URL resource = getClass().getResource(name + ".mv.db");
        return resource.getFile().replace(".mv.db", "");
    }

    protected final <T> T executeScript(DataCallback<T> script) {
        return dataSupport.execute(script);
    }
}
