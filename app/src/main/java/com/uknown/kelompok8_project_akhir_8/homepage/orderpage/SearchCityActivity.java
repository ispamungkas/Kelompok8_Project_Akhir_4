package com.uknown.kelompok8_project_akhir_8.homepage.orderpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uknown.kelompok8_project_akhir_8.databinding.ActivitySearchCityBinding;
import com.uknown.kelompok8_project_akhir_8.homepage.HomepageActivity;
import com.uknown.kelompok8_project_akhir_8.homepage.adapter.NearbyLocationAdapter;
import com.uknown.kelompok8_project_akhir_8.homepage.model.NearbyLocationModel;

import java.util.ArrayList;
import java.util.List;

public class SearchCityActivity extends AppCompatActivity implements NearbyLocationAdapter.listener{

    private NearbyLocationAdapter nearbyLocationAdapter;
    private List<NearbyLocationModel> nearbyLocationAdapterList = new ArrayList<>();
    private FirebaseFirestore db;

    private ActivitySearchCityBinding binding;
    private String getMessageFromPreviousIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchCityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getMessageFromPreviousIntent = getIntent().getStringExtra("choose");


        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getApplicationContext(), HomepageActivity.class);
                startActivity(back);
                finish();
            }
        });

        if(binding.etLocation.getText().toString().isEmpty()){
            nearbyLocationAdapterList.clear();
            getAllLocation();
        }
        binding.etLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nearbyLocationAdapterList.clear();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                db = FirebaseFirestore.getInstance();
                db.collection("location").orderBy("daerah").startAt(charSequence.toString()).endAt(charSequence.toString()+"~").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        nearbyLocationAdapterList.clear();
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                addDataToList(document);
                            }
                        }
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        nearbyLocationAdapter = new NearbyLocationAdapter(nearbyLocationAdapterList, this);
        binding.rvNearby.setHasFixedSize(true);
        binding.rvNearby.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.rvNearby.setAdapter(nearbyLocationAdapter);
    }

    private void getAllLocation() {
        db = FirebaseFirestore.getInstance();
        db.collection("location").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()){
                        addDataToList(document);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(getApplicationContext(), HomepageActivity.class);
        startActivity(back);
        finish();
    }

    public void addDataToList(QueryDocumentSnapshot document) {
        NearbyLocationModel modelData = new NearbyLocationModel();
        modelData.setDaerah(document.get("daerah").toString());
        modelData.setTerminal(document.get("terminal").toString());
        modelData.setKm(Integer.parseInt(document.get("km").toString()));

        nearbyLocationAdapterList.add(modelData);
        nearbyLocationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCLickListener(int adapterPosition) {
        Intent go = new Intent(getApplicationContext(), HomepageActivity.class);
        if(getMessageFromPreviousIntent.equals("departune")){
            Bundle bundle = new Bundle();
            bundle.putString("daerah", nearbyLocationAdapterList.get(adapterPosition).getDaerah());
            bundle.putString("terminal", nearbyLocationAdapterList.get(adapterPosition).getTerminal());
            bundle.putInt("km", nearbyLocationAdapterList.get(adapterPosition).getKm());

            go.putExtra("sendbackDepartune", bundle);

        } else if (getMessageFromPreviousIntent.equals("arrival")) {
            Bundle bundle = new Bundle();
            bundle.putString("daerah", nearbyLocationAdapterList.get(adapterPosition).getDaerah());
            bundle.putString("terminal", nearbyLocationAdapterList.get(adapterPosition).getTerminal());
            bundle.putInt("km", nearbyLocationAdapterList.get(adapterPosition).getKm());

            go.putExtra("sendbackArrival", bundle);
        }

        startActivity(go);
        finish();
    }
}