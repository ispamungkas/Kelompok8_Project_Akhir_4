package com.uknown.kelompok8_project_akhir_8.register;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.uknown.kelompok8_project_akhir_8.databinding.FinisRegisterBinding;
import com.uknown.kelompok8_project_akhir_8.homepage.HomepageActivity;

public class FinisRegisterActivity extends AppCompatActivity {

    private FinisRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FinisRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        binding.btnTakeHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getApplicationContext(), HomepageActivity.class);
                startActivity(go);
                finish();
            }
        });

        binding.ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getApplicationContext(), SetupNumberActivity.class);
                startActivity(go);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent go = new Intent(getApplicationContext(), SetupNumberActivity.class);
        startActivity(go);
        finish();
    }
}