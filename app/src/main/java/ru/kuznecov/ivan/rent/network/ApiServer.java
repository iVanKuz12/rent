package ru.kuznecov.ivan.rent.network;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class ApiServer {

    //User
    private static final String BASE = "http://192.168.0.3:8080";
    private static final String USER_GET_ALL = "/user/getAll";
    private static final String USER_GET = "/user/getUser";
    private static final String USER_GET_LOG_IN = "/user/getByEmailAndPassword";
    private static final String USER_SET = "/user/setUser";
    private static final String USER_UPDATE = "/user/updateUser";
    private static final String USER_EMAIL = "/user/email";
    private static final String USER_PHONE = "/user/phone";

    //City
    private static final String CITY_GETALL = "/city/getAll";

    //District
    private static final String DISTRICT_GETALL = "/district/getAllParentId";

    //Category
    private static final String CATEGORY_GETALL = "/category/getAll";

    //SubCategory
    private static final String SUBCATEGORY_GETALL = "/subCategory/getAllParentId";

    //Things
    private static final String THING_GET_ALL= "/thing/getAll";
    private static final String THING_GET_ONE= "/thing/getOne";
    private static final String THING_ADD = "/thing/addThing";
    private static final String THING_ADD_PHOTO = "/thing/addPhotoThing";



    public static URL getAllUser() throws MalformedURLException {
        Uri uri = Uri.parse(BASE + USER_GET_ALL);
        return new URL(uri.toString()) ;
    }

    public static URL getAllCity() throws MalformedURLException {
        Uri uri = Uri.parse(BASE + CITY_GETALL);
        return new URL(uri.toString()) ;
    }

    public static URL getAllCateory() throws MalformedURLException {
        Uri uri = Uri.parse(BASE + CATEGORY_GETALL);
        return new URL(uri.toString()) ;
    }

    public static URL getUser(long id) throws MalformedURLException {
        Uri uri = Uri.parse(BASE + USER_GET)
                .buildUpon()
                .appendQueryParameter("id", String.valueOf(id))
                .build();
        return new URL(uri.toString());
    }

    public static URL getAllDistrictParentId(long districtParentId) throws MalformedURLException {
        Uri uri = Uri.parse(BASE + DISTRICT_GETALL)
                .buildUpon()
                .appendQueryParameter("districtParentId", String.valueOf(districtParentId))
                .build();
        return new URL(uri.toString());
    }
    public static URL getAllSubCategoryParentId(long subCategoryParentId) throws MalformedURLException {
        Uri uri = Uri.parse(BASE + SUBCATEGORY_GETALL)
                .buildUpon()
                .appendQueryParameter("subCategoryParentId", String.valueOf(subCategoryParentId))
                .build();
        return new URL(uri.toString());
    }
    public static URL getLogIn() throws MalformedURLException {
        Uri uri = Uri.parse(BASE + USER_GET_LOG_IN);
        return new URL(uri.toString());
    }

    public static URL setUser() throws MalformedURLException {
        Uri uri = Uri.parse(BASE + USER_SET);
        return new URL(uri.toString());
    }

    public static URL updateUser() throws MalformedURLException {
        Uri uri = Uri.parse(BASE + USER_UPDATE);
        return new URL(uri.toString());
    }

    public static URL getEmail(String email) throws MalformedURLException {
        Uri uri = Uri.parse(BASE + USER_EMAIL)
                .buildUpon()
                .appendQueryParameter("email", email)
                .build();
        return new URL(uri.toString());
    }

    public static URL getPhone(String phone) throws MalformedURLException {
        Uri uri = Uri.parse(BASE + USER_PHONE)
                .buildUpon()
                .appendQueryParameter("phone", phone)
                .build();
        return new URL(uri.toString());
    }


    //Thing
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

    public static URL addThing() throws MalformedURLException {
        Uri uri = Uri.parse(BASE + THING_ADD);
        return new URL(uri.toString());
    }

    public static URL addPhotoThing() throws MalformedURLException {
        Uri uri = Uri.parse(BASE + THING_ADD_PHOTO);
        return new URL(uri.toString());
    }


}
