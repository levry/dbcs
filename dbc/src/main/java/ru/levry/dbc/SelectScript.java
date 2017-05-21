package ru.levry.dbc;

import ru.levry.dbc.meta.TableMetadata;

import java.sql.Connection;
import java.sql.SQLException;

import static ru.levry.dbc.support.SqlBuilder.select;

/**
 * @author levry
 */
public class SelectScript extends AbstractTableScript {

    public SelectScript(String schemaName, String tableName) {
        super(schemaName, tableName);
    }

    @Override
    public String execute(Connection connection) throws SQLException {

        TableMetadata metadata = getTableMetadata(connection);

        return select()
                .columns(metadata.getColumns())
                .from(tableName)
                .where(metadata.getPrimaryKeys())
                .toString();
    }

}
