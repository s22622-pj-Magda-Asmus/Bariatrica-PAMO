package com.example.bariatric_mobile.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bariatric_mobile.R;
import com.example.bariatric_mobile.fragments.DoctorDashboardFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new DoctorDashboardFragment())
                    .commit();
        }
    }
}
