package com.katyshevtseva.kikiorgmobile.db.lib;


import static com.katyshevtseva.kikiorgmobile.db.lib.DbLibConstants.DATE_FORMAT;
import static com.katyshevtseva.kikiorgmobile.db.lib.DbLibConstants.DATE_TIME_FORMAT;

import com.katyshevtseva.kikiorgmobile.utils.knobs.OneInOneOutKnob;
import com.katyshevtseva.kikiorgmobile.utils.knobs.OneOutKnob;
import com.katyshevtseva.kikiorgmobile.utils.knobs.TwoInKnob;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DbTable<T> {
    private final String name;
    private final Column<T> idColumn;
    private final List<Column<T>> contentColumns;
    private final OneOutKnob<T> emptyObjectSupplier;

    public DbTable(String name, Column<T> idColumn, List<Column<T>> contentColumns, OneOutKnob<T> emptyObjectSupplier) {
        this.name = name;
        this.idColumn = idColumn;
        this.contentColumns = contentColumns;
        this.emptyObjectSupplier = emptyObjectSupplier;
    }

    List<Column<T>> getAllColumns() {
        List<Column<T>> columns = new ArrayList<>(contentColumns);
        columns.add(idColumn);
        return columns;
    }

    public String getName() {
        return name;
    }

    public Column<T> getIdColumn() {
        return idColumn;
    }

    public List<Column<T>> getContentColumns() {
        return contentColumns;
    }

    public OneOutKnob<T> getEmptyObjectSupplier() {
        return emptyObjectSupplier;
    }

    public static class Column<T> {
        private final String name;
        private ColumnDbType dbType;
        private final ColumnActualType actualType;
        private final OneInOneOutKnob<T, Object> actualValueSupplier;
        private final TwoInKnob<T, Object> actualValueReceiver;

        public Column(String name, ColumnActualType actualType,
               OneInOneOutKnob<T, Object> actualValueSupplier,
               TwoInKnob<T, Object> actualValueReceiver) {
            this.name = name;
            this.actualType = actualType;
            this.actualValueSupplier = actualValueSupplier;
            this.actualValueReceiver = actualValueReceiver;
            switch (actualType) {
                case STRING:
                case DATE:
                case DATE_TIME:
                    dbType = ColumnDbType.STRING;
                    break;
                case LONG:
                case BOOLEAN:
                    dbType = ColumnDbType.LONG;
            }
        }

        String getDbValueByActualValue(T t) {
            Object actualValue = actualValueSupplier.execute(t);
            switch (actualType) {
                case STRING:
                case LONG:
                    return "" + actualValue;
                case BOOLEAN:
                    return ((Boolean) actualValue) ? "1" : "0";
                case DATE:
                    return DATE_FORMAT.format(actualValue);
                case DATE_TIME:
                    return DATE_TIME_FORMAT.format(actualValue);
            }
            throw new RuntimeException();
        }

        Object getActualValueByDbValue(Object dbValue) {
            switch (actualType) {
                case LONG:
                case STRING:
                    return dbValue;
                case BOOLEAN:
                    return ((long) dbValue) == 1;
                case DATE:
                    try {
                        return DATE_FORMAT.parse((String) dbValue);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                case DATE_TIME:
                    try {
                        return DATE_TIME_FORMAT.parse((String) dbValue);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
            }
            throw new RuntimeException();
        }

        public String getName() {
            return name;
        }

        public ColumnDbType getDbType() {
            return dbType;
        }

        public ColumnActualType getActualType() {
            return actualType;
        }

        public OneInOneOutKnob<T, Object> getActualValueSupplier() {
            return actualValueSupplier;
        }

        public TwoInKnob<T, Object> getActualValueReceiver() {
            return actualValueReceiver;
        }
    }

    public enum ColumnDbType {
        STRING, LONG
    }

    public enum ColumnActualType {
        STRING, LONG, DATE, BOOLEAN, DATE_TIME
    }
}
