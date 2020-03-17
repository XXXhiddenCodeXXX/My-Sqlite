package com.example.sqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.R;
import com.example.sqlite.model.Mahasiswa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Sanjaya on 3/17/2020
 * Sqlite
 */
public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ListViewHolder> {

    private List<Mahasiswa> mahasiswa = new ArrayList<>();
    private Context context;

    public MahasiswaAdapter(List<Mahasiswa> mahasiswa, Context context) {
        this.mahasiswa = mahasiswa;
        this.context = context;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.tvName.setText(mahasiswa.get(position).getName());
        holder.tvNim.setText(mahasiswa.get(position).getNim());
    }

    @Override
    public int getItemCount() {
        return mahasiswa.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvNim;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvNim = itemView.findViewById(R.id.tv_nim);
        }
    }
}
