package com.uknown.kelompok8_project_akhir_8.homepage.detail_tiket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.uknown.kelompok8_project_akhir_8.Utils;
import com.uknown.kelompok8_project_akhir_8.databinding.ActivityDetailTicketBeforePaymentBinding;
import com.uknown.kelompok8_project_akhir_8.databinding.ActivityDetailTicketBinding;
import com.uknown.kelompok8_project_akhir_8.homepage.HomepageActivity;
import com.uknown.kelompok8_project_akhir_8.homepage.model.BusModel;
import com.uknown.kelompok8_project_akhir_8.homepage.model.NearbyLocationModel;
import com.uknown.kelompok8_project_akhir_8.homepage.model.TicketModel;
import com.uknown.kelompok8_project_akhir_8.homepage.model.User;
import com.uknown.kelompok8_project_akhir_8.homepage.orderpage.DetailOrderActivity;
import com.uknown.kelompok8_project_akhir_8.homepage.orderpage.payment.ChoosePaymentActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DetailTicketBeforePaymentActivity extends AppCompatActivity {

    private ActivityDetailTicketBeforePaymentBinding binding;
    private NearbyLocationModel departune, arrival;
    private BusModel busModel;
    private String date;
    private ArrayList<String> dataselected = new ArrayList<>();
    private int passanger;
    private String busId;
    private int priceResult = 0;
    private FirebaseAuth mAuth;
    private User user = new User();
    private FirebaseFirestore ff;
    private int code = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailTicketBeforePaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        ff = FirebaseFirestore.getInstance();

        if(checkintent()){
            busModel = (BusModel) getIntent().getSerializableExtra("busdata");
            departune = (NearbyLocationModel) getIntent().getSerializableExtra("departune");
            arrival = (NearbyLocationModel) getIntent().getSerializableExtra("arrival");
            date = getIntent().getStringExtra("date");
            passanger = getIntent().getIntExtra("ppl", 1);
            dataselected = getIntent().getStringArrayListExtra("data");
            priceResult = getIntent().getIntExtra("priceresult", 0);
            busId = getIntent().getStringExtra("busid");
        }

        attachDataToLayout();

        binding.btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getApplicationContext(), ChoosePaymentActivity.class);
                startActivity(go);
                go.putStringArrayListExtra("data", dataselected);
                go.putExtra("priceresult", priceResult);
                go.putExtra("busdata", busModel);
                go.putExtra("departune", departune);
                go.putExtra("arrival", arrival);
                go.putExtra("date", date);
                go.putExtra("ppl", passanger);
                go.putExtra("busid", busId);
                go.putExtra("uniquecode", code);
                startActivity(go);
                finish();
            }
        });
    }

    private boolean checkintent() {
        if(getIntent() != null &&
                getIntent().hasExtra("data") &&
                getIntent().hasExtra("priceresult") &&
                getIntent().hasExtra("busdata") &&
                getIntent().hasExtra("departune") &&
                getIntent().hasExtra("arrival") &&
                getIntent().hasExtra("date") &&
                getIntent().hasExtra("ppl") &&
                getIntent().hasExtra("busid")
        ){
            return true;
        } else {
            return false;
        }
    }

    private void attachDataToLayout() {
        getDataUser();

        binding.passangerName.setText(mAuth.getCurrentUser().getDisplayName());
        binding.seatBook.setText(String.valueOf(passanger));
        binding.busName.setText(busModel.getNamabus());
        binding.currentLocation.setText(departune.getDaerah());
        binding.currentTerminal.setText(departune.getTerminal());
        binding.startDate.setText(busModel.getTanggalkeberangkatan());
        binding.timeStart.setText(busModel.getJamkeberangkatan());
        binding.destinationLocation.setText(arrival.getDaerah());
        binding.destinationTerminal.setText(arrival.getTerminal());

        StringBuilder seat = new StringBuilder();
        for (String i : dataselected){
            seat.append(i + ",");
        }


        binding.seatNumber.setText(seat);
        binding.totalPayment.setText(Utils.toRupiah(priceResult));
        binding.totalpassanger.setText(String.valueOf(passanger));

        Random rand = new Random();
        code = rand.nextInt(100000);

        binding.code.setText(String.valueOf(code));
        binding.pricebus.setText(String.valueOf(busModel.getHargaseat()));
        binding.arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getApplicationContext(), DetailOrderActivity.class);
                go.putStringArrayListExtra("data", dataselected);
                go.putExtra("priceresult", priceResult);
                go.putExtra("busdata", busModel);
                go.putExtra("departune", departune);
                go.putExtra("arrival", arrival);
                go.putExtra("date", date);
                go.putExtra("ppl", passanger);
                go.putExtra("busid", busId);
                startActivity(go);
                startActivity(go);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent go = new Intent(this, DetailOrderActivity.class);
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

    private void getDataUser(){
        ff.collection("users").whereEqualTo("userId", mAuth.getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> data = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot ds : data){
                            user = ds.toObject(User.class);
                            binding.phoneNumber.setText(user.getPhoneNumber());
                        }
                    }
                });

    }
}