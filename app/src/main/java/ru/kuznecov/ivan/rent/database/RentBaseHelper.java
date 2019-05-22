package ru.kuznecov.ivan.rent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import ru.kuznecov.ivan.rent.database.RentDBScheme.UserTable;

public class RentBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "rentBase.db";

    public RentBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" create table " + UserTable.NAME + "(" +
                UserTable.Cols.ID + ", " +
                UserTable.Cols.NAME + ", " +
                UserTable.Cols.DATE + ", " +
                UserTable.Cols.PHONE +  ", " +
                UserTable.Cols.EMAIL +  ", " +
                UserTable.Cols.PASSWORD + ", " +
                UserTable.Cols.PHOTO +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
