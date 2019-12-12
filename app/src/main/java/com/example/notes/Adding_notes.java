package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.Collections;

public class Adding_notes extends AppCompatActivity {
    private static final String TAG = "Adding_notes";
    EditText editText;
    int position = -1;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_notes);
        editText = findViewById(R.id.editText2);
        Collections.reverse(MainActivity.ar_notes);
        if(getValue() >= 0)
            editText.setText(MainActivity.ar_notes.get(getValue()));

    }

    private int getValue(){
        intent = getIntent();
        if (intent.getStringExtra("position") != null) {
            Toast.makeText(this, "" + intent.getStringExtra("position"), Toast.LENGTH_SHORT).show();
            String temp = intent.getStringExtra("position");
            position = Integer.valueOf(temp);
        }

        return position;
    }
    @Override
    public void onBackPressed() {
        String text = editText.getText().toString().trim();

        if (!TextUtils.isEmpty(text)) {
            SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
            if (position >= 0) {

                MainActivity.ar_notes.set(getValue(), text);
                MainActivity.adapter.notifyDataSetChanged();
                position = -1;
            } else {
                MainActivity.ar_notes.add(text);
                MainActivity.adapter.notifyDataSetChanged();
            }

            try {
                String serialize = ObjectSerializer.serialize(MainActivity.ar_notes);
                sharedPreferences.edit().putString("array", serialize).apply();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        startActivity(new Intent(Adding_notes.this, MainActivity.class));
        super.onBackPressed();
    }
}
