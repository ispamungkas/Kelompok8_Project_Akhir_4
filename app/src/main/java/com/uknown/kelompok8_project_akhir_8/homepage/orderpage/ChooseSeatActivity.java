package com.uknown.kelompok8_project_akhir_8.homepage.orderpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.uknown.kelompok8_project_akhir_8.databinding.ActivityChoiceSeatBinding;
import com.uknown.kelompok8_project_akhir_8.homepage.detail_tiket.DetailTicketBeforePaymentActivity;
import com.uknown.kelompok8_project_akhir_8.homepage.model.BusModel;
import com.uknown.kelompok8_project_akhir_8.homepage.model.NearbyLocationModel;
import com.uknown.kelompok8_project_akhir_8.R;

import java.util.ArrayList;

public class ChooseSeatActivity extends AppCompatActivity {

    private ActivityChoiceSeatBinding binding;
    private NearbyLocationModel departune, arrival;
    private BusModel busModel;
    private String date;
    private int passanger;
    private String id;
    private ArrayList<String> dataBooked = new ArrayList<>();
    private ArrayList<String> dataSelected = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChoiceSeatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if(checkIntent()){
            if(getIntent() != null && getIntent().hasExtra("data") && getIntent().hasExtra("dataSelectedSeat")){
                dataBooked = getIntent().getStringArrayListExtra("data");
                dataSelected = getIntent().getStringArrayListExtra("dataSelectedSeat");
                dataSelected.clear();
                setDataBookedToComponent(dataBooked);
                setDataSeleectedToComponent(dataSelected);
            }
        }

