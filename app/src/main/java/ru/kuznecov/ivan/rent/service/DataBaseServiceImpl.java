package ru.kuznecov.ivan.rent.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ru.kuznecov.ivan.rent.database.RentBaseHelper;
import ru.kuznecov.ivan.rent.database.RentCursorWrapper;
import ru.kuznecov.ivan.rent.model.User;

import static ru.kuznecov.ivan.rent.database.RentDBScheme.*;

public class DataBaseServiceImpl implements DataBaseService {

    private SQLiteDatabase mDataBase;


    public DataBaseServiceImpl(Context context) {

        mDataBase = new RentBaseHelper(context).getWritableDatabase();
    }

    private static ContentValues getContValUser(User user){
        ContentValues values = new ContentValues();
        values.put(UserTable.Cols.ID, user.getId());
        values.put(UserTable.Cols.NAME, user.getName());
        values.put(UserTable.Cols.DATE, user.getDate().getTime());
        values.put(UserTable.Cols.PHONE, user.getPhone());
        values.put(UserTable.Cols.EMAIL, user.getEmail());
        values.put(UserTable.Cols.PASSWORD, user.getPassword());
        values.put(UserTable.Cols.PHOTO, user.getPhoto());
        return values;
    }

    private RentCursorWrapper queryUser(String whereClause, String[] whereArgs) {
        Cursor cursor = mDataBase.query(
                UserTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new RentCursorWrapper(cursor);
    }

    @Override
    public void update(User user) {
        ContentValues values = getContValUser(user);
        mDataBase.update(UserTable.NAME, values, null, null);
    }

    @Override
    public void saveUser(User user) {
        ContentValues values = getContValUser(user);
        mDataBase.insert(UserTable.NAME, null, values);
    }

    @Override
    public User readUser() {
        RentCursorWrapper cursor = queryUser(null,null);
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getUser();
        } finally {
            cursor.close();
        }
    }

    @Override
    public void deleteUser() {
        mDataBase.delete(UserTable.NAME, null, null);
    }
}
