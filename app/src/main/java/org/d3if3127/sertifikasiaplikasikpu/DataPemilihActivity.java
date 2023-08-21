package org.d3if3127.sertifikasiaplikasikpu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.d3if3127.sertifikasiaplikasikpu.adapter.Adapter;
import org.d3if3127.sertifikasiaplikasikpu.helper.DbHelper;
import org.d3if3127.sertifikasiaplikasikpu.model.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataPemilihActivity extends AppCompatActivity {

    ListView listView;
    AlertDialog.Builder dialog;
    List<Data> itemList = new ArrayList<Data>();
    Adapter adapter;
    DbHelper SQLite = new DbHelper(this);
    public static final String TAG_ID = "id";
    public static final String TAG_NIK = "nik";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_NO_HP = "no_hp";
    public static final String TAG_JENIS_KELAMIN = "jenis_kelamin";
    public static final String TAG_TANGGAL = "tanggal";
    public static final String TAG_ALAMAT = "alamat";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pemilih);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SQLite = new DbHelper(getApplicationContext());

        listView = (ListView) findViewById(R.id.list_View);

        adapter = new Adapter(DataPemilihActivity.this, itemList);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final String idx = itemList.get(position).getId();
                final String nik = itemList.get(position).getNik();
                final String nama = itemList.get(position).getNama();
                final String no_hp = itemList.get(position).getNo_hp();
                final String jenis_kelamin = itemList.get(position).getJenis_kelamin();
                final String tanggal = itemList.get(position).getTanggal();
                final String alamat = itemList.get(position).getAlamat();

                final CharSequence[] dialogitem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(DataPemilihActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:

                                Intent intent = new Intent(DataPemilihActivity.this, InputData.class);
                                intent.putExtra(TAG_ID, idx);
                                intent.putExtra(TAG_NIK, nik);
                                intent.putExtra(TAG_NAMA, nama);
                                intent.putExtra(TAG_NO_HP, no_hp);
                                intent.putExtra(TAG_JENIS_KELAMIN, jenis_kelamin);
                                intent.putExtra(TAG_TANGGAL, tanggal);
                                intent.putExtra(TAG_ALAMAT, alamat);
                                startActivity(intent);
                                break;
                            case 1:

                                SQLite.delete(Integer.parseInt(idx));
                                itemList.clear();
                                getAllData();
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });
        getAllData();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void getAllData() {
        ArrayList<HashMap<String, String>> row = SQLite.getAllData();
        for (int i = 0; i < row.size(); i++) {
            String id = row.get(i).get(TAG_ID);
            String nik = row.get(i).get(TAG_NIK);
            String nama = row.get(i).get(TAG_NAMA);
            String no_hp = row.get(i).get(TAG_NO_HP);
            String jenis_kelamin = row.get(i).get(TAG_JENIS_KELAMIN);
            String tanggal = row.get(i).get(TAG_TANGGAL);
            String alamat = row.get(i).get(TAG_ALAMAT);


            Data data = new Data();
            data.setId(id);
            data.setNik(nik);
            data.setNama(nama);
            data.setNo_hp(no_hp);
            data.setJenis_kelamin(jenis_kelamin);
            data.setTanggal(tanggal);
            data.setAlamat(alamat);

            itemList.add(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemList.clear();
        getAllData();
    }
}