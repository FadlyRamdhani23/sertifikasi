package org.d3if3127.sertifikasiaplikasikpu.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "kpu.dp";

    public static final String TABLE_SQLite = "sqlite";

    public static final String COLUMN_ID = "id";

    public static final String COLUMN_NAMA = "nama";

    public static final String COLUMN_NIK = "nik";

    public static final String COLUMN_NO_HP = "no_hp";

    public static final String COLUMN_JENIS_KELAMIN = "jenis_kelamin";

    public static final String COLUMN_TANGGAL = "tanggal";

    public static final String COLUMN_ALAMAT = "alamat";

    public static final String COLUMN_AVATAR = "gambar";

    
    


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_SQLite + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, " +
                COLUMN_NAMA + " TEXT NOT NULL, " +
                COLUMN_NIK + " TEXT NOT NULL, " +
                COLUMN_NO_HP + " TEXT NOT NULL, " +
                COLUMN_JENIS_KELAMIN + " TEXT NOT NULL, " +
                COLUMN_TANGGAL + " TEXT NOT NULL, " +
                COLUMN_ALAMAT + " TEXT NOT NULL," +
                COLUMN_AVATAR + " BLOB NOT NULL" +
                " )";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SQLite);
        onCreate(db);
    }

    public ArrayList<HashMap<String, Object>> getAllData() {
        ArrayList<HashMap<String, Object>> wordList;
        wordList = new ArrayList<HashMap<String, Object>>();
        String selectQuery = "SELECT * FROM " + TABLE_SQLite;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, Object> map = new HashMap<String, Object>();

                map.put(COLUMN_ID, cursor.getString(0));
                map.put(COLUMN_NAMA, cursor.getString(1));
                map.put(COLUMN_NIK, cursor.getString(2));
                map.put(COLUMN_NO_HP, cursor.getString(3));
                map.put(COLUMN_JENIS_KELAMIN, cursor.getString(4));
                map.put(COLUMN_TANGGAL, cursor.getString(5));
                map.put(COLUMN_ALAMAT, cursor.getString(6));
                map.put(COLUMN_AVATAR, cursor.getBlob(7));


                wordList.add(map);
            } while (cursor.moveToNext());
        }
        Log.e("select sqlite ", "" + wordList);
        database.close();
        return wordList;
    }

    public void insert(String nik, String nama, String no_hp, String jenis_kelamin, String tanggal, String alamat, byte[] gambar) {
        SQLiteDatabase database = this.getWritableDatabase();
        String queryValues = "INSERT INTO " + TABLE_SQLite + " (nama, nik, no_hp, jenis_kelamin, tanggal, alamat, gambar) " +
                "VALUES ('" + nama + "', '" + nik + "', '" + no_hp + "', '" + jenis_kelamin + "', '" + tanggal + "', '" + alamat + "', ?)";

        Log.e("insert sqlite ", "" + queryValues);
        database.execSQL(queryValues, new Object[]{gambar});
        database.close();
    }

    public void update(int id, String nik, String nama, String no_hp, String jenis_kelamin, String tanggal, String alamat, byte[] gambar) {
        SQLiteDatabase database = this.getWritableDatabase();

        String updateQuery = "UPDATE " + TABLE_SQLite + " SET "
                + COLUMN_NIK + "='" + nik + "', "
                + COLUMN_NAMA + "='" + nama + "', "
                + COLUMN_NO_HP + "='" + no_hp + "', "
                + COLUMN_JENIS_KELAMIN + "='" + jenis_kelamin + "', "
                + COLUMN_TANGGAL + "='" + tanggal + "', "
                + COLUMN_ALAMAT + "='" + alamat + "',"
                + COLUMN_AVATAR + "= ?"
                + " WHERE " + COLUMN_ID + "=" + "'" + id + "'";
        Log.e("update sqlite ", updateQuery);
        database.execSQL(updateQuery, new Object[]{gambar});
        database.close();
    }

    public void delete(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "DELETE FROM " + TABLE_SQLite + " WHERE " + COLUMN_ID + "=" + "'" + id + "'";
        Log.e("update sqlite ", updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

    public boolean cekData() {
        SQLiteDatabase database = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + TABLE_SQLite;
        Cursor mcursor = database.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            return true;
        } else {
            return false;
        }
    }
}

