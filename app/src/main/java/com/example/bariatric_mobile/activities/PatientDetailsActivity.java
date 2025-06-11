package com.example.bariatric_mobile.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bariatric_mobile.R;

public class PatientDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        String code = getIntent().getStringExtra("patient_code");
        String date = getIntent().getStringExtra("submission_date");
        String status = getIntent().getStringExtra("status");

        ((TextView) findViewById(R.id.details_code)).setText(code);
        ((TextView) findViewById(R.id.details_date)).setText(date);
//        ((TextView) findViewById(R.id.details_status)).setText(status);
        findViewById(R.id.back_to_list).setOnClickListener(v -> {
            finish();
        });
    }
}
