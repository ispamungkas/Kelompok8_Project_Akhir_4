 package com.uknown.kelompok8_project_akhir_8.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uknown.kelompok8_project_akhir_8.databinding.FragmentNotificationBinding;
import com.uknown.kelompok8_project_akhir_8.homepage.adapter.NotificationAdapter;
import com.uknown.kelompok8_project_akhir_8.homepage.detail_tiket.DetailTicketActivity;
import com.uknown.kelompok8_project_akhir_8.homepage.model.TicketModel;

import java.util.ArrayList;
import java.util.List;

 public class NotificationFragment extends Fragment implements NotificationAdapter.OnClickListener {

    private FragmentNotificationBinding binding;
    private List<TicketModel> modelList = new ArrayList<>();
    private FirebaseFirestore firebaseFirestore;
    private NotificationAdapter adapter;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        query();

        adapter = new NotificationAdapter(modelList, this);
        binding.rvNotification.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        binding.rvNotification.setHasFixedSize(false);
        binding.rvNotification.setAdapter(adapter);

        System.out.println(modelList);

        return binding.getRoot();
    }

     private void query() {
        firebaseFirestore.collection("ticket").whereEqualTo("uid", mAuth.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot doc : task.getResult()){
                            addToModel(doc);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
     }

     private void addToModel(QueryDocumentSnapshot doc) {
        TicketModel model = doc.toObject(TicketModel.class);
        modelList.add(model);
     }

     @Override
     public void onClickListener(int adapterPosition) {
        TicketModel model = modelList.get(adapterPosition);
        Intent go = new Intent(getActivity().getApplicationContext(), DetailTicketActivity.class);
        go.putExtra("model", model);
        go.putExtra("fromnotification", 1);
        startActivity(go);
        getActivity().finish();
     }
 }