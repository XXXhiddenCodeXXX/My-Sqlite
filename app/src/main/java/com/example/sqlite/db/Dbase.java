package com.example.sqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

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
    public long insertData(String nama, int nim) {
        try {
            ContentValues values = new ContentValues();
            values.put(Const.NAMA_MAHASISWA,  nama);
            values.put(Const.NIM, nim);

            return db.insert(Const.TABLE_NAME, null, values);
        } catch (SQLiteException e) {
            return -1;
        }
    }

    // delete data
    public boolean deleteData(int key) {
        return db.delete(Const.TABLE_NAME, Const.ID_MAHASISWA + "=" + key, null) > 0;
    }

    // get all data
    public Cursor getAllData() {
        return db.query(Const.TABLE_NAME,
                null, null, null, null, null, Const.ID_MAHASISWA + " ASC");
    }

    // edit data
    public boolean updateData(int key, String name, String nim) {
        ContentValues values = new ContentValues();
        values.put(Const.NAMA_MAHASISWA, name);
        values.put(Const.NIM, nim);

        return db.update(Const.TABLE_NAME, values, Const.ID_MAHASISWA + "=" + key, null) > 0;
    }
}
