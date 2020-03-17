package com.example.sqlite.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sqlite.R;
import com.example.sqlite.db.Const;
import com.example.sqlite.db.Dbase;
import com.example.sqlite.model.Mahasiswa;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FormDataActivity extends AppCompatActivity {

    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_nim)
    EditText edtNim;
    @BindView(R.id.btn_action)
    Button btnAction;

    private Dbase db;
    private Mahasiswa mahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        db = new Dbase(this);
        action();
    }

    private void action() {
        btnAction.setOnClickListener(view -> {
            String name = edtName.getText().toString();
            String nim = edtNim.getText().toString();
            mahasiswa = new Mahasiswa();
            mahasiswa.setName(name);
            mahasiswa.setNim(nim);

            db.open();
            long result = db.insertData(name, nim);
            if (result > 0)
                Toast.makeText(getApplicationContext(), "Success added!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "Failed added!", Toast.LENGTH_SHORT).show();

            db.close();

            edtName.setText("");
            edtNim.setText("");
        });
    }

}
