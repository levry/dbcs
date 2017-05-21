package ru.levry.dbc.support;

import ru.levry.dbc.meta.Column;

import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * @author levry
 */
public class SqlBuilder {

    private SqlBuilder() {
    }

    public static Select select() {
        return new SqlBuilderSteps(new StringBuilder("SELECT "));
    }

    public static Update update(String tableName) {
        return new SqlBuilderSteps(new StringBuilder("UPDATE ").append(tableName));
    }

    public interface Select {
        From columns(List<Column> columns);
    }

    public interface From {
        Where from(String tableName);
    }

    public interface Update {
        Where set(List<Column> columns);
    }

    public interface Where {
        Build where(List<Column> columns);
    }

    public interface Build {
        String toString();
    }

    private static class SqlBuilderSteps implements Select, From, Update, Where, Build {

        private final StringBuilder sql;

        private SqlBuilderSteps(StringBuilder sql) {
            this.sql = sql;
        }

        @Override
        public From columns(List<Column> columns) {
            String columnSelect = columns.stream()
                    .map(Column::getName)
                    .collect(joining(", "));
            sql.append(columnSelect);
            return this;
        }

        @Override
        public Where from(String tableName) {
            sql.append(" FROM ").append(tableName);
            return this;
        }

        @Override
        public Where set(List<Column> columns) {
            sql.append(" SET ");
            String columnUpdate = columns.stream()
                    .filter(Column::isNotPrimaryKey)
                    .map(col -> col + " = ?")
                    .collect(joining(", "));
            sql.append(columnUpdate);
            return this;
        }

        @Override
        public Build where(List<Column> columns) {
            if (!columns.isEmpty()) {
                sql.append(" WHERE ");

                String expr = columns.stream()
                        .map(col -> col + " = ?")
                        .collect(joining(" AND "));
                sql.append(expr);
            }
            return this;
        }

        @Override
        public String toString() {
            return sql.toString();
        }
    }

}
