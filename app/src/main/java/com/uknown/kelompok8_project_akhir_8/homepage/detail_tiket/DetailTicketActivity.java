package com.uknown.kelompok8_project_akhir_8.homepage.detail_tiket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.uknown.kelompok8_project_akhir_8.Utils;
import com.uknown.kelompok8_project_akhir_8.databinding.ActivityDetailTicketBinding;
import com.uknown.kelompok8_project_akhir_8.homepage.HomepageActivity;
import com.uknown.kelompok8_project_akhir_8.homepage.model.TicketModel;

public class DetailTicketActivity extends AppCompatActivity {

    private ActivityDetailTicketBinding binding;
    private TicketModel model = new TicketModel();
    private float rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailTicketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getIntent() != null && getIntent().hasExtra("model") || getIntent().hasExtra("rate")){
            model = (TicketModel) getIntent().getSerializableExtra("model");
            rate = getIntent().getFloatExtra("rate", 0);
        }

        attachDataToLayout();
        System.out.println(model.getNumberSeat());
    }

    private void attachDataToLayout() {
        binding.date.setText(model.getDateToday());
        binding.bookId.setText(String.valueOf(model.getNumberBook()));
        binding.passangerName.setText(model.getUser().name);

        if(rate == 0){
            binding.ratingFloat.setText(String.valueOf(model.getRate()));
        } else {
            binding.ratingFloat.setText(String.valueOf(rate));
        }

        binding.seatBook.setText(String.valueOf(model.getTotalPassanger()));
        binding.phoneNumber.setText(model.getUser().getPhoneNumber());
        binding.busName.setText(model.getBusModel().getNamabus());
        binding.busNumber.setText(model.getBusModel().getKodebus());
        binding.currentLocation.setText(model.getDepartune().getDaerah());
        binding.currentTerminal.setText(model.getDepartune().getTerminal());
        binding.startDate.setText(model.getDateOrder());
        binding.timeStart.setText(model.getBusModel().getJamkeberangkatan());
        binding.destinationLocation.setText(model.getArrival().getDaerah());
        binding.destinationTerminal.setText(model.getArrival().getTerminal());

        StringBuilder seat = new StringBuilder();
        for (String i : model.getNumberSeat()){
            seat.append(i + ",");
        }

        binding.seatNumber.setText(seat);
        binding.totalPayment.setText(Utils.toRupiah(model.getAccumulatePrice()));
        binding.arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getApplicationContext(), HomepageActivity.class);
                startActivity(go);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent go = new Intent(getApplicationContext(), HomepageActivity.class);
        startActivity(go);
        finish();
    }
}