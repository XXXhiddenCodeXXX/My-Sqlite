package com.example.sqlite.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.R;
import com.example.sqlite.db.Const;
import com.example.sqlite.db.Dbase;
import com.example.sqlite.model.Mahasiswa;

import java.util.List;

/**
 * Created by David Sanjaya on 3/17/2020
 * Sqlite
 */
public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ListViewHolder> {

    private List<Mahasiswa> listMahasiswa;
    private Context context;
    private OnClickItemListener onClickItemListener;

    public MahasiswaAdapter(Context context, List<Mahasiswa> listMahasiswa, OnClickItemListener onClickItemListener) {
        this.context = context;
        this.listMahasiswa = listMahasiswa;
        this.onClickItemListener = onClickItemListener;
    }

    public void addItem(Mahasiswa mahasiswa) {
        this.listMahasiswa.add(mahasiswa);
        notifyItemInserted(listMahasiswa.size() - 1);
    }

    public void updateItem(int position, Mahasiswa mahasiswa) {
        this.listMahasiswa.set(position, mahasiswa);
        Log.e(Const.TAG, "updateItem: " + position);
        notifyItemChanged(position, mahasiswa);
    }

    public void removeData(int position) {
        this.listMahasiswa.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listMahasiswa.size());
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view, onClickItemListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.tvName.setText("Nama : " + listMahasiswa.get(position).getName());
        holder.tvNim.setText("NIM : " + listMahasiswa.get(position).getNim());
    }

    @Override
    public int getItemCount() {
        return listMahasiswa.size();
    }

    static class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName, tvNim;
        OnClickItemListener onClickItemListener;

        ListViewHolder(@NonNull View itemView, OnClickItemListener onClickItemListener) {
            super(itemView);
            this. onClickItemListener = onClickItemListener;
            itemView.setOnClickListener(this);

            tvName = itemView.findViewById(R.id.tv_name);
            tvNim = itemView.findViewById(R.id.tv_nim);
        }

        @Override
        public void onClick(View view) {
            onClickItemListener.onItemClicked(getAdapterPosition());
        }
    }

    public interface OnClickItemListener {
        void onItemClicked(int position);
    }
}
