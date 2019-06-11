package ru.kuznecov.ivan.rent.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jaredrummler.materialspinner.MaterialSpinner;


import java.util.ArrayList;
import java.util.List;
import ru.kuznecov.ivan.rent.R;
import ru.kuznecov.ivan.rent.model.Category;
import ru.kuznecov.ivan.rent.model.City;
import ru.kuznecov.ivan.rent.model.District;
import ru.kuznecov.ivan.rent.model.SubCategory;
import ru.kuznecov.ivan.rent.model.Thing;
import ru.kuznecov.ivan.rent.model.User;
import ru.kuznecov.ivan.rent.service.DataBaseService;
import ru.kuznecov.ivan.rent.service.DataBaseServiceImpl;
import ru.kuznecov.ivan.rent.utils.FireBaseSaveAvatar;
import ru.kuznecov.ivan.rent.utils.MakePhoto;
import ru.kuznecov.ivan.rent.utils.NetworkRegister;


public class AddThingActivity extends BaseActivity {

    private static final String TAG = "AddThingActivity";

    private List<City> cityList;
    private List<District> districtList;
    private List<Category> categoryList;
    private List<SubCategory> subCategoryList;

    private String district;
    private String subCategory;

    private NetworkRegister networkRegister;
    private MakePhoto makePhoto;
    private Thing thing;
    private Uri photoUri;
    private DataBaseService dataBaseService;
    private User user;
    FireBaseSaveAvatar fireBaseSaveAvatar;

    private TextView texViewPost;
    private ImageView imagePhoto;
    private TextView changePhoto;
    private EditText edTexName;
    private MaterialSpinner spinnerCity;
    private MaterialSpinner spinnerDistrict;
    private MaterialSpinner spinnerCategorys;
    private MaterialSpinner spinnerSubCategorys;
    private EditText edTexPrice;
    private EditText edTexDiscription;
    private ProgressBar progressBar;



    private void initUi(){
        texViewPost = findViewById(R.id.add_thing_post);
        imagePhoto = findViewById(R.id.add_thing_image);
        changePhoto = findViewById(R.id.change_thing_photo);
        edTexName = findViewById(R.id.add_thing_name);
        spinnerCity = findViewById(R.id.spinner_city);
        spinnerDistrict = findViewById(R.id.spinner_district);
        spinnerDistrict.setEnabled(false);
        spinnerCategorys = findViewById(R.id.spinner_categorys);
        spinnerSubCategorys = findViewById(R.id.spinner_sub_categorys);
        spinnerSubCategorys.setEnabled(false);
        edTexPrice = findViewById(R.id.add_thing_price);
        edTexDiscription = findViewById(R.id.add_thing_discription);
        progressBar = findViewById(R.id.add_thing_progress_bar);


    }

    private Thing getThingUi(){
        thing.setUserId(user.getId());
        thing.setName(getString(edTexName));
        thing.setPrice(getString(edTexPrice));
        thing.setDiscription(getString(edTexDiscription));
        thing.setStatus(1);
        if (district != null){
            for (District dis: districtList){
                if (dis.getName().equals(district))
                    thing.setCityId((int) dis.getCityId());
            }
        } else {
            Toast.makeText(this, "" +
                    "Район не выбран", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (subCategory != null){
            for (SubCategory sub: subCategoryList){
                if (sub.getName().equals(subCategory)){
                    thing.setCategorId((int) sub.getId());
                }
            }
        } else {
            Toast.makeText(this, "Подкатегория не выбрана", Toast.LENGTH_SHORT).show();
            return null;
        }
        return thing;
    }

    private String getString(EditText editText){
        return editText.getText().toString();
    }

    private void setDataCitySpinner(List<City> listCity) {
        this.cityList = listCity;
        List<String> stringListCity = new ArrayList<>();
        for (City city: cityList)
            stringListCity.add(city.getName());
        spinnerCity.setItems(stringListCity);

    }
    private void setDataCategorySpinner(List<Category> categories) {
        this.categoryList = categories;
        List<String> stringListCategory = new ArrayList<>();
        for (Category category: categoryList)
            stringListCategory.add(category.getName());
        spinnerCategorys.setItems(stringListCategory);
    }
    private void setDataDistrictSpinner(List<District> listDistrict) {
        this.districtList = listDistrict;
        List<String> stringListDistrict = new ArrayList<>();
        for (District district: districtList)
            stringListDistrict.add(district.getName());
        spinnerDistrict.setItems(stringListDistrict);
    }
    private void setDataSubCategorySpinner(List<SubCategory> listSubCategory) {
        this.subCategoryList = listSubCategory;
        List<String> stringListSubCategory = new ArrayList<>();
        for (SubCategory subCategory: subCategoryList)
            stringListSubCategory.add(subCategory.getName());
        spinnerSubCategorys.setItems(stringListSubCategory);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thing);

        initBottomNavigationView(0);
        Log.i("Search", "Hi!");

        initUi();
        initClass();
        loadCityAndCategory();
        networkListener();
        spinnerListener();
        textViewListener();
        fireBaseListener();

    }

    private void textViewListener() {
        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPhoto();
            }
        });
        texViewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkRegister.queueMsgAddThing(getThingUi());
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void spinnerListener() {
        spinnerCity.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                long idCity = ++id;
                networkRegister.queueMsgDistrict(idCity);
            }
        });

        spinnerDistrict.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                district = (String) item;
            }
        });

        spinnerCategorys.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                subCategoryList = null;
                long idCategory = ++id;
                networkRegister.queueMsgSubCategory(idCategory);

            }
        });

        spinnerSubCategorys.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                subCategory = (String) item;
            }
        });
    }

    private void networkListener() {
        networkRegister.setAddThingCityListener(new NetworkRegister.AddThingListener() {
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

            @Override
            public void loadSubCategory(List<SubCategory> subCategories) {
                spinnerSubCategorys.setEnabled(true);
                setDataSubCategorySpinner(subCategories);
            }

            @Override
            public void getIdNewAddThing(long newId) {
                Log.i(TAG, "looki " + newId);
                thing.setId(newId);
                fireBaseSaveAvatar.saveAvatar(photoUri, "users/" + user.getId() + "/thing/"
                        + newId +"/photo");
            }

            @Override
            public void getResultFullAddThing(String res) {
                if (res.equals("OK"))
                    finish();
                else
                    Toast.makeText(AddThingActivity.this, "Ошибка, попробуйте снова"
                            , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCityAndCategory() {
        networkRegister.queueMsgCity("loadCity");
        networkRegister.queueMsgCategory("loadCategory");
    }

    private void createPhoto() {
        photoUri = makePhoto.dispatchTakePictureIntent();
    }

    private void initClass() {
        thing = new Thing();
        makePhoto = new MakePhoto(this);
        networkRegister = NetworkRegister.getInstance();
        dataBaseService = new DataBaseServiceImpl(this);
        user = dataBaseService.readUser();
        fireBaseSaveAvatar = new FireBaseSaveAvatar();

    }

    private void fireBaseListener() {
        fireBaseSaveAvatar.setUriPhotoListener(new FireBaseSaveAvatar.UriPhotoListener() {
            @Override
            public void getStringPhoto(String photo) {
                thing.setPhoto(photo);
                networkRegister.queueMsgAddThing(thing);
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