        binding.arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getApplicationContext(), DetailOrderActivity.class);
                go.putExtra("busdata", busModel);
                go.putExtra("departune", departune);
                go.putExtra("arrival", arrival);
                go.putExtra("date", date);
                go.putExtra("ppl", passanger);
                go.putExtra("busid", id);
                startActivity(go);
                finish();
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getApplicationContext(), DetailOrderActivity.class);
                go.putStringArrayListExtra("dataselected", dataSelected);
                go.putExtra("busdata", busModel);
                go.putExtra("departune", departune);
                go.putExtra("arrival", arrival);
                go.putExtra("date", date);
                go.putExtra("ppl", passanger);
                go.putExtra("busid", id);
                startActivity(go);
                finish();
            }
        });

    }

    public void setSeat(View view) {
        AppCompatButton viewIndex = view.findViewById(view.getId());

        if(dataSelected.size() == passanger && !checkHasData(viewIndex.getText().toString())){
            Toast.makeText(this, "Exceeding the number of passengers", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!checkHasData(viewIndex.getText().toString()) || checkHasData(viewIndex.getText().toString())) {
            if (!checkHasData(viewIndex.getText().toString())) {
                view.setBackground(getDrawable(R.drawable.background_button_primarycolor));
                dataSelected.add(viewIndex.getText().toString());
            } else {
                view.setBackground(getDrawable(R.drawable.background_button_backgroundcolor));
                dataSelected.remove(viewIndex.getText().toString());
            }
        }
    }

    private boolean checkHasData(String dataSeat) {
        for (String i : dataSelected){
            if(dataSeat.equals(i)){
                return true;
            }
        }
        return false;
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
            id = getIntent().getStringExtra("busid");
            return true;
        } else {
            return false;
        }
    }

    private void setDataBookedToComponent(ArrayList<String> dataBooked) {
        for(String data : dataBooked) {
            if (binding.seat1.getText().toString().equals(data)){
                binding.seat1.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat1.setClickable(false);
            } else if (binding.seat2.getText().toString().equals(data)){
                binding.seat2.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat2.setClickable(false);
            } else if (binding.seat3.getText().toString().equals(data)){
                binding.seat3.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat3.setClickable(false);
            } else if (binding.seat4.getText().toString().equals(data)){
                binding.seat4.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat4.setClickable(false);
            } else if (binding.seat5.getText().toString().equals(data)){
                binding.seat5.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat5.setClickable(false);
            } else if (binding.seat6.getText().toString().equals(data)){
                binding.seat6.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat6.setClickable(false);
            } else if (binding.seat7.getText().toString().equals(data)){
                binding.seat7.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat7.setClickable(false);
            } else if (binding.seat8.getText().toString().equals(data)){
                binding.seat8.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat8.setClickable(false);
            } else if (binding.seat9.getText().toString().equals(data)){
                binding.seat9.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat9.setClickable(false);
            } else if (binding.seat10.getText().toString().equals(data)){
                binding.seat10.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat10.setClickable(false);
            } else if (binding.seat11.getText().toString().equals(data)){
                binding.seat11.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat11.setClickable(false);
            } else if (binding.seat12.getText().toString().equals(data)){
                binding.seat12.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat12.setClickable(false);
            } else if (binding.seat13.getText().toString().equals(data)){
                binding.seat13.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat13.setClickable(false);
            } else if (binding.seat14.getText().toString().equals(data)){
                binding.seat14.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat14.setClickable(false);
            } else if (binding.seat15.getText().toString().equals(data)){
                binding.seat15.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat15.setClickable(false);
            } else if (binding.seat16.getText().toString().equals(data)){
                binding.seat16.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat16.setClickable(false);
            } else if (binding.seat17.getText().toString().equals(data)){
                binding.seat17.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat17.setClickable(false);
            } else if (binding.seat18.getText().toString().equals(data)){
                binding.seat18.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat18.setClickable(false);
            } else if (binding.seat19.getText().toString().equals(data)){
                binding.seat19.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat19.setClickable(false);
            } else if (binding.seat20.getText().toString().equals(data)){
                binding.seat20.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat20.setClickable(false);
            } else if (binding.seat21.getText().toString().equals(data)){
                binding.seat21.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat21.setClickable(false);
            } else if (binding.seat22.getText().toString().equals(data)){
                binding.seat22.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat22.setClickable(false);
            } else if (binding.seat23.getText().toString().equals(data)){
                binding.seat23.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat23.setClickable(false);
            } else if (binding.seat24.getText().toString().equals(data)){
                binding.seat24.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat24.setClickable(false);
            } else if (binding.seat25.getText().toString().equals(data)){
                binding.seat25.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat25.setClickable(false);
            } else if (binding.seat26.getText().toString().equals(data)){
                binding.seat26.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat26.setClickable(false);
            } else if (binding.seat27.getText().toString().equals(data)){
                binding.seat27.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat27.setClickable(false);
            } else if (binding.seat28.getText().toString().equals(data)){
                binding.seat28.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat28.setClickable(false);
            } else if (binding.seat29.getText().toString().equals(data)){
                binding.seat29.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat29.setClickable(false);
            } else if (binding.seat30.getText().toString().equals(data)){
                binding.seat30.setBackground(getDrawable(R.drawable.background_button_grey_seat));
                binding.seat30.setClickable(false);
            }
        }
    }

   private void setDataSeleectedToComponent(ArrayList<String> dataBooked) {
        for(String data : dataBooked) {
            if (binding.seat1.getText().toString().equals(data)){
                binding.seat1.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat2.getText().toString().equals(data)){
                binding.seat2.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat3.getText().toString().equals(data)){
                binding.seat3.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat4.getText().toString().equals(data)){
                binding.seat4.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat5.getText().toString().equals(data)){
                binding.seat5.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat6.getText().toString().equals(data)){
                binding.seat6.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat7.getText().toString().equals(data)){
                binding.seat7.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat8.getText().toString().equals(data)){
                binding.seat8.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat9.getText().toString().equals(data)){
                binding.seat9.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat10.getText().toString().equals(data)){
                binding.seat10.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat11.getText().toString().equals(data)){
                binding.seat11.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat12.getText().toString().equals(data)){
                binding.seat12.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat13.getText().toString().equals(data)){
                binding.seat13.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat14.getText().toString().equals(data)){
                binding.seat14.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat15.getText().toString().equals(data)){
                binding.seat15.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat16.getText().toString().equals(data)){
                binding.seat16.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat17.getText().toString().equals(data)){
                binding.seat17.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat18.getText().toString().equals(data)){
                binding.seat18.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat19.getText().toString().equals(data)){
                binding.seat19.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat20.getText().toString().equals(data)){
                binding.seat20.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat21.getText().toString().equals(data)){
                binding.seat21.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat22.getText().toString().equals(data)){
                binding.seat22.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat23.getText().toString().equals(data)){
                binding.seat23.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat24.getText().toString().equals(data)){
                binding.seat24.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat25.getText().toString().equals(data)){
                binding.seat25.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat26.getText().toString().equals(data)){
                binding.seat26.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat27.getText().toString().equals(data)){
                binding.seat27.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat28.getText().toString().equals(data)){
                binding.seat28.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat29.getText().toString().equals(data)){
                binding.seat29.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            } else if (binding.seat30.getText().toString().equals(data)){
                binding.seat30.setBackground(getDrawable(R.drawable.background_button_primarycolor));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent go = new Intent(getApplicationContext(), DetailOrderActivity.class);
        go.putExtra("busdata", busModel);
        go.putExtra("departune", departune);
        go.putExtra("arrival", arrival);
        go.putExtra("date", date);
        go.putExtra("ppl", passanger);
        go.putExtra("busid", id);
        startActivity(go);
        finish();
    }
}