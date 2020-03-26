package com.example.sqlite.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sqlite.R;
import com.example.sqlite.db.Const;
import com.example.sqlite.db.Dbase;
import com.example.sqlite.model.Mahasiswa;

import java.util.ArrayList;
import java.util.List;
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
    private int position;
    private int id;
    private String justName;
    private boolean isEdit = false;
    public static final int REQ_ADD = 101;
    public static final int REQ_UPDATE = 102;
    public static final int RESULT_DEL = 201;
    public static final String EXTRA_DATA = "extra data";
    public static final String EXTRA_POSITION = "extra_position";
    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_ITEM = "extra_id";
    public static final String JUST_NAME = "just_name";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_data);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Form Data");
        db = new Dbase(this);

        // get and check data in model mahasiswa
        mahasiswa = getIntent().getParcelableExtra(EXTRA_DATA);
        if (mahasiswa != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            id = getIntent().getIntExtra(EXTRA_ID, 0);
            justName = getIntent().getStringExtra(JUST_NAME);
            Log.e(Const.TAG, "onCreateID: " + id);
            isEdit = true;
        } else {
            mahasiswa = new Mahasiswa();
        }

        // condition of edit
        if (isEdit) {
            if (mahasiswa != null) {
                edtName.setText(mahasiswa.getName());
                int nim = mahasiswa.getNim();
                String fNim = String.valueOf(nim);
                edtNim.setText(fNim);
                btnAction.setText("Update");
            }
        } else {
            btnAction.setText("Add");
        }

        action();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit) {
            getMenuInflater().inflate(R.menu.option_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_delete) {
            showDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Delete");
        alertDialog.setMessage("Are you sure to delete " + justName + " ?").setPositiveButton("Yes", (dialogInterface, i) -> {
            db.open();
            boolean isResult = db.deleteData(id);
            Log.e(Const.TAG, "onClickID: " + id);
            Log.e(Const.TAG, "onClickSTATUS: " + isResult);
            if (isResult) {
                Toast.makeText(getApplicationContext(), "Success delete!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra(EXTRA_POSITION, position);
                setResult(RESULT_DEL, intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Failed delete!", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }).setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel());
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    private void action() {
        btnAction.setOnClickListener(view -> {
            db.open();
            String name = edtName.getText().toString();
            String nim = edtNim.getText().toString();
            mahasiswa.setName(name);
            mahasiswa.setNim(Integer.parseInt(nim));

            Intent intent = new Intent();
            intent.putExtra(EXTRA_POSITION, position);
            intent.putExtra(EXTRA_DATA, mahasiswa);

            if (isEdit) {
                boolean isResult = db.updateData(id, name, nim);
                Log.e(Const.TAG, "actionID: " + id);
                Log.e(Const.TAG, "actionUPDATEIS: " + isResult);
                setResult(RESULT_OK, intent);
                Log.e(Const.TAG, "action: UPDATE");
                finish();
            } else {
                if (TextUtils.isEmpty(name)) {
                    edtName.setError("Field is required!!!");
                } else if (TextUtils.isEmpty(nim)) {
                    edtNim.setError("Field is required!!!");
                } else {
                    long result = db.insertData(name, Integer.parseInt(nim));
                    Log.e(Const.TAG, "actionInsert: " + result);
                    if (result > 0)
                        Toast.makeText(getApplicationContext(), "Success added !", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "Failed added !", Toast.LENGTH_SHORT).show();

                    edtName.setText("");
                    edtNim.setText("");
                    setResult(RESULT_OK, intent);
                    Log.e(Const.TAG, "action: ADD");
                    finish();
                }
            }
            db.close();
        });
    }

}
