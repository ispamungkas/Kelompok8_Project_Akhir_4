package com.uknown.kelompok8_project_akhir_4.homepage;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.slider.Slider;
import com.uknown.kelompok8_project_akhir_4.homepage.model.BusModel;
import com.uknown.kelompok8_project_akhir_4.homepage.model.NearbyLocationModel;
import com.uknown.kelompok8_project_akhir_4.homepage.orderpage.ChooseBusActivity;
import com.uknown.kelompok8_project_akhir_4.homepage.orderpage.SearchCityActivity;
import com.uknown.kelompok8_project_akhir_8.R;
import com.uknown.kelompok8_project_akhir_8.databinding.DialogMuchPassangerBinding;
import com.uknown.kelompok8_project_akhir_8.databinding.FragmentSearchBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private DialogMuchPassangerBinding dialogMuchPassangerBinding;
    private Bundle bundleDepartune, bundleArrived;
    private String departune = "Select your departune", arrived = "Select your arrived";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        Dialog ppl = ppl();

        Bundle bundle = this.getArguments();
        SharedPreferences sp = this.getActivity().getSharedPreferences("departune", Context.MODE_PRIVATE);
        SharedPreferences.Editor add = sp.edit();

        if (!(bundle == null)){
            if(bundle.getBundle("1") != null){
//                SharedPreferences sp = this.getActivity().getSharedPreferences("departune", Context.MODE_PRIVATE);
//                SharedPreferences.Editor add = sp.edit();
                add.putString("slDepartuneDaerah", bundle.getBundle("1").getString("daerah"));
                add.putString("slDepartuneTerminal", bundle.getBundle("1").getString("terminal"));
                add.putInt("slDepartuneKm", bundle.getBundle("1").getInt("km"));
                add.apply();
//                binding.departuneLocation.setText(this.getActivity().getSharedPreferences("departune", Context.MODE_PRIVATE).getString("slDepartuneDepartuneDaerah", null));
                binding.departuneLocation.setText(this.getActivity().getSharedPreferences("departune", Context.MODE_PRIVATE).getString("slDepartuneDaerah", null));
            }
            if(bundle.getBundle("2") != null) {
//                SharedPreferences sp = this.getActivity().getSharedPreferences("arrival", Context.MODE_PRIVATE);
//                SharedPreferences.Editor add = sp.edit();
                add.putString("slArrivalDaerah", bundle.getBundle("2").getString("daerah"));
                add.putString("slArrivalTerminal", bundle.getBundle("2").getString("terminal"));
                add.putInt("slArrivalKm", bundle.getBundle("2").getInt("km"));
                add.apply();
//                binding.arrivalLocation.setText(this.getActivity().getSharedPreferences("arrival", Context.MODE_PRIVATE).getString("slDepartuneArrivalDaerah", null));
                binding.arrivalLocation.setText(this.getActivity().getSharedPreferences("departune", Context.MODE_PRIVATE).getString("slArrivalDaerah", null));
            }
        }

        SharedPreferences spp = this.getActivity().getSharedPreferences("departune", Context.MODE_PRIVATE);
        if (spp != null){
            if (spp.getString("slDepartuneDaerah", null) != null){
                binding.departuneLocation.setText(spp.getString("slDepartuneDaerah", null));
            }
            if (spp.getString("slArrivalDaerah", null) != null){
                binding.arrivalLocation.setText(spp.getString("slArrivalDaerah", null));
            }
        }

