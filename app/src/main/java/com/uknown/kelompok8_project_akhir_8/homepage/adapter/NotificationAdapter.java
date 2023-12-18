package com.uknown.kelompok8_project_akhir_8.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uknown.kelompok8_project_akhir_8.databinding.ItemNotificationBinding;
import com.uknown.kelompok8_project_akhir_8.homepage.model.TicketModel;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<TicketModel> ticketModelList = new ArrayList<>();
    private ItemNotificationBinding binding;
    private OnClickListener listener;

    public NotificationAdapter(List<TicketModel> models, OnClickListener listener){
        this.ticketModelList = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        holder.bindingx.tanggalTransaksi.setText(ticketModelList.get(position).getDateToday());
    }

    @Override
    public int getItemCount() {
        return ticketModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemNotificationBinding bindingx;
        public ViewHolder(@NonNull ItemNotificationBinding bind) {
            super(bind.getRoot());
            bindingx = bind;
            bindingx.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickListener(getAdapterPosition());
                }
            });
        }
    }

    public interface OnClickListener{
        void onClickListener(int adapterPosition);
    }
}
