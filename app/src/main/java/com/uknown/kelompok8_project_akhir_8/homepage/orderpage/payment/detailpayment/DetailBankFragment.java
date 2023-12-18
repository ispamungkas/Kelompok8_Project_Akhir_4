package com.uknown.kelompok8_project_akhir_8.homepage.orderpage.payment.detailpayment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uknown.kelompok8_project_akhir_8.Utils;
import com.uknown.kelompok8_project_akhir_8.databinding.FragmentDetailBankBinding;
import com.uknown.kelompok8_project_akhir_8.homepage.model.BusModel;
import com.uknown.kelompok8_project_akhir_8.homepage.model.NearbyLocationModel;
import com.uknown.kelompok8_project_akhir_8.homepage.model.TicketModel;
import com.uknown.kelompok8_project_akhir_8.homepage.model.User;
import com.uknown.kelompok8_project_akhir_8.homepage.orderpage.payment.ChoosePaymentActivity;
import com.uknown.kelompok8_project_akhir_8.homepage.orderpage.payment.SuccessPaymentActivity;
import com.uknown.kelompok8_project_akhir_8.R;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DetailBankFragment extends Fragment{

    private FragmentDetailBankBinding binding;
    private ChoosePaymentActivity activity;
    private FirebaseFirestore firebaseFirestore;
    private Bundle dataFromActivity;
    private String userId;
    private TicketModel ticketModel;
    private NearbyLocationModel departune;
    private NearbyLocationModel arrival;
    private BusModel busModel;
    private User dataUser = new User();
    private int accumulatePrice;
    private int code = 0;
    private List<String> dataSelectedCurrent = new ArrayList<>();
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        binding = FragmentDetailBankBinding.inflate(inflater, container, false);
        activity = (ChoosePaymentActivity) getActivity();
        firebaseFirestore = FirebaseFirestore.getInstance();
        dataFromActivity = activity.passingDataToAnyFragment();

        Bundle bundle = this.getArguments();
        String userChoice = bundle.getString("choose");
        setDataUser();


        if(userChoice.equals("cimb")){
            binding.imagebank.setImageResource(R.drawable.image_cimbniaga);
            binding.bankname.setText(R.string.cimb_niaga_mobile_banking);
        }

        binding.amount.setText(Utils.toRupiah((double) dataFromActivity.getInt("accumulatePrice")));

        binding.verifpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addModelTicket(userChoice);
                createTicket();
                updateDataBus();

            }
        });


        return binding.getRoot();
    }

    private void addModelTicket(String userChoice) {
        // create date today
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        SimpleDateFormat formatter1
                = new SimpleDateFormat("yyyy-MM-dd HH:mm");


        // setting initialitation all model
        setDatafromActivity();

        ticketModel = new TicketModel(
                formatter1.format(ts),
                code,
                0,
                dataFromActivity.getInt("ppl"),
                dataFromActivity.getString("date"),
                busModel,
                dataUser,
                departune,
                arrival,
                dataSelectedCurrent,
                dataFromActivity.getString("busid"),
                userChoice,
                dataUser.getUserId(),
                accumulatePrice,
                null
        );

    }

    private void setDatafromActivity(){
        departune = (NearbyLocationModel) dataFromActivity.getSerializable("departune");
        arrival = (NearbyLocationModel) dataFromActivity.getSerializable("arrival");
        busModel = (BusModel) dataFromActivity.getSerializable("busdata");
        dataSelectedCurrent = dataFromActivity.getStringArrayList("data");
        accumulatePrice = dataFromActivity.getInt("accumulatePrice");
        code = dataFromActivity.getInt("code");
    }

    private void setDataUser() {
        firebaseFirestore.collection("users").whereEqualTo("userId", mAuth.getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> data = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot ds : data){
                            dataUser = ds.toObject(User.class);
                        }
                    }
                });
    }

    private void createTicket() {
        firebaseFirestore.collection("ticket").add(ticketModel)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent go = new Intent(activity, SuccessPaymentActivity.class);
                        go.putExtra("id", ticketModel.getNumberBook());
                        startActivity(go);
                        getActivity().finish();
                        Toast.makeText(activity, "The transaction was successful", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateDataBus() {
        String busid = dataFromActivity.getString("busid");
        int before = busModel.getSeatbooked();
        ArrayList<String> dataSelected = dataFromActivity.getStringArrayList("data");
        int result = before + dataSelectedCurrent.size();
        ArrayList<String> dataSelectedBefore = busModel.getBookedseat();
        dataSelected.addAll(dataSelectedBefore);

        busModel.setBookedseat(dataSelected);
        busModel.setSeatbooked(result);

        DocumentReference docs = firebaseFirestore.collection("bus").document(busid);
        Map<String, Object> map = new HashMap<>();
        map.put("seatbooked", result);
        map.put("bookedseat", dataSelected);
        docs.update(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });

    }
}