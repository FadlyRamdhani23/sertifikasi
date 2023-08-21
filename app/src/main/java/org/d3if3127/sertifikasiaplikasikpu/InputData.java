package org.d3if3127.sertifikasiaplikasikpu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.d3if3127.sertifikasiaplikasikpu.helper.DbHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InputData extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    TextInputEditText text_id, text_nik, text_nama, text_no_hp, text_tanggal, text_alamat;

    Button btn_submit;

    ImageView imageView;

    RadioGroup radioGroup;

    String selectedId;
    RadioButton radio_laki, radio_perempuan;


    DbHelper SQLite = new DbHelper(this);

    String id, nik, nama, no_hp, jenis_kelamin, tanggal, alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        text_id = (TextInputEditText) findViewById(R.id.id_inp);
        text_nik = (TextInputEditText) findViewById(R.id.nik_inp);
        text_nama = (TextInputEditText) findViewById(R.id.nama_inp);
        text_no_hp = (TextInputEditText) findViewById(R.id.noTelepon_inp);
        radio_laki = (RadioButton) findViewById(R.id.priaRadioButton);
        radio_perempuan = (RadioButton) findViewById(R.id.wanitaRadioButton);
        text_tanggal = (TextInputEditText) findViewById(R.id.tanggal_inp);
        text_alamat = (TextInputEditText) findViewById(R.id.alamat_inp);
        imageView = (ImageView) findViewById(R.id.imageView2);
        btn_submit = (Button) findViewById(R.id.submit_btn);


        id = getIntent().getStringExtra(DataPemilihActivity.TAG_ID);
        nik = getIntent().getStringExtra(DataPemilihActivity.TAG_NIK);
        nama = getIntent().getStringExtra(DataPemilihActivity.TAG_NAMA);
        no_hp = getIntent().getStringExtra(DataPemilihActivity.TAG_NO_HP);
        jenis_kelamin = getIntent().getStringExtra(DataPemilihActivity.TAG_JENIS_KELAMIN);
        tanggal = getIntent().getStringExtra(DataPemilihActivity.TAG_TANGGAL);
        alamat = getIntent().getStringExtra(DataPemilihActivity.TAG_ALAMAT);

        if (id == null || id == ""){
            setTitle("Tambah Data");
        } else {
            setTitle("Edit Data");
            text_id.setText(id);
            text_nik.setText(nik);
            text_nama.setText(nama);
            text_no_hp.setText(no_hp);
            text_tanggal.setText(tanggal);
            text_alamat.setText(alamat);
            if (jenis_kelamin.equals("Pria")){
                radio_laki.setChecked(true);
            } else {
                radio_perempuan.setChecked(true);
            }
        }

        btn_submit.setOnClickListener(v -> {
            try {
                if (text_id.getText().toString().equals("")){
                    save();
                } else {
                    edit();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        text_tanggal.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        imageView.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                blank();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void blank(){
        text_nik.requestFocus();
        text_id.setText(null);
        text_nik.setText(null);
        text_nama.setText(null);
        text_no_hp.setText(null);
        radio_laki.setChecked(false);
        radio_perempuan.setChecked(false);
        text_tanggal.setText(null);
        text_alamat.setText(null);
    }

    private void save(){
        if (String.valueOf(text_nik.getText()).equals(null) || String.valueOf(text_nik.getText()).equals("") ||
                String.valueOf(text_nama.getText()).equals(null) || String.valueOf(text_nama.getText()).equals("") ||
                String.valueOf(text_no_hp.getText()).equals(null) || String.valueOf(text_no_hp.getText()).equals("") ||
                String.valueOf(text_tanggal.getText()).equals(null) || String.valueOf(text_tanggal.getText()).equals("") ||
                String.valueOf(text_alamat.getText()).equals(null) || String.valueOf(text_alamat.getText()).equals("")){
            Snackbar.make(btn_submit, "Data tidak boleh kosong", Snackbar.LENGTH_LONG).show();
        } else {
            if (radio_laki.isChecked()){
                jenis_kelamin = "Pria";
            } else {
                jenis_kelamin = "Wanita";
            }
            SQLite.insert(text_nik.getText().toString().trim(), text_nama.getText().toString().trim(), text_no_hp.getText().toString().trim(),
                    jenis_kelamin, text_tanggal.getText().toString().trim(), text_alamat.getText().toString().trim());
            blank();
            finish();
        }
    }

    private void edit(){
        if (String.valueOf(text_nik.getText()).equals(null) || String.valueOf(text_nik.getText()).equals("") ||
                String.valueOf(text_nama.getText()).equals(null) || String.valueOf(text_nama.getText()).equals("") ||
                String.valueOf(text_no_hp.getText()).equals(null) || String.valueOf(text_no_hp.getText()).equals("") ||
                String.valueOf(text_tanggal.getText()).equals(null) || String.valueOf(text_tanggal.getText()).equals("") ||
                String.valueOf(text_alamat.getText()).equals(null) || String.valueOf(text_alamat.getText()).equals("")){
            Snackbar.make(btn_submit, "Data tidak boleh kosong", Snackbar.LENGTH_LONG).show();
        } else {
            if (radio_laki.isChecked()){
                jenis_kelamin = "Pria";
            } else if (radio_perempuan.isChecked()){
                jenis_kelamin = "Wanita";
            }
            SQLite.update(Integer.parseInt(text_id.getText().toString().trim()), text_nik.getText().toString().trim(), text_nama.getText().toString().trim(),
                    text_no_hp.getText().toString().trim(), jenis_kelamin, text_tanggal.getText().toString().trim(), text_alamat.getText().toString().trim());
            blank();
            finish();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        text_tanggal.setText(simpleDateFormat.format(calendar.getTime()));
    }
}