package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    static ArrayList<String> ar_notes;
    static AdapterListView adapter;
    static SharedPreferences sharedPreferences;

    ListView listView;
    Intent intent;
    int index = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.item_add_notes) {
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);


        ar_notes = new ArrayList<>();
        String text = sharedPreferences.getString("array", "");
        if (!TextUtils.isEmpty(text)) {
            try {
                ar_notes = (ArrayList<String>) ObjectSerializer.deserialize(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (ar_notes.size() == 0)
            ar_notes.add("Examples");

        final AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to delete this item?")
                .setMessage("if you click on yes the items will not be accessible here anymore...")
                .setIcon(R.drawable.ic_delete_black_24dp)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ar_notes.remove(index);
                        try {
                            String serialize = ObjectSerializer.serialize(ar_notes);
                            sharedPreferences.edit().putString("array", serialize).apply();
                            adapter.notifyDataSetChanged();
                            index = 0;
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("No", null);

        intent = new Intent(this, Adding_notes.class);
        listView = findViewById(R.id.listview);
        Collections.reverse(ar_notes);
        adapter = new AdapterListView(this, R.layout.simple_list, ar_notes);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                alert.show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, Adding_notes.class);
                int pos = ar_notes.size() - (i + 1);
                Log.d(TAG, "onItemClick: i = " + i + "pos = " + pos);
                intent.putExtra("position", pos + "");
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }
}
