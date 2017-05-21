package ru.levry.dbc.meta;

import org.junit.jupiter.api.Test;

import java.sql.JDBCType;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author levry
 */
class SqlTypeNameTests {

    @Test
    void simpleType() {
        SqlTypeName typeName = new SqlTypeName();

        String type = typeName.toString(JDBCType.INTEGER, "integer", null, null);

        assertEquals("integer", type);
    }

    @Test
    void typeWithSize() {
        SqlTypeName typeName = new SqlTypeName()
                .withSize(JDBCType.VARCHAR);

        String type = typeName.toString(JDBCType.VARCHAR, "varchar", 255, null);

        assertEquals("varchar(255)", type);
    }

    @Test
    void typeWithDigits() {
        SqlTypeName typeName = new SqlTypeName()
                .withDigits(JDBCType.DECIMAL);

        String type = typeName.toString(JDBCType.DECIMAL, "decimal", 10, 2);

        assertEquals("decimal(10, 2)", type);
    }
}