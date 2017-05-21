package ru.levry.dbc.meta;

import java.util.Objects;

/**
 * @author levry
 */
public class Column {

    private final String name;
    private boolean primaryKey;
    private boolean nullable;
    private String typeName;
    private Integer size;
    private Integer decimalDigits;
    private int dataType;

    public Column(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isNotPrimaryKey() {
        return !primaryKey;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }


    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getDataType() {
        return dataType;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }

    public void setDecimalDigits(Integer decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public Integer getDecimalDigits() {
        return decimalDigits;
    }

    public String getType() {
        return SqlTypeName.typeToString(dataType, typeName, size, decimalDigits);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Column)) {
            return false;
        }

        Column other = (Column) o;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public String toDefinition() {
        StringBuilder buf = new StringBuilder(getName());
        buf.append(" ").append(getType());
        if(!isNullable()) {
            buf.append(" NOT NULL");
        }
        return buf.toString();
    }
}
