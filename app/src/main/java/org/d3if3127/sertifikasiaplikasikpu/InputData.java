package org.d3if3127.sertifikasiaplikasikpu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InputData extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int PICK_IMAGE_REQUEST = 1;
    DbHelper SQLite = new DbHelper(this);
    TextInputEditText text_id, text_nik, text_nama, text_no_hp, text_tanggal, text_alamat;
    Button btn_submit;
    RadioButton radio_laki, radio_perempuan;
    ImageView imageView, gambarList;
    byte[] gambar;
    String id, nik, nama, no_hp, jenis_kelamin, tanggal, alamat;
    Bitmap selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //inisialisasi tampilan
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
        gambarList = (ImageView) findViewById(R.id.iamgeView3);
        btn_submit = (Button) findViewById(R.id.submit_btn);


        // Mendapatkan data yang mungkin dikirim dari activity sebelumnya
        id = getIntent().getStringExtra(DataPemilihActivity.TAG_ID);
        nik = getIntent().getStringExtra(DataPemilihActivity.TAG_NIK);
        nama = getIntent().getStringExtra(DataPemilihActivity.TAG_NAMA);
        no_hp = getIntent().getStringExtra(DataPemilihActivity.TAG_NO_HP);
        jenis_kelamin = getIntent().getStringExtra(DataPemilihActivity.TAG_JENIS_KELAMIN);
        tanggal = getIntent().getStringExtra(DataPemilihActivity.TAG_TANGGAL);
        alamat = getIntent().getStringExtra(DataPemilihActivity.TAG_ALAMAT);
        gambar = getIntent().getByteArrayExtra(DataPemilihActivity.TAG_GAMBAR);

        // pemilihan gambar
        gambarList.setOnClickListener(v ->{
            // Buat dialog untuk memilih sumber gambar (kamera atau galeri)
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Pilih Sumber Gambar");
            builder.setItems(new CharSequence[]{"Kamera", "Galeri"}, (dialog, which) -> {
                if (which == 0) {
                    // Pilihan kamera
                    if (checkCameraPermission()) {
                        // Izin kamera telah diberikan, lanjutkan untuk mengambil gambar dari kamera
                        dispatchTakePictureIntent();
                    }
                } else {
                    // Pilihan galeri
                    chooseImageFromGallery();
                }
            });
            builder.show();
        });

        //jika mode tambah data
        if (id == null || id == ""){
            setTitle("Tambah Data");
        } else {
            //jika mode edit data
            setTitle("Edit Data");
            text_id.setText(id);
            text_nik.setText(nik);
            text_nama.setText(nama);
            text_no_hp.setText(no_hp);
            text_tanggal.setText(tanggal);
            text_alamat.setText(alamat);
            gambarList.setImageBitmap(BitmapFactory.decodeByteArray(gambar, 0, gambar.length));

            if (jenis_kelamin.equals("Pria")){
                radio_laki.setChecked(true);
            } else {
                radio_perempuan.setChecked(true);
            }
        }
        // tombol submit
        btn_submit.setOnClickListener(v -> {
            try {
                if (text_id.getText().toString().equals("")){
                    //jika ID kosong
                    save();
                } else {
                    //jika ID ada isinya
                    edit();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        // tombol tanggal
        text_tanggal.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        // tombol gambar tanggal
        imageView.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
    }

    //metode untuk memicu pengambilan gambar dari kamera
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // pemilihan gambar dari galery
    private void chooseImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    //tombol back
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

    // metode ketika mengosongkan halaman
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
        gambarList.setImageResource(R.drawable.ic_launcher_background);
    }

    //metode untuk menyimpan data
    private void save(){
        if (String.valueOf(text_nik.getText()).equals(null) || String.valueOf(text_nik.getText()).equals("") ||
                String.valueOf(text_nama.getText()).equals(null) || String.valueOf(text_nama.getText()).equals("") ||
                String.valueOf(text_no_hp.getText()).equals(null) || String.valueOf(text_no_hp.getText()).equals("") ||
                String.valueOf(text_tanggal.getText()).equals(null) || String.valueOf(text_tanggal.getText()).equals("") ||
                String.valueOf(text_alamat.getText()).equals(null) || String.valueOf(text_alamat.getText()).equals("")){
            //menampilkan pesan jika ada data kosong
            Snackbar.make(btn_submit, "Data tidak boleh kosong", Snackbar.LENGTH_LONG).show();
        } else {
            if (radio_laki.isChecked()){
                jenis_kelamin = "Pria";
            } else {
                jenis_kelamin = "Wanita";
            }

            byte [] gambarbyteArray = null;
            if (selectedImage != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                gambarbyteArray = byteArrayOutputStream.toByteArray();
            } else {
                //menampilkan pesan jika gambar tidak dipilih
                Snackbar.make(btn_submit, "Pilih gambar terlebih dahulu", Snackbar.LENGTH_LONG).show();
                return; // Tidak lanjut ke proses penyimpanan jika gambar tidak dipilih.
            }
            //masukkan ke dalam database SQLite
            SQLite.insert(text_nik.getText().toString().trim(), text_nama.getText().toString().trim(), text_no_hp.getText().toString().trim(),
                    jenis_kelamin, text_tanggal.getText().toString().trim(), text_alamat.getText().toString().trim(), gambarbyteArray);
            blank();
            finish();
        }
    }


    //metode untuk mengedit data
    private void edit(){
        if (String.valueOf(text_nik.getText()).equals(null) || String.valueOf(text_nik.getText()).equals("") ||
                String.valueOf(text_nama.getText()).equals(null) || String.valueOf(text_nama.getText()).equals("") ||
                String.valueOf(text_no_hp.getText()).equals(null) || String.valueOf(text_no_hp.getText()).equals("") ||
                String.valueOf(text_tanggal.getText()).equals(null) || String.valueOf(text_tanggal.getText()).equals("") ||
                String.valueOf(text_alamat.getText()).equals(null) || String.valueOf(text_alamat.getText()).equals("")){
            //menampilkan pesan jika ada data kosong
            Snackbar.make(btn_submit, "Data tidak boleh kosong", Snackbar.LENGTH_LONG).show();
        } else {
            if (radio_laki.isChecked()){
                jenis_kelamin = "Pria";
            } else if (radio_perempuan.isChecked()){
                jenis_kelamin = "Wanita";
            }
            byte[] imageByteArray = null;
            if (selectedImage != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                imageByteArray = byteArrayOutputStream.toByteArray();
            }
            //masukkan ke dalam database SQLite
            SQLite.update(Integer.parseInt(text_id.getText().toString().trim()), text_nik.getText().toString().trim(), text_nama.getText().toString().trim(),
                    text_no_hp.getText().toString().trim(), jenis_kelamin, text_tanggal.getText().toString().trim(), text_alamat.getText().toString().trim(), imageByteArray);
            blank();
            finish();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
        // Membuat objek Calendar untuk mengatur tanggal yang dipilih
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        // Menggunakan SimpleDateFormat untuk memformat tanggal yang dipilih ke dalam format "yyyy-MM-dd"
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        // Menampilkan tanggal yang dipilih ke dalam TextInputEditText
        text_tanggal.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //jika gambar diambil dari kamera
            Bundle extras = data.getExtras();
            selectedImage = (Bitmap) extras.get("data");
            gambarList.setImageBitmap(selectedImage);
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Gambar dari galeri
            Uri imageUri = data.getData();
            try {
                // Mengambil gambar dari URI yang dipilih oleh pengguna
                selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                gambarList.setImageBitmap(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin kamera diberikan oleh pengguna, lanjutkan untuk mengambil gambar dari kamera
                dispatchTakePictureIntent();
            } else {
                // Izin kamera tidak diberikan oleh pengguna, berikan pesan kepada pengguna
                Snackbar.make(btn_submit, "Izin kamera diperlukan untuk mengambil gambar.", Snackbar.LENGTH_LONG).show();
            }
        }
    }
    //Memeriksa izin kamera dan meminta izin jika belum diberikan.
    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            return false;
        }
        return true;
    }


}