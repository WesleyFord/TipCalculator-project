package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    private Button resetBtn;
    private Button calcBtn;
    private RadioButton tenPercent;
    private RadioButton fifteenPercent;
    private RadioButton twentyPercent;
    private RadioButton customPercent;
    private EditText customTip;
    private EditText partySize;
    private EditText billTotal;
    private RadioGroup radioGroup;
    private Intent i;
    private boolean validBill = false, validParty = false, validTip = false;
    private int party;
    private double bill, tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetBtn = findViewById(R.id.resetBtn);
        calcBtn = findViewById(R.id.calculateBtn);
        tenPercent = findViewById(R.id.tenTipBtn);
        fifteenPercent = findViewById(R.id.fifteenTipBtn);
        twentyPercent = findViewById(R.id.twentyTipBtn);
        customPercent = findViewById(R.id.customTipBtn);
        customTip = findViewById(R.id.customTip);
        partySize = findViewById(R.id.numPeople);
        billTotal = findViewById(R.id.billCost);
        radioGroup = findViewById(R.id.radioGroup);

        calcBtn.setEnabled(false);

        billTotal.setOnKeyListener(mKeyListener);
        partySize.setOnKeyListener(mKeyListener);
        customTip.setOnKeyListener(mKeyListener);
        tenPercent.setOnClickListener(mClickListener);
        fifteenPercent.setOnClickListener(mClickListener);
        twentyPercent.setOnClickListener(mClickListener);
        customPercent.setOnClickListener(mClickListener);


        i = new Intent(this, SecondActivity.class);

        resetBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                radioGroup.clearCheck();
                billTotal.setText("");
                customTip.setText("");
                partySize.setText("");
            }
        });

        calcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data;

                party = Integer.parseInt(partySize.getText().toString());
                bill = Double.parseDouble(billTotal.getText().toString());

                if (tenPercent.isChecked()) {
                    tip = .1;
                }
                else if (fifteenPercent.isChecked()) {
                    tip = .15;
                }
                else if (twentyPercent.isChecked()) {
                    tip = .2;
                }
                else if (customPercent.isChecked()) {
                    tip = Double.parseDouble(customTip.getText().toString()) / 100;
                }

                data = new Bundle();
                data.putDouble("bill", bill);
                data.putDouble("tip", tip);
                data.putInt("party", party);

                i.putExtras(data);

                startActivity(i);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("bill", billTotal.getText().toString());
        outState.putString("tip", customTip.getText().toString());
        outState.putString("party", partySize.getText().toString());
        outState.putBoolean("tenTip", tenPercent.isChecked());
        outState.putBoolean("fifteenTip", fifteenPercent.isChecked());
        outState.putBoolean("twentyTip", twentyPercent.isChecked());
        outState.putBoolean("customTip", customPercent.isChecked());

    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        billTotal.setText(savedInstanceState.getString("bill"));
        customTip.setText(savedInstanceState.getString("tip"));
        partySize.setText(savedInstanceState.getString("party"));

        tenPercent.setChecked(savedInstanceState.getBoolean("tenTip"));
        fifteenPercent.setChecked(savedInstanceState.getBoolean("fifteenTip"));
        twentyPercent.setChecked(savedInstanceState.getBoolean("twentyTip"));
        customPercent.setChecked(savedInstanceState.getBoolean("customTip"));
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (customPercent.isChecked()) {
                calcBtn.setEnabled(validBill && validParty && validTip);
            }
            else if (tenPercent.isChecked() || fifteenPercent.isChecked() || twentyPercent.isChecked()) {
                calcBtn.setEnabled(validBill && validParty);
            }
        }
    };

    private View.OnKeyListener mKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            switch (v.getId()) {
                case R.id.billCost:
                    if (billTotal.getText().length() > 0 && Double.parseDouble(billTotal.getText().toString()) >= 1) {
                        validBill = true;
                    }
                    else {
                        validBill = false;
                    }
                    break;
                case R.id.numPeople:
                    if (partySize.getText().length() > 0 && Integer.parseInt(partySize.getText().toString()) >= 1) {
                        validParty = true;
                    }
                    else {
                        validParty = false;
                    }
                    break;
                case R.id.customTip:
                    if (customTip.getText().length() > 0 && Double.parseDouble(customTip.getText().toString()) >= 1) {
                        validTip = true;
                    }
                    else {
                        validTip = false;
                    }
                    break;
                case R.id.customTipBtn:

            }
            if (customPercent.isChecked()) {
                calcBtn.setEnabled(validBill && validParty && validTip);
            }
            else if (tenPercent.isChecked() || fifteenPercent.isChecked() || twentyPercent.isChecked()) {
                calcBtn.setEnabled(validBill && validParty);
            }

            return false;
        }

    };
}