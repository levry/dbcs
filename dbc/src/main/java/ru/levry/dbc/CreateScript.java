package ru.levry.dbc;

import ru.levry.dbc.meta.TableMetadata;
import ru.levry.dbc.support.DDLBuilder;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author levry
 */
public class CreateScript extends AbstractTableScript {

    public CreateScript(String schemaName, String tableName) {
        super(schemaName, tableName);
    }

    @Override
    public String execute(Connection connection) throws SQLException {

        TableMetadata metadata = getTableMetadata(connection);

        return DDLBuilder.create()
                .table(tableName)
                .columns(metadata.getColumns())
                .primaryKey(metadata.getPrimaryKeys())
                .toString();
    }

}
