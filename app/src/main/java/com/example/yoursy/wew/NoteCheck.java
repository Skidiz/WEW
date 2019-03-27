package com.example.yoursy.wew;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yoursy on 13/02/2018.
 */
public class NoteCheck extends SQLiteOpenHelper {

    static String DATABASE_NAME = "DOQUE";
    public static final String TABLE_NAME = "NOTES";
    public static final String KEY_ID = "id";
    public static final String KEY_CATEGORY = "TITLE";
    public static final String KEY_NOTE = "NOTE";
    public static final String KEY_CATEG = "CATEGORY";
    public static final String KEY_DATE_CREATED = "DATE_CREATED";
    public static final String KEY_TIME_CREATED = "TIME_CREATED";


    public NoteCheck(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY  , " + KEY_CATEGORY + " TEXT, "
                + KEY_NOTE + " TEXT, " + KEY_CATEG + " TEXT, " + KEY_DATE_CREATED + " DATETIME DEFAULT CURRENT_DATE, "
                + KEY_TIME_CREATED + " DATETIME DEFAULT CURRENT_TIME )";
        database.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
