package com.uknown.kelompok8_project_akhir_8.homepage.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.uknown.kelompok8_project_akhir_8.Utils;
import com.uknown.kelompok8_project_akhir_8.databinding.ItemBusChoiceBinding;
import com.uknown.kelompok8_project_akhir_8.homepage.model.BusModel;
import com.uknown.kelompok8_project_akhir_8.R;

import java.util.ArrayList;
import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder> {

    private List<BusModel> busModelList = new ArrayList<>();
    private listener onllistener;
    private Context context;

    public BusAdapter(List<BusModel> busModelList, listener setClickListener, Context context){
        this.busModelList = busModelList;
        this.onllistener = setClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public BusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBusChoiceBinding binding = ItemBusChoiceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BusAdapter.ViewHolder holder, int position) {

        BusModel busModelAtPosition = busModelList.get(position);

        holder.binding.tvNamaBus.setText(busModelAtPosition.getNamabus());
        holder.binding.tvKodeBus.setText(busModelAtPosition.getKodebus());
        holder.binding.tvDarimana.setText(busModelAtPosition.getTerminal());
        holder.binding.tanggalkeberangkatan.setText(busModelAtPosition.getTanggalkeberangkatan());
        holder.binding.tvJamDarimana.setText(busModelAtPosition.getJamkeberangkatan());
        holder.binding.tvKemana.setText(busModelAtPosition.getTerminaltujuan());
        holder.binding.tvHarga.setText(Utils.toRupiah((double) busModelAtPosition.getHargaseat()));

        if(busModelAtPosition.getMaxpenumpang() - busModelAtPosition.getSeatbooked() <= 0){
            int youngGrey = ContextCompat.getColor(context, R.color.close);
            int greyBlack = ContextCompat.getColor(context, R.color.grey);
            Drawable buttonClose = ContextCompat.getDrawable(context, R.drawable.background_button_cantclick);
            holder.binding.container.setBackgroundColor(youngGrey);
            holder.binding.tvNamaBus.setTextColor(greyBlack);
            holder.binding.tvHarga.setTextColor(greyBlack);
            holder.binding.btnBook.setBackground(buttonClose);
            holder.binding.btnBook.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return busModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemBusChoiceBinding binding;
        public ViewHolder(@NonNull ItemBusChoiceBinding bindingParameter) {
            super(bindingParameter.getRoot());
            binding = bindingParameter;

            binding.btnBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onllistener.onClickListener(getAdapterPosition());
                }
            });
        }
    }

    public interface listener {
        void onClickListener(int adapterPosition);
    }
}
