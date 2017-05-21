package ru.levry.dbc;

import ru.levry.dbc.meta.TableMetadata;
import ru.levry.dbc.support.SqlBuilder;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author levry
 */
public class UpdateScript extends AbstractTableScript {

    public UpdateScript(String schemaName, String tableName) {
        super(schemaName, tableName);
    }

    @Override
    public String execute(Connection connection) throws SQLException {

        TableMetadata metadata = getTableMetadata(connection);

        return SqlBuilder.update(tableName)
                .set(metadata.getColumns())
                .where(metadata.getPrimaryKeys())
                .toString();
    }

}
