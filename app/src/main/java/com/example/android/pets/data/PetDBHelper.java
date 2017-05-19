package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.pets.data.PetContract.PetEntry;
/**
 * Created by mwp20 on 5/14/2017.
 */

public class PetDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "shelter.db";
    public static final int DATABASE_VERSION = 1;
    public static final String CREATE_ENTRIES = "CREATE TABLE " + PetEntry.TABLE_NAME + " (" +
            PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PetEntry.COLUMN_NAME + " TEXT NOT NULL, "
            + PetEntry.COLUMN_BREED + " TEXT, "
            + PetEntry.COLUMN_GENDER + " INTEGER NOT NULL, "
            + PetEntry.COLUMN_WEIGHT + " INTEGER NOT NULL DEFAULT 2);";

    public static final String DELETE_ENTRIES = "DROP TABLE FROM " + PetEntry.TABLE_NAME;

    public PetDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
