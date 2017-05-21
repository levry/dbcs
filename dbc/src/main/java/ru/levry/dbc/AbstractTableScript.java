package ru.levry.dbc;

import ru.levry.dbc.meta.TableMetadata;
import ru.levry.dbc.meta.TableMetadataProvider;
import ru.levry.dbc.support.DataCallback;

import java.sql.Connection;

/**
 * @author levry
 */
public abstract class AbstractTableScript implements DataCallback<String> {

    protected final String schemaName;
    protected final String tableName;

    public AbstractTableScript(String schemaName, String tableName) {
        this.schemaName = schemaName;
        this.tableName = tableName;
    }

    protected final TableMetadata getTableMetadata(Connection connection) {
        return new TableMetadataProvider(schemaName, tableName).getMetadata(connection);
    }
}
