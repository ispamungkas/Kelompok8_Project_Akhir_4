package com.uknown.kelompok8_project_akhir_8.homepage.orderpage.payment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uknown.kelompok8_project_akhir_8.databinding.FragmentCreditCardBinding;
import com.uknown.kelompok8_project_akhir_8.homepage.orderpage.payment.detailpayment.DetailCreditCardFragment;
import com.uknown.kelompok8_project_akhir_8.R;

public class CreditCardFragment extends Fragment {

    private FragmentCreditCardBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreditCardBinding.inflate(inflater, container, false);

        binding.mastercardContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailCreditCardFragment fragment = new DetailCreditCardFragment();
                Bundle bundle  = new Bundle();
                bundle.putString("choose", "mastercard");
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutAc, fragment)
                        .addToBackStack("master")
                        .commit();
            }
        });

        binding.visaContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailCreditCardFragment fragment = new DetailCreditCardFragment();
                Bundle bundle  = new Bundle();
                bundle.putString("choose", "visa");
                fragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutAc, fragment)
                        .addToBackStack("visa")
                        .commit();
            }
        });

        return binding.getRoot();
    }
}