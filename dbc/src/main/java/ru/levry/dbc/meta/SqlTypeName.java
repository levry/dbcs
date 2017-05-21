package ru.levry.dbc.meta;

import java.sql.JDBCType;
import java.util.EnumMap;

/**
 * @author levry
 */
class SqlTypeName {

    @FunctionalInterface
    private interface TypeNamePattern {
        String format(Integer size, Integer decimalDigits);
    }

    private final EnumMap<JDBCType, TypeNamePattern> typeNamePatterns = new EnumMap<>(JDBCType.class);

    private static final TypeNamePattern WITH_NONE = (size, decimalDigits) -> "";
    private static final TypeNamePattern WITH_SIZE = (size, decimalDigits) -> "(" + size + ")";
    private static final TypeNamePattern WITH_DIGITS = (size, decimalDigits) -> "(" + size + ", " + decimalDigits + ")";

    public SqlTypeName withSize(JDBCType jdbcType) {
        typeNamePatterns.put(jdbcType, WITH_SIZE);
        return this;
    }

    public SqlTypeName withDigits(JDBCType jdbcType) {
        typeNamePatterns.put(jdbcType, WITH_DIGITS);
        return this;
    }

    String toString(JDBCType type, String typeName, Integer size, Integer decimalDigits) {
        return typeName + typeNamePatterns.getOrDefault(type, WITH_NONE).format(size, decimalDigits);
    }

    String toString(int dataType, String typeName, Integer size, Integer decimalDigits) {
        JDBCType jdbcType = JDBCType.valueOf(dataType);
        return toString(jdbcType, typeName, size, decimalDigits);
    }

    private static final SqlTypeName defaultTypeNames = new SqlTypeName()
            .withDigits(JDBCType.DECIMAL)
            .withDigits(JDBCType.NUMERIC)
            .withSize(JDBCType.VARCHAR)
            .withSize(JDBCType.NVARCHAR)
            .withSize(JDBCType.CHAR)
            .withSize(JDBCType.NCHAR);

    static String typeToString(int dataType, String typeName, Integer size, Integer decimalDigits) {
        return defaultTypeNames.toString(dataType, typeName, size, decimalDigits);
    }
}
