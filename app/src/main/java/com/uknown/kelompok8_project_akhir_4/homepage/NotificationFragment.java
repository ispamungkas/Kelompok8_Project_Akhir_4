 package com.uknown.kelompok8_project_akhir_4.homepage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uknown.kelompok8_project_akhir_8.R;
import com.uknown.kelompok8_project_akhir_8.databinding.FragmentNotificationBinding;

 public class NotificationFragment extends Fragment {

    private FragmentNotificationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}