package com.uknown.kelompok8_project_akhir_8.homepage.orderpage.payment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uknown.kelompok8_project_akhir_8.databinding.FragmentRetailBinding;
import com.uknown.kelompok8_project_akhir_8.homepage.orderpage.payment.detailpayment.DetailRetailPaymentFragment;
import com.uknown.kelompok8_project_akhir_8.R;

public class RetailFragment extends Fragment {

    private FragmentRetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRetailBinding.inflate(inflater, container, false);

        binding.alfaContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutAc, new DetailRetailPaymentFragment())
                        .addToBackStack("alfa")
                        .commit();
            }
        });

        return binding.getRoot();
    }
}