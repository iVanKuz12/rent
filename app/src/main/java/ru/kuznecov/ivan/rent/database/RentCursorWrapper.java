package ru.kuznecov.ivan.rent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;

import ru.kuznecov.ivan.rent.database.RentDBScheme.UserTable;
import ru.kuznecov.ivan.rent.model.User;

public class RentCursorWrapper extends CursorWrapper {

    public RentCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser(){
        long id = getLong (getColumnIndex(UserTable.Cols.ID));
        String name = getString(getColumnIndex(UserTable.Cols.NAME));
        long date = getLong(getColumnIndex(UserTable.Cols.DATE));
        String phone = getString(getColumnIndex(UserTable.Cols.PHONE));
        String email = getString(getColumnIndex(UserTable.Cols.EMAIL));
        String password = getString(getColumnIndex(UserTable.Cols.PASSWORD));

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setDate(new Date(date));
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
