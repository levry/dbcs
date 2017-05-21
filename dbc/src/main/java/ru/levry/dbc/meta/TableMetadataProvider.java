package ru.levry.dbc.meta;

import ru.levry.dbc.support.DataException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.sql.DatabaseMetaData.columnNullable;

/**
 * @author levry
 */
public class TableMetadataProvider {

    private final String schemaName;
    private final String tableName;

    public TableMetadataProvider(String schemaName, String tableName) {
        this.schemaName = schemaName;
        this.tableName = tableName;
    }

    public TableMetadata getMetadata(Connection connection) {
        try {
            TableMetadata table = new TableMetadata();

            DatabaseMetaData metaData = connection.getMetaData();
            Set<String> primaryKeys = extractPrimaryKeys(metaData);
            ResultSet resultSet = metaData.getColumns(null, schemaName, tableName, null);
            while (resultSet.next()) {
                Column column = extractColumn(resultSet, primaryKeys);
                table.addColumn(column);
                if(column.isPrimaryKey()) {
                    table.addPrimaryKeyColumn(column);
                }
            }
            return table;
        } catch (SQLException e) {
            throw new DataException("Error on extract columns: " + e.getMessage(), e);
        }
    }

    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String COLUMN_NULLABLE = "NULLABLE";
    private static final String COLUMN_TYPE_NAME = "TYPE_NAME";
    private static final String COLUMN_SIZE = "COLUMN_SIZE";
    private static final String COLUMN_DECIMAL_DIGITS = "DECIMAL_DIGITS";
    private static final String COLUMN_DATA_TYPE = "DATA_TYPE";

    private Set<String> extractPrimaryKeys(DatabaseMetaData metaData) throws SQLException {
        Set<String> keys = new HashSet<>();
        try(ResultSet resultSet = metaData.getPrimaryKeys(null, schemaName, tableName)) {
            while (resultSet.next()) {
                keys.add(resultSet.getString(COLUMN_NAME));
            }
        }
        return keys;
    }

    private Column extractColumn(ResultSet resultSet, Set<String> primaryKeys) throws SQLException {
        String name = resultSet.getString(COLUMN_NAME);
        boolean primaryKey = primaryKeys.contains(name);
        String typeName = resultSet.getString(COLUMN_TYPE_NAME);
        Integer size = (Integer) resultSet.getObject(COLUMN_SIZE);
        Integer decimalDigits = (Integer) resultSet.getObject(COLUMN_DECIMAL_DIGITS);
        int dataType = resultSet.getInt(COLUMN_DATA_TYPE);
        boolean nullable = Objects.equals(columnNullable, resultSet.getInt(COLUMN_NULLABLE));

        Column column = new Column(name);
        column.setPrimaryKey(primaryKey);
        column.setTypeName(typeName);
        column.setDataType(dataType);
        column.setSize(size);
        column.setDecimalDigits(decimalDigits);
        column.setNullable(nullable);
        return column;
    }

}
