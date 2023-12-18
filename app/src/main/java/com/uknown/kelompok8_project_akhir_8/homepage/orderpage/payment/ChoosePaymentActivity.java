package com.uknown.kelompok8_project_akhir_8.homepage.orderpage.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.uknown.kelompok8_project_akhir_8.Utils;
import com.uknown.kelompok8_project_akhir_8.databinding.ActivityChoosePaymentBinding;
import com.uknown.kelompok8_project_akhir_8.homepage.detail_tiket.DetailTicketBeforePaymentActivity;
import com.uknown.kelompok8_project_akhir_8.homepage.model.BusModel;
import com.uknown.kelompok8_project_akhir_8.homepage.model.NearbyLocationModel;
import com.uknown.kelompok8_project_akhir_8.homepage.orderpage.DetailOrderActivity;
import com.uknown.kelompok8_project_akhir_8.R;

import java.util.ArrayList;

public class ChoosePaymentActivity extends AppCompatActivity {

    private ActivityChoosePaymentBinding binding;
    private NearbyLocationModel departune, arrival;
    private BusModel busModel;
    private String date;
    private ArrayList<String> dataselected = new ArrayList<>();
    private int passanger;
    private String busId;
    private int priceResult = 0;
    private int code = 0;
    private PassData passData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChoosePaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(checkintent()){
            busModel = (BusModel) getIntent().getSerializableExtra("busdata");
            departune = (NearbyLocationModel) getIntent().getSerializableExtra("departune");
            arrival = (NearbyLocationModel) getIntent().getSerializableExtra("arrival");
            date = getIntent().getStringExtra("date");
            passanger = getIntent().getIntExtra("ppl", 1);
            dataselected = getIntent().getStringArrayListExtra("data");
            priceResult = getIntent().getIntExtra("priceresult", 0);
            code = getIntent().getIntExtra("uniquecode", 0);
            busId = getIntent().getStringExtra("busid");
        }

        binding.totalHarga.setText(Utils.toRupiah(priceResult));
        binding.arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getApplicationContext(), DetailTicketBeforePaymentActivity.class);
                go.putStringArrayListExtra("data", dataselected);
                go.putExtra("priceresult", priceResult);
                go.putExtra("busdata", busModel);
                go.putExtra("departune", departune);
                go.putExtra("arrival", arrival);
                go.putExtra("date", date);
                go.putExtra("ppl", passanger);
                go.putExtra("busid", busId);
                startActivity(go);
                finish();
            }
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutAc, new ChoosePaymentFragment())
                .addToBackStack("main").commit();

    }


    public Bundle passingDataToAnyFragment() {
        Bundle dataBundle = new Bundle();
        dataBundle.putSerializable("departune", departune);
        dataBundle.putSerializable("arrival", arrival);
        dataBundle.putInt("priceresult", priceResult);
        dataBundle.putSerializable("busdata", busModel);
        dataBundle.putString("busid", busId);
        dataBundle.putStringArrayList("data", dataselected);
        dataBundle.putString("date", date);
        dataBundle.putInt("ppl", passanger);
        dataBundle.putInt("accumulatePrice", priceResult);
        dataBundle.putInt("code", code);

        return dataBundle;
    }

    public boolean checkintent(){
        if(getIntent() != null &&
                getIntent().hasExtra("data") &&
                getIntent().hasExtra("priceresult") &&
                getIntent().hasExtra("busdata") &&
                getIntent().hasExtra("departune") &&
                getIntent().hasExtra("arrival") &&
                getIntent().hasExtra("date") &&
                getIntent().hasExtra("uniquecode") &&
                getIntent().hasExtra("ppl") &&
                getIntent().hasExtra("busid")
        ){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
            Intent go = new Intent(this, DetailTicketBeforePaymentActivity.class);
            go.putStringArrayListExtra("data", dataselected);
            go.putExtra("priceresult", priceResult);
            go.putExtra("busdata", busModel);
            go.putExtra("departune", departune);
            go.putExtra("arrival", arrival);
            go.putExtra("date", date);
            go.putExtra("ppl", passanger);
            go.putExtra("busid", busId);
            startActivity(go);
            this.finish();
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }
}