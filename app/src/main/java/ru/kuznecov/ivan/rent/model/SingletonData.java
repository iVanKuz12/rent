package ru.kuznecov.ivan.rent.model;

import java.util.ArrayList;
import java.util.List;

import ru.kuznecov.ivan.rent.pojo.Category;
import ru.kuznecov.ivan.rent.pojo.City;
import ru.kuznecov.ivan.rent.pojo.District;
import ru.kuznecov.ivan.rent.pojo.SubCategory;

public class SingletonData {
    private static SingletonData sSingletonData;
    private List<City> cities = new ArrayList<>();
    private List<District> districts = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private List<SubCategory> subCategories = new ArrayList<>();

    public static SingletonData getInstance(){
        if (sSingletonData == null){
            sSingletonData = new SingletonData();
            return sSingletonData;
        } else
            return sSingletonData;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }
}
