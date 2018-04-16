package com.example.prash.cmpe295b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class SensorDataSQLHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SensorDataSQL.db";
    private static final int DATABASE_VERSION = 1;

    public SensorDataSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SensorDataSQL.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SensorDataSQL.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void truncateTable(SQLiteDatabase db) {
        db.execSQL(SensorDataSQL.SQL_DELETE_ENTRIES);
    }

    public void insertHome(Data data) throws Exception {

        SQLiteDatabase db = this.getWritableDatabase();
        this.onCreate(db);


        ContentValues values = new ContentValues();
        values.put(SensorDataSQL.COLUMN1_NAME_TITLE, data.getPulseSQL());
        values.put(SensorDataSQL.COLUMN2_NAME_TITLE, data.getTempSQL());
        values.put(SensorDataSQL.COLUMN3_NAME_TITLE, data.getAccxSQL());
        values.put(SensorDataSQL.COLUMN4_NAME_TITLE, data.getAccySQL());
        values.put(SensorDataSQL.COLUMN5_NAME_TITLE, data.getAcczSQL());
        values.put(SensorDataSQL.COLUMN6_NAME_TITLE, data.getGyrxSQL());
        values.put(SensorDataSQL.COLUMN7_NAME_TITLE, data.getGyrySQL());
        values.put(SensorDataSQL.COLUMN8_NAME_TITLE, data.getGyrzSQL());
        long newRowId = db.insert(SensorDataSQL.TABLE_NAME, null, values);

        db.close();
    }

    public ArrayList<Data> obtainSensorDataBase() {

        ArrayList<Data> datalist = new ArrayList<Data>();
        Double tempData[] = new Double[8];
        SQLiteDatabase db = this.getReadableDatabase();

        this.onCreate(db);

        try {
            Cursor cursorObj = db.rawQuery("SELECT * FROM " + SensorDataSQL.TABLE_NAME, null);

            if (cursorObj.moveToFirst()) {
                do {

                    Data sensorData = new Data();

                    // Assigning row fields to variables ...
                    tempData[0] = cursorObj.getDouble(1);
                    tempData[1] = cursorObj.getDouble(2);
                    //Log.d("YahooValue", "SHit5");

                    tempData[2] = cursorObj.getDouble(3);
                    tempData[3] = cursorObj.getDouble(4);
                    tempData[4] = cursorObj.getDouble(5);
                    tempData[5] = cursorObj.getDouble(6);
                    tempData[6] = cursorObj.getDouble(7);
                    tempData[7] = cursorObj.getDouble(8);

                    sensorData.setData(tempData);

                    datalist.add(sensorData);
                } while (cursorObj.moveToNext());
            }
            cursorObj.close();

            db.close();
        } catch (Exception e) {
            Log.d("Can not read", "Fix");
        }
        return datalist;
    }

    public void deleteItem(String address) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] whereArgs = new String[]{String.valueOf(address)};
            this.onCreate(db);
            db.delete(SensorDataSQL.TABLE_NAME, SensorDataSQL.COLUMN2_NAME_TITLE + "=?", whereArgs);
            db.close();
        } catch (Exception e) {
            Log.e("Unable to delete", "solve the issue");
        }
    }

    public void updateItem(String[] val, String address) {
        try {
            System.out.println("Entered");
            SQLiteDatabase db = this.getReadableDatabase();
            this.onCreate(db);
            ContentValues values = new ContentValues();
            values.put(SensorDataSQL.COLUMN1_NAME_TITLE, val[0]);
            values.put(SensorDataSQL.COLUMN3_NAME_TITLE, val[2]);
            values.put(SensorDataSQL.COLUMN4_NAME_TITLE, val[3]);
            values.put(SensorDataSQL.COLUMN5_NAME_TITLE, val[4]);
            values.put(SensorDataSQL.COLUMN6_NAME_TITLE, val[5]);
            values.put(SensorDataSQL.COLUMN7_NAME_TITLE, val[6]);
            values.put(SensorDataSQL.COLUMN8_NAME_TITLE, val[7]);
            String[] whereArgs = new String[]{String.valueOf(address)};
            db.update(SensorDataSQL.TABLE_NAME, values, SensorDataSQL.COLUMN2_NAME_TITLE + "=?", whereArgs);
            db.close();
        } catch (Exception e) {
            Log.e("Unable to delete", "solve the issue");
        }
    }

}
