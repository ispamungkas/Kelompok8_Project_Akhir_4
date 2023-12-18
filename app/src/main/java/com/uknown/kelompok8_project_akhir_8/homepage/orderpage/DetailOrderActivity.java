package com.uknown.kelompok8_project_akhir_8.homepage.orderpage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.uknown.kelompok8_project_akhir_8.Utils;
import com.uknown.kelompok8_project_akhir_8.databinding.ActivityDetailOrderBinding;
import com.uknown.kelompok8_project_akhir_8.databinding.DialogImageBinding;
import com.uknown.kelompok8_project_akhir_8.homepage.detail_tiket.DetailTicketBeforePaymentActivity;
import com.uknown.kelompok8_project_akhir_8.homepage.model.BusModel;
import com.uknown.kelompok8_project_akhir_8.homepage.model.NearbyLocationModel;
import com.uknown.kelompok8_project_akhir_8.homepage.orderpage.payment.ChoosePaymentActivity;
import com.uknown.kelompok8_project_akhir_8.R;

import java.util.ArrayList;

public class DetailOrderActivity extends AppCompatActivity {

    private ActivityDetailOrderBinding binding;
    private DialogImageBinding bindingImage;
    private NearbyLocationModel departune, arrival;
    private BusModel busModel;
    private String date;
    private int passanger;
    private int seatSelected = 0;
    private ArrayList<String> dataselected = new ArrayList<>();
    private ArrayList<String> dataSelectedSeatBefore = new ArrayList<>();
    private String id = "";
    private Dialog dgImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setInitialComponent();
    }

    private void setInitialComponent() {

        if(checkIntent()){

            if (getIntent() != null && getIntent().hasExtra("dataselected")){
                 dataselected = getIntent().getStringArrayListExtra("dataselected");
                 seatSelected += dataselected.size();
            }

            Glide.with(getApplicationContext())
                    .load(busModel.getGambarbus())
                    .placeholder(R.drawable.loading)
                    .into(binding.imageBus);

            int hasil = busModel.getMaxpenumpang() - busModel.getSeatbooked();
            System.out.println(busModel.getMaxpenumpang());
            System.out.println(busModel.getSeatbooked());

            binding.total.setText(hasil + " seat are available");
            binding.busName.setText(busModel.getNamabus());
            binding.dateBus.setText(date);
            binding.numberBus.setText(busModel.getKodebus());
            binding.tvDarimana.setText(departune.getDaerah());
            binding.tvJamDarimana.setText(busModel.getJamkeberangkatan());
            binding.tvKemana.setText(arrival.getDaerah());
            binding.tvTerminal.setText(busModel.getTerminal());
            binding.tvTerminal1.setText(busModel.getTerminaltujuan());
            binding.seatSelected.setText(String.valueOf(seatSelected));
            binding.muchSeatPassanger.setText(String.valueOf(passanger));

            if(busModel.getKodebus().equals("SAG0091")){
                binding.classBus.setText("Executive");
            } else if (busModel.getKodebus().equals("PGI0091")){
                binding.classBus.setText("VIP");
            }

            binding.muchPassanger.setText(String.valueOf(passanger));
            binding.pricePerSeat.setText(Utils.toRupiah(busModel.getHargaseat()));

            int priceResult = busModel.getHargaseat() * passanger;
            binding.priceResult.setText(Utils.toRupiah((double) priceResult));

            binding.btnSeat.setOnClickListener((item) -> {
                Intent go = new Intent(getApplicationContext(), ChooseSeatActivity.class);
                go.putStringArrayListExtra("dataSelectedSeat", dataselected);
                go.putStringArrayListExtra("data", dataSelectedSeatBefore);
                go.putExtra("busdata", busModel);
                go.putExtra("departune", departune);
                go.putExtra("arrival", arrival);
                go.putExtra("date", date);
                go.putExtra("ppl", passanger);
                go.putExtra("busid", id);
                startActivity(go);
                finish();
            });

            binding.btnBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(seatSelected != passanger){
                        Toast.makeText(DetailOrderActivity.this, "Each passanger must have a seat", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent go = new Intent(getApplicationContext(), DetailTicketBeforePaymentActivity.class);
                    startActivity(go);
                    go.putStringArrayListExtra("data", dataselected);
                    go.putExtra("priceresult", priceResult);
                    go.putExtra("busdata", busModel);
                    go.putExtra("departune", departune);
                    go.putExtra("arrival", arrival);
                    go.putExtra("date", date);
                    go.putExtra("ppl", passanger);
                    go.putExtra("busid", id);
                    startActivity(go);
                    finish();
                }
            });

            binding.btnSeePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dgImage = initial();
                    bindingImage = DialogImageBinding.inflate(getLayoutInflater());
                    dgImage.setContentView(bindingImage.getRoot());
                    dgImage.show();
                    actionDialog(bindingImage);
                }
            });

            binding.arrowBack.setOnClickListener((item) -> {
                Intent back = new Intent(getApplicationContext(), ChooseBusActivity.class);
                back.putExtra("departune", departune);
                back.putExtra("arrival", arrival);
                back.putExtra("ppl", passanger);
                back.putExtra("date", date);
                startActivity(back);
                finishAffinity();

            });
        }
    }

    private void actionDialog(DialogImageBinding bindingImage) {
        Glide.with(this)
                .load(busModel.getGambarbus())
                .placeholder(R.drawable.loading)
                .into(bindingImage.imagedialog);
    }

    private Dialog initial() {
        Dialog d = new Dialog(this);

        Window window = d.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(getDrawable(R.drawable.background_white));
        d.setCancelable(true);

        return d;
    }

    private boolean checkIntent() {
        if(getIntent() != null &&
            getIntent().getExtras() != null &&
            getIntent().hasExtra("busdata") &&
            getIntent().hasExtra("departune") &&
            getIntent().hasExtra("arrival") &&
            getIntent().hasExtra("date") &&
            getIntent().hasExtra("ppl")
        ) {
            busModel = (BusModel) getIntent().getSerializableExtra("busdata");
            departune = (NearbyLocationModel) getIntent().getSerializableExtra("departune");
            arrival = (NearbyLocationModel) getIntent().getSerializableExtra("arrival");
            date = getIntent().getStringExtra("date");
            passanger = getIntent().getIntExtra("ppl", 1);
            dataSelectedSeatBefore = busModel.getBookedseat();
            id = getIntent().getStringExtra("busid");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent back = new Intent(getApplicationContext(), ChooseBusActivity.class);
        back.putExtra("departune", departune);
        back.putExtra("arrival", arrival);
        back.putExtra("ppl", passanger);
        back.putExtra("date", date);
        startActivity(back);
        finishAffinity();
    }
}