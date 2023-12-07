package com.uknown.kelompok8_project_akhir_4.homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.uknown.kelompok8_project_akhir_8.R;
import com.uknown.kelompok8_project_akhir_8.databinding.ActivityHomepageBinding;

import java.util.Objects;

public class HomepageActivity extends AppCompatActivity {

    private ActivityHomepageBinding binding;
    private Bundle departune, arrived;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        departune = getIntent().getBundleExtra("sendbackDepartune");
        arrived = getIntent().getBundleExtra("sendbackArrival");

        // Start Fragment
        transactionFragment(new SearchFragment(), departune, arrived);

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return onItemNavigationClicked(item);
            }
        });
    }


    private boolean onItemNavigationClicked(MenuItem item){

        if(item.getItemId() == R.id.search){
            transactionFragment(new SearchFragment(), null, null);
        } else if(item.getItemId() == R.id.history){
            transactionFragment(new HistoryFragment(), null, null);
        } else if (item.getItemId() == R.id.notification){
            transactionFragment(new NotificationFragment(), null, null);
        } else {
            transactionFragment(new ProfileFragment(), null, null);
        }

        return true;
    }

    private void transactionFragment(Fragment fragment, Bundle departune, Bundle arrived) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(departune != null || arrived != null){
            Bundle bundle = new Bundle();
            bundle.putBundle("1", departune);
            bundle.putBundle("2", arrived);
            fragment.setArguments(bundle);
        }
        ft.replace(binding.frameLayout.getId(), fragment);
        ft.commit();
    }
}