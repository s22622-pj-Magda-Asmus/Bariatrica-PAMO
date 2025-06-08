package com.example.bariatric_mobile.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bariatric_mobile.R;
import com.example.bariatric_mobile.fragments.DoctorDashboardFragment;

/**
 * MainActivity serves as the entry point of the application.
 * <p>
 * This activity hosts the {@link DoctorDashboardFragment} inside a container view.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Called when the activity is starting. Sets the layout for the activity
     * and loads the initial fragment if this is the first creation.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down, this Bundle contains
     *                           the data it most recently supplied.
     */
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
