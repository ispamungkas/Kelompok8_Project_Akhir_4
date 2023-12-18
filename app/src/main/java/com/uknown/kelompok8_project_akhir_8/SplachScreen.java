package com.uknown.kelompok8_project_akhir_8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.uknown.kelompok8_project_akhir_8.register.LoginActivity;


public class SplachScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplachScreen.this, LoginActivity.class);
                startActivity(intent);

                //untuk menutup aktivitas splash screen
                finish();
            }
        }, 3000);
    }
}