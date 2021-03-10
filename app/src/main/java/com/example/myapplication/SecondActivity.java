package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class SecondActivity extends AppCompatActivity {
    private TextView justBill;
    private TextView justTip;
    private TextView billWithTip;
    private TextView pricePerPerson;
    private Button returnButton;

    private int party;
    private double bill;
    private double tip;
    private double tipCost;
    private double totalWithTip;
    private double costPerPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        party = getIntent().getExtras().getInt("party");
        bill = getIntent().getExtras().getDouble("bill");
        tip = getIntent().getExtras().getDouble("tip");
        tipCost = tip * bill;
        totalWithTip = bill + tipCost;
        costPerPerson = totalWithTip / party;

        Intent i = new Intent(this, MainActivity.class);

        justBill = findViewById(R.id.originalTotal);
        justTip = findViewById(R.id.totalTip);
        billWithTip = findViewById(R.id.totalWithTip);
        pricePerPerson = findViewById(R.id.perPerson);

        returnButton = findViewById(R.id.returnBtn);

        justBill.setText(String.format("$%.2f", bill));
        justTip.setText(String.format("$%.2f", tipCost));
        billWithTip.setText(String.format("$%.2f", totalWithTip));
        pricePerPerson.setText(String.format("$%.2f", costPerPerson));

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

    }
    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putDouble("bill", bill);
        outState.putDouble("party", party);
        outState.putDouble("totalWithTip", tip);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        bill = savedInstanceState.getInt("bill");
        party = savedInstanceState.getInt("party");
        tip = savedInstanceState.getInt("tip");
    }
}