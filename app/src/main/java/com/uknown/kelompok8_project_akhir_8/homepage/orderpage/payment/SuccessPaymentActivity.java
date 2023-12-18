package com.uknown.kelompok8_project_akhir_8.homepage.orderpage.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.uknown.kelompok8_project_akhir_8.databinding.ActivitySuccessPaymentBinding;
import com.uknown.kelompok8_project_akhir_8.homepage.HomepageActivity;

public class SuccessPaymentActivity extends AppCompatActivity {

    private ActivitySuccessPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuccessPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int id = getIntent().getIntExtra("id", 0);
        binding.transactionId.setText(String.valueOf(id));

        SharedPreferences mSharePreference = getApplicationContext().getSharedPreferences("departune", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = mSharePreference.edit();
        ed.remove("slDepartuneDaerah");
        ed.remove("slDepartuneTerminal");
        ed.remove("slDepartuneKm");
        ed.remove("slArrivalDaerah");
        ed.remove("slArrivalTerminal");
        ed.remove("slArrivalKm");
        ed.apply();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.progress.setVisibility(View.GONE);
                binding.layoutSuccess.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent go = new Intent(getApplicationContext(), HomepageActivity.class);
                        go.putExtra("success", 1);
                        startActivity(go);
                        finishAffinity();
                    }
                }, 3000);
            }
        }, 3000);
    }
}