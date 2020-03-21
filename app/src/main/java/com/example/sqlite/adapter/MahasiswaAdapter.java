package com.example.sqlite.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.R;
import com.example.sqlite.model.Mahasiswa;

import java.util.List;

/**
 * Created by David Sanjaya on 3/17/2020
 * Sqlite
 */
public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ListViewHolder> {

    private List<Mahasiswa> listMahasiswa;
    private Context context;

    public MahasiswaAdapter(Context context, List<Mahasiswa> listMahasiswa) {
        this.context = context;
        this.listMahasiswa = listMahasiswa;
    }

    public void addItem(Mahasiswa mahasiswa) {
        this.listMahasiswa.add(mahasiswa);
        notifyItemInserted(listMahasiswa.size() - 1);
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.tvName.setText("Nama : " + listMahasiswa.get(position).getName());
        holder.tvNim.setText("NIM : " + listMahasiswa.get(position).getNim());
        holder.tvOption.setOnClickListener(view -> {
            // Create popup menu
            PopupMenu popupMenu = new PopupMenu(context, holder.tvOption);
            // inflate menu from xml res
            popupMenu.inflate(R.menu.option_menu);
            // add click listener
            popupMenu.setOnMenuItemClickListener(menuItem -> {

                switch (menuItem.getItemId()) {
                    case R.id.menu_edit :
                        Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_delete :
                        Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            });
            // display the popup menu
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return listMahasiswa.size();
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvNim, tvOption;

        ListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvNim = itemView.findViewById(R.id.tv_nim);
            tvOption = itemView.findViewById(R.id.tv_option);
        }
    }
}
