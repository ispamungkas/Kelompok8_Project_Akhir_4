package com.uknown.kelompok8_project_akhir_8.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uknown.kelompok8_project_akhir_8.R;
import com.uknown.kelompok8_project_akhir_8.databinding.ItemHistoryBinding;
import com.uknown.kelompok8_project_akhir_8.homepage.model.TicketModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<TicketModel> ticketModelList = new ArrayList<>();
    private ItemHistoryBinding binding;

    private OnClickListener onClickListener;
    public interface OnClickListener{
        void onClickListener(int adapterPosition);
    }

    public HistoryAdapter(List<TicketModel> ticketModelList, OnClickListener listener){
        this.ticketModelList = ticketModelList;
        this.onClickListener = listener;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        TicketModel modelinindex = ticketModelList.get(position);
        holder.bind.bookId.setText(String.valueOf(modelinindex.getNumberBook()));
        holder.bind.date.setText(modelinindex.getDateToday());
        holder.bind.busName.setText(modelinindex.getBusModel().getNamabus());
        holder.bind.timeArrived.setText(modelinindex.getBusModel().getJamkeberangkatan());
        holder.bind.busNumber.setText(modelinindex.getBusModel().getKodebus());
        holder.bind.arrival.setText(modelinindex.getDepartune().getTerminal());

        if(modelinindex.getRate() > 0){
            holder.bind.paid.txt.setText(R.string.issued);
        }

    }

    @Override
    public int getItemCount() {
        return ticketModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemHistoryBinding bind;
        public ViewHolder(@NonNull ItemHistoryBinding binding) {
            super(binding.getRoot());
            bind = binding;
            bind.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClickListener(getAdapterPosition());
                }
            });
        }
    }
}
