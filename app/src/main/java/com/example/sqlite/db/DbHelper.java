package com.example.sqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by David Sanjaya on 3/16/2020
 * Sqlite
 */
public class DbHelper extends SQLiteOpenHelper {
    // for create table
    private final static String CREATE_TABLE = "create table " +
            Const.TABLE_NAME + " (" +
            Const.ID_MAHASISWA + " integer primary key autoincrement, " +
            Const.NAMA_MAHASISWA + " text not null, " +
            Const.NIM + " integer not null);";

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory f, int version) {
        super(context, name, f, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch (Exception e) {
            Log.e(Const.TAG, "onCreate: ", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " +
                Const.TABLE_NAME);
        onCreate(db);
    }
}
