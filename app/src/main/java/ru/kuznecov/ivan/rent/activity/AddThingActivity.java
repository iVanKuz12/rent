package ru.kuznecov.ivan.rent.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.jaredrummler.materialspinner.MaterialSpinner;


import java.util.ArrayList;
import java.util.List;
import ru.kuznecov.ivan.rent.R;
import ru.kuznecov.ivan.rent.model.Category;
import ru.kuznecov.ivan.rent.model.City;
import ru.kuznecov.ivan.rent.model.District;
import ru.kuznecov.ivan.rent.model.Thing;
import ru.kuznecov.ivan.rent.utils.MakePhoto;
import ru.kuznecov.ivan.rent.utils.NetworkRegister;


public class AddThingActivity extends BaseActivity {

    private List<City> listCity;
    private List<District> listDistrict;
    private List<Category> listCategory;

    private static final String TAG = "AddThingActivity";

    private MakePhoto makePhoto;
    private Uri photoUri;
    private Thing thing;

    private TextView texViewPost;
    private ImageView imagePhoto;
    private EditText edTexName;
    private MaterialSpinner spinnerCity;
    private MaterialSpinner spinnerDistrict;
    private MaterialSpinner spinnerCategorys;
    private MaterialSpinner spinnerSubCategorys;
    private EditText edTexPrice;
    private MaterialSpinner spinnerPrice;
    private EditText edTexDiscription;





    private void initUi(){
        texViewPost = findViewById(R.id.add_thing_post);
        imagePhoto = findViewById(R.id.add_thing_image);
        edTexName = findViewById(R.id.add_thing_name);
        spinnerCity = findViewById(R.id.spinner_city);
        spinnerDistrict = findViewById(R.id.spinner_district);
        spinnerDistrict.setEnabled(false);
        spinnerCategorys = findViewById(R.id.spinner_categorys);
        spinnerSubCategorys = findViewById(R.id.spinner_sub_categorys);
        spinnerSubCategorys.setEnabled(false);
        edTexPrice = findViewById(R.id.add_thing_price);
        spinnerPrice = findViewById(R.id.spinner_price);
        edTexDiscription = findViewById(R.id.add_thing_discription);
    }

    private void getThingUi(){
        thing.setName(getString(edTexName));
        thing.setPrice(getString(edTexPrice));
        thing.setDiscription(getString(edTexDiscription));
    }

    private String getString(EditText editText){
        return editText.getText().toString();
    }

    private void setDataCitySpinner(List<City> listCity) {
        this.listCity = listCity;
        List<String> stringListCity = new ArrayList<>();
        for (City city: listCity)
            stringListCity.add(city.getName());
        spinnerCity.setItems(stringListCity);

    }

    private void setDataCategorySpinner(List<Category> categories) {
        this.listCategory = categories;
        List<String> stringListCategory = new ArrayList<>();
        for (Category category: listCategory)
            stringListCategory.add(category.getName());
        spinnerCategorys.setItems(stringListCategory);
    }
    private void setDataDistrictSpinner(List<District> listDistrict) {
        this.listDistrict = listDistrict;
        try {
            List<String> stringListDistrict = new ArrayList<>();
            for (District district: listDistrict)
                stringListDistrict.add(district.getName());
            spinnerDistrict.setItems(stringListDistrict);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thing);

        initBottomNavigationView(0);
        Log.i("Search", "Hi!");

        initUi();

        thing = new Thing();
        makePhoto = new MakePhoto(this);
        photoUri = makePhoto.dispatchTakePictureIntent();

        NetworkRegister networkRegister = NetworkRegister.getInstance();
        networkRegister.queueMsgCity("loadCity");
        networkRegister.queueMsgCategory("loadCategory");
        networkRegister.setAddThingCityListener(new NetworkRegister.AddThingCityListener() {
            @Override
            public void loadCity(List<City> cities) {
                setDataCitySpinner(cities);
            }

            @Override
            public void loadCategory(List<Category> categories) {
                setDataCategorySpinner(categories);
            }

            @Override
            public void loadDistrict(List<District> districts) {
                spinnerDistrict.setEnabled(true);
                setDataDistrictSpinner(districts);
            }
        });



        spinnerCity.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                int idCity = 0;
                for (City city: listCity){
                    if (city.getName().equals(item))
                        idCity = city.getId();
                }
                networkRegister.queueMsgDistrict(Long.valueOf(idCity));
                Snackbar.make(view, "Clicked " + item + idCity, Snackbar.LENGTH_LONG).show();
            }
        });

        spinnerDistrict.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        setChekedItem(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == makePhoto.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Glide
                    .with(this)
                    .load(photoUri)
                    .into(imagePhoto);
        }
    }
}
