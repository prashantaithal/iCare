package com.example.prash.cmpe295b;

public class SensorDataSQL {

    private SensorDataSQL() {}

    /* Inner class that defines the table contents */
    private static final String _ID = "SERIALNO";
    public static final String TABLE_NAME = "SENSORTABLE";
    public static final String COLUMN1_NAME_TITLE = "PULSE";
    public static final String COLUMN2_NAME_TITLE = "TEMP";
    public static final String COLUMN3_NAME_TITLE = "ACCX";
    public static final String COLUMN4_NAME_TITLE = "ACCY";
    public static final String COLUMN5_NAME_TITLE = "ACCZ";
    public static final String COLUMN6_NAME_TITLE = "GYRX";
    public static final String COLUMN7_NAME_TITLE = "GYRY";
    public static final String COLUMN8_NAME_TITLE = "GYRZ";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + SensorDataSQL.TABLE_NAME + " (" +
                    SensorDataSQL._ID + " INTEGER PRIMARY KEY," +
                    SensorDataSQL.COLUMN1_NAME_TITLE + " TEXT," +
                    SensorDataSQL.COLUMN2_NAME_TITLE + " TEXT," +
                    SensorDataSQL.COLUMN3_NAME_TITLE + " TEXT," +
                    SensorDataSQL.COLUMN4_NAME_TITLE + " TEXT," +
                    SensorDataSQL.COLUMN5_NAME_TITLE + " TEXT," +
                    SensorDataSQL.COLUMN6_NAME_TITLE + " TEXT," +
                    SensorDataSQL.COLUMN7_NAME_TITLE + " TEXT," +
                    SensorDataSQL.COLUMN8_NAME_TITLE + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DELETE FROM " + SensorDataSQL.TABLE_NAME + ";\n" +
                    "DELETE FROM sqlite_sequence where name=" + SensorDataSQL.TABLE_NAME;

}
