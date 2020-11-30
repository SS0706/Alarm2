package com.example.alarmproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

public final class DataBase extends SQLiteOpenHelper {
    //declare the final variables
    private static final String DATABASE_NAME = "alarms.db";
    private static final int SCHEMA = 1;

    private static final String TABLE_NAME = "alarms";

    public static final String _ID = "_id";
    public static final String column_TIME = "time";
    public static final String column_LABEL = "label";
    public static final String column_MON = "monday";
    public static final String column_TUES = "tuesday";
    public static final String column_WED = "wednesday";
    public static final String column_THURS = "thursday";
    public static final String column_FRI = "friday";
    public static final String column_SAT = "saturday";
    public static final String column_SUN = "sunday";
    public static final String column_IS_ENABLED = "is_enabled";

    private static DataBase alarmDatabase = null;

    public static synchronized DataBase getInstance(Context context) {
        if (alarmDatabase == null) {
            alarmDatabase = new DataBase(context.getApplicationContext());
        }
        return alarmDatabase;
    }

    private DataBase(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.i(getClass().getSimpleName(), "Creating database...");
        //creating an alarm table in sqlite database
        final String CreateAlarmTable = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                column_TIME + " INTEGER NOT NULL, " +
                column_LABEL + " TEXT, " +
                column_SUN + " INTEGER NOT NULL, " +
                column_MON + " INTEGER NOT NULL, " +
                column_TUES + " INTEGER NOT NULL, " +
                column_WED + " INTEGER NOT NULL, " +
                column_THURS + " INTEGER NOT NULL, " +
                column_FRI + " INTEGER NOT NULL, " +
                column_SAT + " INTEGER NOT NULL, " +
                column_IS_ENABLED + " INTEGER NOT NULL" +
                ");";

        sqLiteDatabase.execSQL(CreateAlarmTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        throw new UnsupportedOperationException("This shouldn't happen yet!");
    }
    // add a new alarm when the user creates
    public long addAlarm() {
        return addAlarm(new Alarm());
    }

    long addAlarm(Alarm alarm) {
        return getWritableDatabase().insert(TABLE_NAME, null, UtilsAlarm.toContentValues(alarm));
    }

    public int updateAlarm(Alarm alarm) {
        final String where = _ID + "=?";
        final String[] whereArgs = new String[] { Long.toString(alarm.getId()) };
        return getWritableDatabase()
                .update(TABLE_NAME, UtilsAlarm.toContentValues(alarm), where, whereArgs);
    }
    //call this method in ui part to delete an alarm by the user
    public int deleteAlarm(Alarm alarm) {
        return deleteAlarm(alarm.getId());
    }

    int deleteAlarm(long id) {
        final String where = _ID + "=?";
        final String[] whereArgs = new String[] { Long.toString(id) };
        return getWritableDatabase().delete(TABLE_NAME, where, whereArgs);
    }

    public List<Alarm> getAlarms() {

        Cursor cur = null;

        try{
            cur = getReadableDatabase().query(TABLE_NAME, null, null, null, null, null, null);
            return UtilsAlarm.buildAlarmList(cur);
        } finally {
            if (cur != null && !cur.isClosed()) cur.close();
        }

    }

}
