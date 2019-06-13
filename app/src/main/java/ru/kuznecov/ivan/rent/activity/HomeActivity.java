package ru.kuznecov.ivan.rent.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import ru.kuznecov.ivan.rent.R;
import ru.kuznecov.ivan.rent.adapter.Adapter;
import ru.kuznecov.ivan.rent.pojo.Thing;
import ru.kuznecov.ivan.rent.pojo.User;
import ru.kuznecov.ivan.rent.service.Network;


public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActiv";
    private Network<String, User> network;
    private Handler mainHandler;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<Thing> things;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initBottomNavigationView(1);
        createBackGround();
        networkListener();
        initRecyclerView();
        adapterListener();




    }

    private void createBackGround() {
        if (mainHandler == null){
            network = Network.getInstance();
            mainHandler = new Handler();
            try {
                network.start();
            } catch (IllegalThreadStateException e) {
                e.printStackTrace();
            }
            network.setMainHandler(mainHandler);
            network.getLooper();
        }
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void adapterListener() {
        adapter.setListener(new Adapter.Listener() {
            @Override
            public void onClick(int position) {
                Thing thing = things.get(position);
                Toast.makeText(HomeActivity.this, thing.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void networkListener() {
        network.setHomeListener(new Network.HomeListener() {
            @Override
            public void getAllThing(List<Thing> list) {
                things = list;
                for (Thing thing: things){
                    Log.i(TAG, "looki" + thing);
                }
                adapter.setItems(things);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        network.queueMsgSingleton();
        network.queueMsgGetAllThing();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setChekedItem(1);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        network.clearQueue();
        network.quit();
    }

}
