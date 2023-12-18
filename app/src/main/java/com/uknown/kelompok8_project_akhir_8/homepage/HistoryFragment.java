package com.uknown.kelompok8_project_akhir_8.homepage;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uknown.kelompok8_project_akhir_8.databinding.DialogHistoryBinding;
import com.uknown.kelompok8_project_akhir_8.databinding.FragmentHistoryBinding;
import com.uknown.kelompok8_project_akhir_8.homepage.adapter.HistoryAdapter;
import com.uknown.kelompok8_project_akhir_8.homepage.detail_tiket.DetailTicketActivity;
import com.uknown.kelompok8_project_akhir_8.homepage.model.TicketModel;
import com.uknown.kelompok8_project_akhir_8.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryFragment extends Fragment implements HistoryAdapter.OnClickListener {

    private List<TicketModel> ticketModelList = new ArrayList<>();
    private List<String> id = new ArrayList<>();
    private FragmentHistoryBinding binding;
    private DialogHistoryBinding bindingHistory;
    private FirebaseFirestore firebaseFirestore;
    private String userId;
    private HistoryAdapter adapter;
    private Dialog dialog;
    float rate;
    private FirebaseAuth mAuth;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        queryDataListTicket();

        adapter = new HistoryAdapter(ticketModelList, this);

        binding.rvHistory.setHasFixedSize(true);
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        binding.rvHistory.setAdapter(adapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLayout();
            }
        }, 3000);

        return binding.getRoot();
    }

    private void queryDataListTicket() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("ticket").whereEqualTo("uid", mAuth.getUid()).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(QueryDocumentSnapshot d : task.getResult()){
                                    addTolistTicket(d);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });

    }

    private void addTolistTicket(QueryDocumentSnapshot d) {
        TicketModel model = new TicketModel();
        model = d.toObject(TicketModel.class);

        id.add(d.getId());
        ticketModelList.add(model);
    }


    private void checkLayout() {
        if(ticketModelList.isEmpty()){
            binding.diplaynoticket.setVisibility(View.VISIBLE);
            binding.rvHistory.setVisibility(View.GONE);
        } else {
            binding.diplaynoticket.setVisibility(View.GONE);
            binding.rvHistory.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClickListener(int adapterPosition) {
        TicketModel modelInPosition = ticketModelList.get(adapterPosition);
        if(modelInPosition.getRate() == 0){
            dialog = generate();
            bindingHistory = DialogHistoryBinding.inflate(getLayoutInflater());
            dialog.setContentView(bindingHistory.getRoot());
            dialog.show();
            actionDialog(bindingHistory, dialog, modelInPosition, adapterPosition);
        } else {
            TicketModel dataUpdate = ticketModelList.get(adapterPosition);

            Intent go = new Intent(getActivity().getApplicationContext(), DetailTicketActivity.class);
            go.putExtra("model", dataUpdate);
            startActivity(go);
            getActivity().finish();
        }
    }

    private void actionDialog(DialogHistoryBinding bindingHistory, Dialog dialog, TicketModel model, int adapterPosisition) {

        bindingHistory.busName.setText(model.getBusModel().getNamabus());
        bindingHistory.busNumber.setText(model.getBusModel().getKodebus());
        bindingHistory.timeArrived.setText(model.getBusModel().getJamkeberangkatan());

        bindingHistory.rateClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rate = bindingHistory.rbTicket.getRating();
                updateTicket(model, adapterPosisition, rate);

                Intent go = new Intent(getActivity().getApplicationContext(), DetailTicketActivity.class);
                go.putExtra("model", model);
                go.putExtra("rate", rate);
                startActivity(go);
                getActivity().finish();
            }
        });
    }

    private void updateTicket(TicketModel model, int adapterPosition, float rate) {
        String index = id.get(adapterPosition);
        Map<String, Object> dataUpdate = new HashMap<>();
        dataUpdate.put("rate", rate);
        firebaseFirestore.collection("ticket").document(index)
                .update(dataUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity().getApplicationContext(), "Thankyou for your feedback", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private Dialog generate(){
        Dialog d = new Dialog(getActivity());

        Window window = d.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(getActivity().getDrawable(R.drawable.background_white));
        d.setCancelable(true);

        return d;
    }
}