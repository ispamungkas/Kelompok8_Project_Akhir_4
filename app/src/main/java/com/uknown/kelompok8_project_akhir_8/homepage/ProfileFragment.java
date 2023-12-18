package com.uknown.kelompok8_project_akhir_8.homepage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.uknown.kelompok8_project_akhir_8.databinding.FragmentProfileBinding;
import com.uknown.kelompok8_project_akhir_8.homepage.model.User;
import com.uknown.kelompok8_project_akhir_8.register.LoginActivity;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        db = FirebaseFirestore.getInstance();
        loadUserData();
        binding.c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getActivity().getApplicationContext(), AboutActivity.class);
                startActivity(go);
                getActivity().finish();
            }
        });


        return binding.getRoot();
    }

    private void loadUserData() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            db.collection("users")
                    .document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                User user = document.toObject(User.class);

                                if (user != null) {
                                    binding.name.setText(user.getName());

                                    String phoneNumber = user.getPhoneNumber();
                                    if (phoneNumber != null && !phoneNumber.isEmpty()) {
                                        binding.nomor.setText(phoneNumber);
                                    } else {
                                        binding.nomor.setVisibility(View.GONE);
                                    }

                                    String userEmail = user.getEmail();
                                    binding.email.setText(userEmail);

                                    if (user.getPhoto() != null && !user.getPhoto().isEmpty()) {
                                        Glide.with(getActivity().getApplicationContext())
                                                .load(user.getPhoto())
                                                .into(binding.imageViewProfile);
                                    }

                                    // Logout button click listener
                                    binding.btnLogout.setOnClickListener(view -> {
                                        // Handle logout logic
                                        FirebaseAuth.getInstance().signOut();

                                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.remove("userId");
                                        editor.apply();

                                        // Redirect to LoginActivity
                                        Intent intent = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        getActivity().finish(); // Close current activity

                                    });
                                }
                            } else {
                                // Document does not exist
                            }
                        } else {
                            // Handle errors
                        }
                    });
        }
    }


}