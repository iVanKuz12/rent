package ru.kuznecov.ivan.rent.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.kuznecov.ivan.rent.R;
import ru.kuznecov.ivan.rent.model.SingletonData;
import ru.kuznecov.ivan.rent.pojo.City;
import ru.kuznecov.ivan.rent.pojo.District;
import ru.kuznecov.ivan.rent.pojo.SubCategory;
import ru.kuznecov.ivan.rent.pojo.Thing;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Listener mListener;
    private Context mContext;
    private SingletonData singletonData;
    private List<City> cities;
    private List<District> districts;
    private List<SubCategory> subCategories;

    private List<Thing> mList = new ArrayList<>();

    public interface Listener{
        void onClick(int position);
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public void setItems(List<Thing> list){
        clearItems();
        setList();
        mList.addAll(list);
        notifyDataSetChanged();
    }
    public void clearItems(){
        mList.clear();
        notifyDataSetChanged();
    }

    public Adapter(Activity activity) {
        mContext = activity;
        singletonData = SingletonData.getInstance();

    }

    public void setList() {
        cities = singletonData.getCities();
        districts = singletonData.getDistricts();
        subCategories = singletonData.getSubCategories();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Thing thing = mList.get(i);
        Glide
                .with(mContext)
                .load(thing.getPhoto())
                .into(viewHolder.imageView);

        viewHolder.textViewName.setText(thing.getName());
        viewHolder.textViewPrice.setText(thing.getPrice());
        long parentIdDistrict = 0;
        int idDistrict = thing.getCityId();
        int idSubCategory = thing.getCategorId();
        for (District district: districts){
            if (district.getId() == idDistrict){
                parentIdDistrict = district.getCityId();
                viewHolder.textViewDistrict.setText(district.getName());
            }
        }
        for (City city: cities){
            if (city.getId() == parentIdDistrict){
                viewHolder.textViewCity.setText(city.getName());
            }
        }
        for (SubCategory subCategory: subCategories){
            if (subCategory.getId() == idSubCategory){
                viewHolder.textViewSubCategory.setText(subCategory.getName());
            }
        }

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private ImageView imageView;
        private TextView textViewName;
        private TextView textViewPrice;
        private TextView textViewCity;
        private TextView textViewDistrict;
        private TextView textViewSubCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imageView = itemView.findViewById(R.id.card_image);
            textViewName = itemView.findViewById(R.id.card_name);
            textViewPrice = itemView.findViewById(R.id.card_price);
            textViewCity = itemView.findViewById(R.id.card_city);
            textViewDistrict = itemView.findViewById(R.id.card_district);
            textViewSubCategory = itemView.findViewById(R.id.card_sub_category);
        }

    }
}
