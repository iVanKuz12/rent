package ru.kuznecov.ivan.rent.database;

public class RentDBScheme {
    public static final class UserTable{
        public static final String NAME = "user";

        public static final class Cols {
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String DATE = "date";
            public static final String PHONE = "phone";
            public static final String EMAIL = "email";
            public static final String PASSWORD = "password";
            public static final String PHOTO = "photo";
        }
    }
}