//        if(this.getActivity().getSharedPreferences("departune", Context.MODE_PRIVATE).getString("slDepartuneDepartuneDaerah", null)  != null){
//            binding.departuneLocation.setText(this.getActivity().getSharedPreferences("departune", Context.MODE_PRIVATE).getString("slDepartuneDepartuneDaerah", null));
//        }
//        if (this.getActivity().getSharedPreferences("arrival", Context.MODE_PRIVATE).getString("slDepartuneArrivalDaerah", null)  != null){
//            binding.arrivalLocation.setText(this.getActivity().getSharedPreferences("arrival", Context.MODE_PRIVATE).getString("slDepartuneArrivalDaerah", null));
//        }

        binding.manyPassanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMuchPassangerBinding = DialogMuchPassangerBinding.inflate(getLayoutInflater());
                ppl.setContentView(dialogMuchPassangerBinding.getRoot());
                ppl.show();
                dialogAction(dialogMuchPassangerBinding, ppl);
            }
        });

        binding.departuneLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getActivity(), SearchCityActivity.class);
                go.putExtra("choose", "departune");
                startActivity(go);
                getActivity().finish();
            }
        });

        binding.arrivalLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(getActivity(), SearchCityActivity.class);
                go.putExtra("choose", "arrival");
                startActivity(go);
                getActivity().finish();
            }
        });

        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        String data = new SimpleDateFormat("MM-dd-yy", Locale.getDefault()).format(new Date(selection));
                        binding.date.setText(data);
                    }
                });
                datePicker.show(getActivity().getSupportFragmentManager(), "tag");
            }
        });

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentBookWithData();
            }
        });

        return binding.getRoot();


    }

    private void intentBookWithData() {
        if (binding.date.getText().toString().equals(getString(R.string.select_date))){
            Toast.makeText(getActivity().getApplicationContext(), "Silahkan pilih tanggal keberangkatan", Toast.LENGTH_SHORT).show();
            return;
        }

        NearbyLocationModel nearbyLocationModelDepartune = new NearbyLocationModel(
                getActivity().getSharedPreferences("departune", Context.MODE_PRIVATE).getString("slDepartuneDaerah", null),
                getActivity().getSharedPreferences("departune", Context.MODE_PRIVATE).getString("slDepartuneTerminal", null),
                getActivity().getSharedPreferences("departune", Context.MODE_PRIVATE).getInt("slDepartuneKm", 1)
        );

        NearbyLocationModel nearbyLocationModelArrival = new NearbyLocationModel(
                getActivity().getSharedPreferences("departune", Context.MODE_PRIVATE).getString("slArrivalDaerah", null),
                getActivity().getSharedPreferences("departune", Context.MODE_PRIVATE).getString("slArrivalTerminal", null),
                getActivity().getSharedPreferences("departune", Context.MODE_PRIVATE).getInt("slArrivalKm", 1)
        );

        int getPassanger = Integer.parseInt(String.valueOf(binding.passanger.getText().charAt(0)));
        String getDate = binding.date.getText().toString();

        Intent go = new Intent(getActivity().getApplicationContext(), ChooseBusActivity.class);
        go.putExtra("departune", nearbyLocationModelDepartune);
        go.putExtra("arrival", nearbyLocationModelArrival);
        go.putExtra("ppl", getPassanger);
        go.putExtra("date", getDate);
        startActivity(go);
        getActivity().finish();
    }

    private void dialogAction(DialogMuchPassangerBinding binding, Dialog dialog) {
        // Inisialisasi Passanger
        binding.muchPassanger.setText(String.valueOf((int) binding.sliderPassanger.getValue()));

        binding.sliderPassanger.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                int cast = (int) value;
                binding.muchPassanger.setText(String.valueOf(cast));
            }
        });
        binding.btnCancel.setOnClickListener((item) -> {dialog.dismiss();});
        binding.btnSelect.setOnClickListener((item) -> {
            int manyPassanger = Integer.parseInt(binding.muchPassanger.getText().toString());
            setValuePPL(manyPassanger);
            dialog.dismiss();
        });
    }

    private void setValuePPL(int manyPassanger) {
        String showMuchPassanger = manyPassanger + " PPL";
        binding.passanger.setText(showMuchPassanger);
    }

    public Dialog ppl(){
        // Inisialisasi
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_much_passanger);

        // Setup Layout
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(getActivity().getDrawable(R.drawable.background_white));
        window.getAttributes().gravity = Gravity.BOTTOM;

        // Not Canccelable
        dialog.setCancelable(false);

        return dialog;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}