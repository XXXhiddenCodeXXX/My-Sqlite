package com.example.sqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by David Sanjaya on 3/16/2020
 * Sqlite
 */
public class Dbase {
    private SQLiteDatabase db;
    private final Context context;
    private final DbHelper dbHelper;

    public Dbase(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context, Const.DB_NAME, null, Const.DB_VERSION);
    }

    // Open database
    public void open() {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    // close database
    public void close() {
        db.close();
    }

    // add data
    public long insertData(String nama, String nim) {
        try {
            ContentValues values = new ContentValues();
            values.put(Const.NAMA_MAHASISWA,  nama);
            values.put(Const.NIM, nim);

            return db.insert(Const.TABLE_NAME, null, values);
        } catch (Exception e) {
            return -1;
        }
    }

    // delete data
    public boolean deleteData(long key) {
        return db.delete(Const.TABLE_NAME, Const.ID_MAHASISWA + "=" + key, null) > 0;
    }

    // get all data
    public Cursor getAllData() {
        return db.query(Const.TABLE_NAME,
                new String[]{Const.ID_MAHASISWA,
                        Const.NAMA_MAHASISWA,
                        Const.NIM},
                null, null, null, null, null);
    }
}
