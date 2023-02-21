package com.example.jan31tues;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar tipbar;
    EditText input;
    EditText howmany;
    RadioGroup control;
    RadioButton yes;
    RadioButton no;
    TextView tipamount;
    TextView output;
    TextView tiptext;
    TextView per;
    Button settings_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tipbar = findViewById(R.id.tipbar);
        input = findViewById(R.id.input);
        control = findViewById(R.id.control);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        tipamount = findViewById(R.id.tipamount);
        tiptext = findViewById(R.id.tiptext);
        output = findViewById(R.id.output);
        per = findViewById(R.id.per);
        howmany = findViewById(R.id.howmany);
        settings_button = findViewById(R.id.settings_button);

        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Settings.class);
                //i.putExtra("color", backgroundColor);
                startActivity(i);
            }

        });

        tipbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tiptext.setText(i+"%");

                try {
                    double value = Double.parseDouble((input.getText().toString()));
                    double many = Double.parseDouble((howmany.getText().toString()));
                    double main = Double.parseDouble((input.getText().toString()));
                    if (yes.isChecked()) {
                        value = (value / many) * (i / 100.0);
                    } else if (no.isChecked()) {
                        value = (value) * (i / 100.0);
                    }
                    output.setText("$" + String.format("%.2f", (value + (main / many))));
                    tipamount.setText("$" + String.format("%.2f", (value)));
                } catch (NumberFormatException e){

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                try{
                if (i== EditorInfo.IME_ACTION_DONE){
                   double value = Double.parseDouble((input.getText().toString()));
                   double many = Double.parseDouble((howmany.getText().toString()));
                   double main = Double.parseDouble((input.getText().toString()));
                   if (yes.isChecked()) {
                       value = (value/many)*(tipbar.getProgress()/100.0);
                   } else if (no.isChecked()) {
                       value = ((value)*(tipbar.getProgress()/100.0));
                   }
                    output.setText("$"+String.format("%.2f", (value+(main/many))));
                    tipamount.setText("$"+String.format("%.2f", (value)));
               }
                } catch (NumberFormatException e){

                }
                return false;
            }
        });

        howmany.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                try{
                if (i== EditorInfo.IME_ACTION_DONE) {
                    double value = Double.parseDouble((input.getText().toString()));
                    double many = Double.parseDouble((howmany.getText().toString()));
                    double main = Double.parseDouble((input.getText().toString()));
                    if (yes.isChecked()) {
                        value = (value/many)*(tipbar.getProgress()/100.0);
                    } else if (no.isChecked()) {
                        value = ((value)*(tipbar.getProgress()/100.0));
                    }
                    output.setText("$"+String.format("%.2f", (value+(main/many))));
                    tipamount.setText("$"+String.format("%.2f", (value)));
                }
                } catch (NumberFormatException e){

                }
                return false;
            }
        });

        control.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                input.setText("");
                output.setText("");
                tipamount.setText("");
                if (i == R.id.yes) {
                    per.setText("Cost Per Check With Tip:");
                } else if (i == R.id.no) {
                    per.setText("Final Cost With Tip:");
                    howmany.setText(1+"");
                }
            }
        });
    }

    public void updateDefaults() {
        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        tipbar.setProgress(sp.getInt("defaultTip", 15));
        howmany.setText(sp.getInt("defaultSplit", 1)+"");
        if (sp.getBoolean("defaultSplitCheck", false) == true){
            yes.setChecked(true);
            no.setChecked(false);
        } else {
            yes.setChecked(false);
            no.setChecked(true);
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDefaults();
    }

}