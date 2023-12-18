package com.uknown.kelompok8_project_akhir_8.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uknown.kelompok8_project_akhir_8.homepage.model.NearbyLocationModel;
import com.uknown.kelompok8_project_akhir_8.R;

import java.util.ArrayList;
import java.util.List;


public class NearbyLocationAdapter extends RecyclerView.Adapter<NearbyLocationAdapter.ViewHolder> {

    private List<NearbyLocationModel> listNearbyLocation = new ArrayList<>();
    private listener setListener;

    public NearbyLocationAdapter (List<NearbyLocationModel> listNearbyLocation, listener setOnClick) {
        this.listNearbyLocation = listNearbyLocation;
        this.setListener = setOnClick;
    }

    @NonNull
    @Override
    public NearbyLocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nearby_location, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NearbyLocationAdapter.ViewHolder holder, int position) {
        NearbyLocationModel modelData = listNearbyLocation.get(position);
        holder.daerah.setText(modelData.getDaerah());
        holder.terminal.setText(modelData.getTerminal());
    }

    @Override
    public int getItemCount() {
        return listNearbyLocation.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView daerah, terminal;
        private LinearLayout container;
        public ViewHolder(@NonNull View view) {
            super(view);
            daerah = view.findViewById(R.id.daerah);
            terminal = view.findViewById(R.id.terminal);
            container = view.findViewById(R.id.containerLocation);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setListener.onCLickListener(getAdapterPosition());
                }
            });
        }
    }

    public interface listener {
        void onCLickListener(int adapterPosition);
    }
}
