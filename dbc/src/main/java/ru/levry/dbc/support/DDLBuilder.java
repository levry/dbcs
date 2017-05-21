package ru.levry.dbc.support;

import ru.levry.dbc.meta.Column;

import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * @author levry
 */
public class DDLBuilder {

    private DDLBuilder() {
    }

    public static Create create() {
        return new DDLBuilderSteps("CREATE TABLE ");
    }

    public interface Create {
        Columns table(String tableName);
    }

    public interface Columns {
        Constraints columns(List<Column> columns);
    }

    public interface Constraints {
        Build primaryKey(List<Column> columns);
    }

    public interface Build {
        String toString();
    }

    private static class DDLBuilderSteps implements Create, Columns, Constraints, Build {

        private final StringBuilder ddl;

        private DDLBuilderSteps(String s) {
            this(new StringBuilder(s));
        }

        private DDLBuilderSteps(StringBuilder ddl) {
            this.ddl = ddl;
        }

        @Override
        public Columns table(String tableName) {
            ddl.append(tableName).append(" (");
            return this;
        }

        @Override
        public Constraints columns(List<Column> columns) {
            String definitions = columns.stream()
                    .map(Column::toDefinition)
                    .collect(joining(", "));
            ddl.append(definitions);
            return this;
        }

        @Override
        public Build primaryKey(List<Column> columns) {
            if(!columns.isEmpty()) {
                ddl.append(", ");
                ddl.append("CONSTRAINT PRIMARY KEY (");
                String keys = columns.stream()
                        .map(Column::getName)
                        .collect(joining(", "));
                ddl.append(keys);
                ddl.append(")");
            }
            ddl.append(")");
            return this;
        }

        @Override
        public String toString() {
            return ddl.toString();
        }
    }
}
