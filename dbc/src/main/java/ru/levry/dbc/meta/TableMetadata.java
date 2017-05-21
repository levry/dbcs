package ru.levry.dbc.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author levry
 */
public class TableMetadata {

    private final List<Column> columns = new ArrayList<>();
    private final List<Column> primaryKeys = new ArrayList<>();

    public List<Column> getColumns() {
        return columns;
    }

    void addColumn(Column column) {
        columns.add(column);
    }

    public List<Column> getPrimaryKeys() {
        return primaryKeys;
    }

    void addPrimaryKeyColumn(Column column) {
        primaryKeys.add(column);
    }

}
