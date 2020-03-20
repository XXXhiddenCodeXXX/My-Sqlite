package com.example.sqlite;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.adapter.MahasiswaAdapter;
import com.example.sqlite.db.Const;
import com.example.sqlite.db.Dbase;
import com.example.sqlite.model.Mahasiswa;
import com.example.sqlite.ui.FormDataActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rv_data_mhs)
    RecyclerView rvDataMhs;
    @BindView(R.id.tv_noData)
    TextView tvNoData;

    private List<Mahasiswa> listMahasiswa = new ArrayList<>();
    private Dbase dbase;
    private MahasiswaAdapter mahasiswaAdapter;
    private Mahasiswa mahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbase = new Dbase(this);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Data Mahasiswa");

        actionToForm();

        // set adapter
        mahasiswaAdapter = new MahasiswaAdapter(this, listMahasiswa);
        rvDataMhs.setLayoutManager(new LinearLayoutManager(this));
        rvDataMhs.setHasFixedSize(true);
        rvDataMhs.setAdapter(mahasiswaAdapter);
        mahasiswaAdapter.notifyDataSetChanged();
        retrieve();

    }

    private void actionToForm() {
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), FormDataActivity.class);
            startActivityForResult(intent, FormDataActivity.REQ_ADD);
        });
    }

    @SuppressLint("SetTextI18n")
    private void retrieve() {
        listMahasiswa.clear();
         dbase.open();

        Cursor cursor = dbase.getAllData();

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String nim = cursor.getString(2);

                mahasiswa = new Mahasiswa();
                mahasiswa.setId(id);
                mahasiswa.setName(name);
                mahasiswa.setNim(nim);
                listMahasiswa.add(mahasiswa);
                Log.e(Const.TAG, "id: " + id);

//                listMhs.add(id + name + nim);

            } while (cursor.moveToNext());
        }
        dbase.close();

        if (!(listMahasiswa.size() < 1)) {
            mahasiswaAdapter.notifyDataSetChanged();
            Log.e(Const.TAG, "size: " + listMahasiswa.size());
            tvNoData.setVisibility(View.GONE);
        } else  {
            tvNoData.setText("-No Data-");
            tvNoData.setVisibility(View.VISIBLE);
            Log.e(Const.TAG, "retrieve: no data");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == FormDataActivity.REQ_ADD) {
                if (resultCode == RESULT_OK) {
                    Mahasiswa mahasiswa = data.getParcelableExtra(FormDataActivity.EXTRA_DATA);
                    mahasiswaAdapter.addItem(mahasiswa);
                    rvDataMhs.smoothScrollToPosition(mahasiswaAdapter.getItemCount() - 1);
                    tvNoData.setVisibility(View.GONE);
                }
            }
        }
    }
}
