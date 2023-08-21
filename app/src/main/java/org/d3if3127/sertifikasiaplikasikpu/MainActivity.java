package org.d3if3127.sertifikasiaplikasikpu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import org.d3if3127.sertifikasiaplikasikpu.adapter.Adapter;
import org.d3if3127.sertifikasiaplikasikpu.helper.DbHelper;
import org.d3if3127.sertifikasiaplikasikpu.model.Data;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DbHelper SQLite = new DbHelper(this);

    Adapter adapter;

    AlertDialog.Builder dialog;
    ImageView btnForm, btnDataPemilih, btnInformasi, btnKeluar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLite = new DbHelper(getApplicationContext());
        btnInformasi = findViewById(R.id.btnInformasi);
        btnForm = findViewById(R.id.btnFormEntry);
        btnKeluar = findViewById(R.id.btnKeluar);
        btnDataPemilih = findViewById(R.id.btnLihatData);
        btnInformasi.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InformasiActivity.class);
            startActivity(intent);
        });
        btnDataPemilih.setOnClickListener(v -> {
            if (SQLite.cekData() == true){
                Intent intent = new Intent(MainActivity.this, DataPemilihActivity.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(MainActivity.this, InputData.class);
                startActivity(intent);
            }
        });


        btnForm.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InputData.class);
            startActivity(intent);
        });
        btnKeluar.setOnClickListener(v -> {
            finishAffinity();
        });

    }

}