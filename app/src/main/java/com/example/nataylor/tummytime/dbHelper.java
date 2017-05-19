package com.example.nataylor.tummytime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nataylor on 5/1/17.
 */

public class dbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "tummyTime.db";
    private static final String TABLE_NAME = "tummyTime";
    private static final String KEY_ID = "id";
    private static final String KEY_DATE= "date";
    private static final String KEY_START = "start";
    private static final String KEY_TIME = "time";

    private static dbHelper dbHelperInstance = null;
    private static Context mContext;

    public static synchronized dbHelper newInstance(Context context) {
        if (dbHelperInstance == null){
            dbHelperInstance = new dbHelper(context.getApplicationContext());
        }

        return dbHelperInstance;
    }

    private dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        String DATABASE_TABLE_CREATE = "CREATE TABLE " + TABLE_NAME +"("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_DATE + " TEXT,"
                + KEY_START + " TEXT," + KEY_TIME + " INTEGER" + ")";
        db.execSQL(DATABASE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

            // Create tables again
            onCreate(db);
        }

    public void addTime (Tummy tummy) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, tummy.getDate());
        values.put(KEY_START, tummy.getStart());
        values.put(KEY_TIME, tummy.getTime());

        db.insert(TABLE_NAME, null, values);
        Log.d("Inserting", tummy.getDate());
        db.close();
    }

    public List<Tummy> getTime (String date) {
        Log.d("search", date);
        List<Tummy> tummyList = new ArrayList<Tummy>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_DATE + "=" + "'" +date + "'";
        Log.d("Query", selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Tummy tummy = new Tummy();
                tummy.setDate(cursor.getString(1));
                tummy.setStart(cursor.getString(2));
                tummy.setTime(cursor.getLong(3));
                tummyList.add(tummy);
            } while (cursor.moveToNext());
        } else {
            Log.d("Shit", "The query was empty");
        }

        return tummyList;
    }
}
