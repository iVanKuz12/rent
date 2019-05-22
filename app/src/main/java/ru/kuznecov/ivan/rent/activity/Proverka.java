package ru.kuznecov.ivan.rent.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import ru.kuznecov.ivan.rent.R;
import ru.kuznecov.ivan.rent.adapter.Adapter;


public class Proverka extends AppCompatActivity {

    private List<String> mList;
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        mList = new ArrayList<>();
        for (int i = 1; i <= 100; i++)
            mList.add( i + "Hi");

        mRecyclerView = (RecyclerView)findViewById(R.id.id_recycler_view);
        mAdapter = new Adapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        listenerClickAdapter();
    }

    private void listenerClickAdapter() {
        mAdapter.setListener(new Adapter.Listener() {
            @Override
            public void onClick(int position) {
                showToast(position);
            }
        });
    }
    public void showToast(int position){
        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_LONG).show();
    }
}
