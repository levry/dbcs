package ru.levry.dbc.support;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author levry
 */
@FunctionalInterface
public interface DataCallback<T> {
    T execute(Connection connection) throws SQLException;
}
