package ru.kuznecov.ivan.rent.network;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class ApiServer {
    private static final String BASE = "http://192.168.0.3:8080";
    private static final String USER_GET_ALL = "/user/getAll";
    private static final String USER_GET_ONE = "/user/getOne";
    private static final String USER_GET_LOG_IN = "/user/getByEmailAndPassword";
    private static final String USER_SET_ONE = "/user/setOne";
    private static final String THING_GET_ALL= "/thing/getAll";
    private static final String THING_GET_ONE= "/thing/getOne";
    private static final String THING_SET_ONE= "/thing/setOne";



    public static URL getAllUser() throws MalformedURLException {
        Uri uri = Uri.parse(BASE + USER_GET_ALL);
        return new URL(uri.toString()) ;
    }

    public static URL getOneUser(long id) throws MalformedURLException {
        Uri uri = Uri.parse(BASE + USER_GET_ONE)
                .buildUpon()
                .appendQueryParameter("id", String.valueOf(id))
                .build();
        return new URL(uri.toString());
    }
    public static URL getLogIn(String email, String password) throws MalformedURLException {
        Uri uri = Uri.parse(BASE + USER_GET_LOG_IN)
                .buildUpon()
                .appendQueryParameter("email" , email)
                .appendQueryParameter("password", password)
                .build();
        return new URL(uri.toString());
    }

    public static URL setOneUser() throws MalformedURLException {
        Uri uri = Uri.parse(BASE + USER_SET_ONE);
        return new URL(uri.toString());
    }

    public static URL getAllThings() throws MalformedURLException {
        Uri uri = Uri.parse(BASE + THING_GET_ALL);
        return new URL(uri.toString()) ;
    }

    public static URL getOneThing(long id) throws MalformedURLException {
        Uri uri = Uri.parse(BASE + THING_GET_ONE)
                .buildUpon()
                .appendQueryParameter("id", String.valueOf(id))
                .build();
        return new URL(uri.toString());
    }

    public static URL setOneThing() throws MalformedURLException {
        Uri uri = Uri.parse(BASE + THING_SET_ONE);
        return new URL(uri.toString());
    }


}
