package com.example.bookxpert;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    TextView act_ID_details ,act_name_details,act_amount_details;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        act_ID_details = findViewById(R.id.act_ID_details);
        act_name_details = findViewById(R.id.act_name_details);
        act_amount_details = findViewById(R.id.act_amount_details);
        act_ID_details.setText(getIntent().getStringExtra("actID"));
        act_name_details.setText(getIntent().getStringExtra("actName"));
        act_amount_details.setText(getIntent().getStringExtra("actAmount"));


    }
}