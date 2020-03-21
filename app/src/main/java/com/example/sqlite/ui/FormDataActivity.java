package com.example.sqlite.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
    public static final int REQ_ADD = 101;
    public static final String EXTRA_DATA = "extra data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Form Data");

        mahasiswa = getIntent().getParcelableExtra(EXTRA_DATA);
        if (mahasiswa == null) {
            mahasiswa = new Mahasiswa();
        }

        db = new Dbase(this);
        action();
    }

    private void action() {
        btnAction.setOnClickListener(view -> {
            Intent intent = new Intent();
            String name = edtName.getText().toString();
            String nim = edtNim.getText().toString();

            if (TextUtils.isEmpty(name)) {
                edtName.setError("Field is required!!!");
            } else if (TextUtils.isEmpty(nim)) {
                edtNim.setError("Field is required!!!");
            } else {
                mahasiswa.setName(name);
                mahasiswa.setNim(Integer.parseInt(nim));
                intent.putExtra(EXTRA_DATA, mahasiswa);

                db.open();
                long result = db.insertData(name, Integer.parseInt(nim));
                Log.e(Const.TAG, "actionInsert: " + result);
                if (result > 0)
                    Toast.makeText(getApplicationContext(), "Success added !", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Failed added !", Toast.LENGTH_SHORT).show();

                db.close();

                edtName.setText("");
                edtNim.setText("");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
