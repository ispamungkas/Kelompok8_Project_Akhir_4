package com.uknown.kelompok8_project_akhir_8.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.uknown.kelompok8_project_akhir_8.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {

    ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getApplicationContext(), HomepageActivity.class);        go.putExtra("profile", 1);
                go.putExtra("profile", 1);
                startActivity(go);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent go = new Intent(getApplicationContext(), HomepageActivity.class);
        go.putExtra("profile", 1);
        startActivity(go);
        finish();
    }
}