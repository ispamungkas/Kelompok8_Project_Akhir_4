package com.uknown.kelompok8_project_akhir_4.homepage.orderpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uknown.kelompok8_project_akhir_4.homepage.HomepageActivity;
import com.uknown.kelompok8_project_akhir_4.homepage.adapter.BusAdapter;
import com.uknown.kelompok8_project_akhir_4.homepage.model.BusModel;
import com.uknown.kelompok8_project_akhir_4.homepage.model.NearbyLocationModel;
import com.uknown.kelompok8_project_akhir_8.databinding.ActivityChooseBusBinding;

import java.util.ArrayList;
import java.util.List;

public class ChooseBusActivity extends AppCompatActivity implements BusAdapter.listener {

    private ActivityChooseBusBinding binding;
    private NearbyLocationModel departune;
    private NearbyLocationModel arrival;
    private List<BusModel> busModelList = new ArrayList<>();
    private FirebaseFirestore firebaseFirestore;
    private String date;
    private int passanger;
    private BusAdapter adapter;
    private ArrayList<String> bookedseat = new ArrayList<>();
    private List<String> id = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseBusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getIntent() != null &&
                getIntent().getExtras() != null &&
                getIntent().hasExtra("departune") &&
                getIntent().hasExtra("arrival") &&
                getIntent().hasExtra("ppl") &&
                getIntent().hasExtra("date")
        ){

            departune = (NearbyLocationModel) getIntent().getSerializableExtra("departune");
            arrival = (NearbyLocationModel) getIntent().getSerializableExtra("arrival");
            date = getIntent().getStringExtra("date");
            passanger = getIntent().getIntExtra("ppl", 1);

            setValueWithPreviousIntentData();
        }

        showAvailableBus();

        adapter = new BusAdapter(busModelList, this);
        binding.rvBus.setHasFixedSize(true);
        binding.rvBus.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.rvBus.setAdapter(adapter);

        binding.arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getApplicationContext(), HomepageActivity.class);
                startActivity(go);
                finish();
            }
        });
    }

    private void showAvailableBus() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("bus")
                .whereEqualTo("tanggalkeberangkatan", date)
                .whereEqualTo("terminal", departune.getTerminal())
                .whereEqualTo("terminaltujuan", arrival.getTerminal())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.getResult().size() > 0){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                addDataBus(documentSnapshot);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            busModelList.clear();
                            int temp = 0;
                            int kmAsal = departune.getKm();
                            int kmTujuan = arrival.getKm();

                            if(kmAsal >= kmTujuan){
                                temp = kmAsal - kmTujuan;
                            } else {
                                temp = kmTujuan - kmAsal;
                            }

                            int harga = (temp == 0) ? 25000 : 25000*temp;

                            bookedseat.add("0");

                            addListBusWhenBusisNotYet(harga, "08.00", "PGI0091", "Pahala Kencana", date, departune.getTerminal(), arrival.getTerminal(), "https://pbs.twimg.com/media/DKsvsS-UEAAQiRG.jpg", bookedseat);
                            addListBusWhenBusisNotYet(harga + 20000, "12.00", "SAG0091", "Simpati Star", date, departune.getTerminal(), arrival.getTerminal(), "https://img.inews.co.id/media/620/files/inews_new/2023/05/22/Sempati_Star_Double_Decker__1_.jpg", bookedseat);
                            addListBusWhenBusisNotYet(harga - 30000, "16.00", "MLM0091", "ALS", date, departune.getTerminal(), arrival.getTerminal(), "https://asset.kompas.com/crops/oPsmUEDVltVbkqkmWLZkwl15XCU=/0x24:600x424/1200x800/data/photo/2020/10/18/5f8b27154b6dc.png", bookedseat);

                            for (BusModel bus : busModelList){
                                firebaseFirestore.collection("bus").add(bus).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                    }
                                });
                            }
                            Toast.makeText(getApplicationContext(), "Ditemukan", Toast.LENGTH_SHORT).show();

                            busModelList.clear();
                            firebaseFirestore.collection("bus")
                                    .whereEqualTo("tanggalkeberangkatan", date)
                                    .whereEqualTo("terminal", departune.getTerminal())
                                    .whereEqualTo("terminaltujuan", arrival.getTerminal())
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                                addDataBus(documentSnapshot);
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void addDataBus (QueryDocumentSnapshot documentSnapshot){
       BusModel model = new BusModel(
               documentSnapshot.get("namabus").toString(),
               documentSnapshot.get("kodebus").toString(),
               documentSnapshot.get("terminal").toString(),
               documentSnapshot.get("terminaltujuan").toString(),
               Integer.parseInt(documentSnapshot.get("hargaseat").toString()),
               Integer.parseInt(documentSnapshot.get("maxpenumpang").toString()),
               Integer.parseInt(documentSnapshot.get("seatbooked").toString()),
               documentSnapshot.get("jamkeberangkatan").toString(),
               documentSnapshot.get("tanggalkeberangkatan").toString(),
               documentSnapshot.get("gambarbus").toString(),
               (ArrayList<String>) documentSnapshot.get("bookedseat")

       );
       id.add(documentSnapshot.getId());
       busModelList.add(model);
    }

    private void addListBusWhenBusisNotYet(
            int hargaseat,
            String jamkeberangkatan,
            String kodebus,
            String namabus,
            String tanggalkeberangkatan,
            String terminal,
            String terminaltujuan,
            String gambarbus,
            ArrayList<String> bookedseat
    ) {
        BusModel model = new BusModel(
                namabus, kodebus, terminal, terminaltujuan, hargaseat, 30, 0, jamkeberangkatan, tanggalkeberangkatan, gambarbus, bookedseat
        );
        busModelList.add(model);
    }

    private void setValueWithPreviousIntentData() {
        binding.counttiket.setText("Seat " +passanger);
        binding.dateTicket.setText(date);
        binding.departune.setText(departune.getDaerah());
        binding.arrival.setText(arrival.getDaerah());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent go = new Intent(getApplicationContext(), HomepageActivity.class);
        startActivity(go);
        finish();
    }

    @Override
    public void onClickListener(int adapterPosition) {
            BusModel busModel = busModelList.get(adapterPosition);
            Intent go = new Intent(getApplicationContext(), DetailOrderActivity.class);
            go.putExtra("busdata", busModel);
            go.putExtra("departune", departune);
            go.putExtra("arrival", arrival);
            go.putExtra("date", date);
            go.putExtra("ppl", passanger);
            go.putExtra("busid", id.get(adapterPosition));
            startActivity(go);
    }
}