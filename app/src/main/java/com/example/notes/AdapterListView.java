package com.example.notes;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AdapterListView extends ArrayAdapter<String> {
    private static final String TAG = "AdapterListView";

    Context context;
    int ressources_id;
    ArrayList<String> data;

    public AdapterListView(Context context, int ressources_id, ArrayList<String> data) {
        super(context, ressources_id, data);
        this.context = context;
        this.ressources_id = ressources_id;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == convertView) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(ressources_id, parent, false);
        }
        TextView txt = convertView.findViewById(R.id.sl_text);
        txt.setText(data.get(position).trim());
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
