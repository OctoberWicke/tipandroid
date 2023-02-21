package com.example.jan31tues;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Settings extends AppCompatActivity {

    Button back;
    EditText defaultTip;
    EditText defaultSplit;
    CheckBox splitBill;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        back = findViewById(R.id.back);
        splitBill = (findViewById(R.id.splitBill));
        defaultTip = findViewById(R.id.defaultTip);
        defaultSplit = findViewById(R.id.defaultSplit);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void onPause(){
        super.onPause();
        updateSharedPreferences();
    }

    private void updateSharedPreferences(){
        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("defaultTip", Integer.parseInt(defaultTip.getText().toString()));
        editor.putInt("defaultSplit", Integer.parseInt(defaultSplit.getText().toString()));
        editor.putBoolean("defaultSplitCheck", splitBill.isChecked());
        editor.commit();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateDefaults();
    }

    public void updateDefaults() {
        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        defaultTip.setText(sp.getInt("defaultTip", 15)+"");
        defaultSplit.setText(sp.getInt("defaultSplit", 1)+"");
        if (sp.getBoolean("defaultSplitCheck", false) == true){
            splitBill.setChecked(true);
        } else {
            splitBill.setChecked(false);
        };
    }


}