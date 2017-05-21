package ru.levry.dbc.support;

import org.junit.jupiter.api.Test;
import ru.levry.dbc.meta.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author levry
 */
class SqlBuilderTests {

    @Test
    void select() {
        String sql = SqlBuilder.select().toString();

        assertEquals("SELECT ", sql);
    }

    @Test
    void select_columns() {
        List<Column> columns = Arrays.asList(
                column("id"),
                column("name")
        );

        String sql = SqlBuilder.select()
                .columns(columns)
                .toString();

        assertEquals("SELECT id, name", sql);
    }

    @Test
    void select_columns_from() {
        List<Column> columns = Arrays.asList(
                column("id"),
                column("name")
        );

        String sql = SqlBuilder.select()
                .columns(columns)
                .from("Catalog")
                .toString();

        assertEquals("SELECT id, name FROM Catalog", sql);
    }

    @Test
    void select_columns_from_where() {
        List<Column> columns = Arrays.asList(
                column("id"),
                column("name")
        );
        List<Column> keys = Collections.singletonList(
                column("code")
        );

        String sql = SqlBuilder.select()
                .columns(columns)
                .from("Catalog")
                .where(keys)
                .toString();

        assertEquals("SELECT id, name FROM Catalog WHERE code = ?", sql);
    }

    @Test
    void update() {
        String sql = SqlBuilder.update("Users").toString();

        assertEquals("UPDATE Users", sql);
    }

    @Test
    void update_set() {
        List<Column> columns = Arrays.asList(
                column("username"),
                column("comment")
        );

        String sql = SqlBuilder.update("Users")
                .set(columns)
                .toString();

        assertEquals("UPDATE Users SET username = ?, comment = ?", sql);
    }

    @Test
    void update_set_where() {
        List<Column> columns = Arrays.asList(
                column("username"),
                column("comment")
        );
        List<Column> keys = Collections.singletonList(
                column("id")
        );

        String sql = SqlBuilder.update("Users")
                .set(columns)
                .where(keys)
                .toString();

        assertEquals("UPDATE Users SET username = ?, comment = ? WHERE id = ?", sql);
    }

    private Column column(String name) {
        return new Column(name);
    }
}