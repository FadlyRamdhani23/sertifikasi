package org.d3if3127.sertifikasiaplikasikpu.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;
import com.bumptech.glide.Glide;
import org.d3if3127.sertifikasiaplikasikpu.R;
import org.d3if3127.sertifikasiaplikasikpu.model.Data;

import java.util.List;

public class Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Data> items;

    public Adapter(Activity activity, List<Data> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView nik = (TextView) convertView.findViewById(R.id.nikList);
        TextView nama = (TextView) convertView.findViewById(R.id.namaList);
        TextView no_hp = (TextView) convertView.findViewById(R.id.noHandphoneList);
        TextView jenis_kelamin = (TextView) convertView.findViewById(R.id.jkList);
        TextView tanggal = (TextView) convertView.findViewById(R.id.tanggalList);
        TextView alamat = (TextView) convertView.findViewById(R.id.alamatList);

        Data data = items.get(position);

        id.setText(data.getId());
        nik.setText("NIK : " + data.getNik());
        nama.setText("NAMA : " + data.getNama());
        no_hp.setText("NO TELEPON : " + data.getNo_hp());
        jenis_kelamin.setText("JENIS KELAMIN : " +data.getJenis_kelamin());
        tanggal.setText("TANGGAL : " +data.getTanggal());
        alamat.setText("ALAMAT : " + data.getAlamat());

        return convertView;
    }

}
