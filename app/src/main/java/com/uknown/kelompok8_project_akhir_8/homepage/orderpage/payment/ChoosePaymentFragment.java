package com.uknown.kelompok8_project_akhir_8.homepage.orderpage.payment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uknown.kelompok8_project_akhir_8.R;
import com.uknown.kelompok8_project_akhir_8.databinding.FragmentChoosePaymentBinding;

public class ChoosePaymentFragment extends Fragment {

    private FragmentChoosePaymentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChoosePaymentBinding.inflate(inflater, container, false);

        binding.banktransferContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutAc, new BankTransferFragment())
                        .addToBackStack("bank")
                        .commit();
            }
        });

        binding.creditcardContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutAc, new CreditCardFragment())
                        .addToBackStack("credit")
                        .commit();
            }
        });

        binding.retailpaymentContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutAc, new RetailFragment())
                        .addToBackStack("retail")
                        .commit();
            }
        });

        return binding.getRoot();
    }
}