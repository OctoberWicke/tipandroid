package com.example.jan31tues;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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

        tipbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tiptext.setText(i+"%");
                double value = Double.parseDouble((input.getText().toString()));
                double many = Double.parseDouble((howmany.getText().toString()));
                double main = Double.parseDouble((input.getText().toString()));
                if (yes.isChecked()) {
                    value = (value/many)*(i/100.0);
                } else if (no.isChecked()) {
                    value = (value)*(i/100.0);
                }
                output.setText("$"+String.format("%.2f", (value+(main/many))));
                tipamount.setText("$"+String.format("%.2f", (value)));
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
                return false;
            }
        });

        howmany.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
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
                    howmany.setVisibility(View.VISIBLE);
                    howmany.setText("");
                    per.setText("Cost Per Check With Tip:");
                } else if (i == R.id.no) {
                    howmany.setVisibility(View.GONE);
                    howmany.setText("1.0");
                    per.setText("Final Cost With Tip:");
                }
            }
        });
    }
}