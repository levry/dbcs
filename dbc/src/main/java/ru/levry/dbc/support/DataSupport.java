package ru.levry.dbc.support;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author levry
 */
public class DataSupport {

    private final DataSource dataSource;

    public DataSupport(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T execute(DataCallback<T> callback) {
        Connection conn = openConnection();
        try {
            return callback.execute(conn);
        } catch (SQLException e) {
            throw new DataException("Error on execute callback: " + e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    private Connection openConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DataException("Error on open connection: " + e.getMessage(), e);
        }
    }

    private void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new DataException("Error on close connection: " + e.getMessage(), e);
        }
    }

}
