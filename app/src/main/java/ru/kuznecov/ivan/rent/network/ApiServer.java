package ru.kuznecov.ivan.rent.network;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class ApiServer {

    //Base
    private static final String BASE = "http://192.168.0.3:8080";

    //User
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
    private static final String DISTRICT_GET_ALL = "/district/getAll";
    private static final String DISTRICT_GET_PARENT_ID = "/district/getAllParentId";

    //Category
    private static final String CATEGORY_GETALL = "/category/getAll";

    //SubCategory
    private static final String SUBCATEGORY_GET_ALL = "/subCategory/getAll";
    private static final String SUBCATEGORY_GET_PARENT_ID = "/subCategory/getAllParentId";

    //Things
    private static final String THING_GET_ALL= "/thing/getAll";
    private static final String THING_GET_ONE= "/thing/getOne";
    private static final String THING_ADD = "/thing/addThing";
    private static final String THING_ADD_PHOTO = "/thing/addPhotoThing";
    private static final String THING_GET_ALL_USER_ID = "/thing/getAllThingUserId";



    //User
    public static URL getAllUser() throws MalformedURLException {
        Uri uri = Uri.parse(BASE + USER_GET_ALL);
        return new URL(uri.toString()) ;
    }

    public static URL getUser(long id) throws MalformedURLException {
        Uri uri = Uri.parse(BASE + USER_GET)
                .buildUpon()
                .appendQueryParameter("id", String.valueOf(id))
                .build();
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

    public static URL getAllThingUserId(long id) throws MalformedURLException {
        Uri uri = Uri.parse(BASE + THING_GET_ALL_USER_ID)
                .buildUpon()
                .appendQueryParameter("id", String.valueOf(id))
                .build();
        return new URL(uri.toString());
    }

    //Category

    public static URL getAllCateory() throws MalformedURLException {
        Uri uri = Uri.parse(BASE + CATEGORY_GETALL);
        return new URL(uri.toString()) ;
    }

    //SubCategory

    public static URL getAllSubCategoryParentId(long subCategoryParentId) throws MalformedURLException {
        Uri uri = Uri.parse(BASE + SUBCATEGORY_GET_PARENT_ID)
                .buildUpon()
                .appendQueryParameter("subCategoryParentId", String.valueOf(subCategoryParentId))
                .build();
        return new URL(uri.toString());
    }

    public static URL getAllSubCategory() throws MalformedURLException {
        Uri uri = Uri.parse(BASE + SUBCATEGORY_GET_ALL);
        return new URL(uri.toString()) ;
    }

    //City
    public static URL getAllCity() throws MalformedURLException {
        Uri uri = Uri.parse(BASE + CITY_GETALL);
        return new URL(uri.toString()) ;
    }

    //District

    public static URL getAllDistrictParentId(long districtParentId) throws MalformedURLException {
        Uri uri = Uri.parse(BASE + DISTRICT_GET_PARENT_ID)
                .buildUpon()
                .appendQueryParameter("districtParentId", String.valueOf(districtParentId))
                .build();
        return new URL(uri.toString());
    }

    public static URL getAllDistrict() throws MalformedURLException {
        Uri uri = Uri.parse(BASE + DISTRICT_GET_ALL);
        return new URL(uri.toString()) ;
    }

    //LogIn
    public static URL getLogIn() throws MalformedURLException {
        Uri uri = Uri.parse(BASE + USER_GET_LOG_IN);
        return new URL(uri.toString());
    }

    //Registration
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

}
