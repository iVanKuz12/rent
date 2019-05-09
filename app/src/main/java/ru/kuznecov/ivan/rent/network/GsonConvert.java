package ru.kuznecov.ivan.rent.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;



public class GsonConvert<T> {

    private T t;
    private Gson gson = new GsonBuilder().create();

    public <T> List<T> getList(String jsonArray, Class<T> clazz) {
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.fromJson(jsonArray, typeOfT);
    }

    public <T> T deserialization(String json, Class<T> clazz){
        return gson.fromJson(json, clazz);
    }
    public String serialization(T t){
        return gson.toJson(t);
    }

}
