package com.xasanboyevdiyorbek.bmi_tatu;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.xasanboyevdiyorbek.bmi_tatu.Untity.NetworkchangeListener;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {


    NetworkchangeListener networkchangeListener = new NetworkchangeListener();

    BottomNavigationView bottomNavigationView;
    Home_fragment home_fragment = new Home_fragment();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomnavbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.container1, home_fragment).commit();


        bottomNavigationView.setOnItemSelectedListener(this);

    }

    @Override
    protected void onStart() {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkchangeListener, intentFilter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkchangeListener);
        super.onStop();
    }

    private void displayfragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().replace(R.id.container1, fragment).commit();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {

            case R.id.home_item:
                fragment = new Home_fragment();
                break;
            case R.id.buyurtma_item:
                fragment = new Orders_fragment();
                break;
            case R.id.info_item:
                fragment = new Info_fragment();
                break;

            default:
                fragment = new Home_fragment();
                break;

        }
        displayfragment(fragment);
        return true;
    }

}