package com.uknown.kelompok8_project_akhir_4.homepage.orderpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.uknown.kelompok8_project_akhir_4.homepage.model.BusModel;
import com.uknown.kelompok8_project_akhir_4.homepage.model.NearbyLocationModel;
import com.uknown.kelompok8_project_akhir_8.R;
import com.uknown.kelompok8_project_akhir_8.databinding.ActivityDetailOrderBinding;

import java.util.ArrayList;

public class DetailOrderActivity extends AppCompatActivity {

    private ActivityDetailOrderBinding binding;
    private NearbyLocationModel departune, arrival;
    private BusModel busModel;
    private String date;
    private int passanger;
    private int seatSelected = 0;
    private ArrayList<String> dataselected = new ArrayList<>();
    private ArrayList<String> dataSelectedSeatBefore = new ArrayList<>();
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setInitialComponent();
    }

    private void setInitialComponent() {

        if(checkIntent()){

            if (getIntent() != null && getIntent().hasExtra("dataselected")){
                dataSelectedSeatBefore = getIntent().getStringArrayListExtra("dataselected");
                System.out.println(dataSelectedSeatBefore.size());
                System.out.println(dataSelectedSeatBefore);
                seatSelected += dataSelectedSeatBefore.size();
            }

            Glide.with(getApplicationContext())
                    .load(busModel.getGambarbus())
                    .placeholder(R.drawable.loading)
                    .into(binding.imageBus);

            binding.busName.setText(busModel.getNamabus());
            binding.dateBus.setText(date);
            binding.numberBus.setText(busModel.getKodebus());
            binding.tvDarimana.setText(departune.getDaerah());
            binding.tvJamDarimana.setText(busModel.getJamkeberangkatan());
            binding.tvKemana.setText(arrival.getDaerah());
            binding.tvTerminal.setText(busModel.getTerminal());
            binding.tvTerminal1.setText(busModel.getTerminaltujuan());
            binding.seatSelected.setText(String.valueOf(seatSelected));
            binding.muchSeatPassanger.setText(String.valueOf(passanger));

            if(busModel.getKodebus().equals("SAG0091")){
                binding.classBus.setText("Executive");
            } else if (busModel.getKodebus().equals("PGI0091")){
                binding.classBus.setText("VIP");
            }

            binding.muchPassanger.setText(String.valueOf(passanger));
            binding.pricePerSeat.setText(String.valueOf(busModel.getHargaseat()));

            int priceResult = busModel.getHargaseat() * passanger;
            binding.priceResult.setText(String.valueOf(priceResult));

            binding.btnSeat.setOnClickListener((item) -> {
                Intent go = new Intent(getApplicationContext(), ChooseSeatActivity.class);
                if(dataselected.get(0).equals("0")){
                    dataselected.remove(0);
                }
                go.putStringArrayListExtra("dataSelectedSeat", dataSelectedSeatBefore);
                go.putStringArrayListExtra("data", dataselected);
                go.putExtra("busdata", busModel);
                go.putExtra("departune", departune);
                go.putExtra("arrival", arrival);
                go.putExtra("date", date);
                go.putExtra("ppl", passanger);
                go.putExtra("busid", id);
                startActivity(go);
                finish();
            });

            binding.arrowBack.setOnClickListener((item) -> {
                Intent back = new Intent(getApplicationContext(), ChooseBusActivity.class);
                back.putExtra("departune", departune);
                back.putExtra("arrival", arrival);
                back.putExtra("ppl", passanger);
                back.putExtra("date", date);
                startActivity(back);
                finish();
            });
        }
    }

    private boolean checkIntent() {
        if(getIntent() != null &&
            getIntent().getExtras() != null &&
            getIntent().hasExtra("busdata") &&
            getIntent().hasExtra("departune") &&
            getIntent().hasExtra("arrival") &&
            getIntent().hasExtra("date") &&
            getIntent().hasExtra("ppl")
        ) {
            busModel = (BusModel) getIntent().getSerializableExtra("busdata");
            departune = (NearbyLocationModel) getIntent().getSerializableExtra("departune");
            arrival = (NearbyLocationModel) getIntent().getSerializableExtra("arrival");
            date = getIntent().getStringExtra("date");
            passanger = getIntent().getIntExtra("ppl", 1);
            dataselected = busModel.getBookedseat();
            id = getIntent().getStringExtra("busid");
            return true;
        } else {
            return false;
        }
    }

}