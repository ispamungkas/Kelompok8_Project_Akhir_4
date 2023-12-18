package com.uknown.kelompok8_project_akhir_8.homepage.orderpage.payment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uknown.kelompok8_project_akhir_8.databinding.FragmentBankTransferBinding;
import com.uknown.kelompok8_project_akhir_8.homepage.orderpage.payment.detailpayment.DetailBankFragment;
import com.uknown.kelompok8_project_akhir_8.R;

public class BankTransferFragment extends Fragment {

    private FragmentBankTransferBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentBankTransferBinding.inflate(inflater, container, false);

        Bundle bundle = new Bundle();

        binding.bniContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("choose", "bni");
                DetailBankFragment fragment = new DetailBankFragment();
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutAc, fragment)
                        .addToBackStack("bni")
                        .commit();
            }
        });

        binding.cimbContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("choose", "cimb");
                DetailBankFragment fragment = new DetailBankFragment();
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutAc, fragment)
                        .addToBackStack("cimb")
                        .commit();
            }
        });
        return binding.getRoot();
    }
}