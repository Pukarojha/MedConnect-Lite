package com.first.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.first.demo.DbHelperClasses.DatabaseHelper;
import com.first.demo.appointment.ViewAppointmentsFragment;
import com.first.demo.consultations.ConsultationsFragment;
import com.first.demo.databinding.ActivityMainBinding;
import com.first.demo.patient.ViewPatientsFragment;
import com.first.demo.activities.ui.login.LoginActivity;
//import com.first.demo.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    DatabaseHelper db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.topAppBar;
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        replaceFragment(new ViewPatientsFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.patients) {
                replaceFragment(new ViewPatientsFragment());
                return true;
            } else if (item.getItemId() == R.id.appointments) {
                replaceFragment(new ViewAppointmentsFragment());
                return true;
            } else if (item.getItemId() == R.id.consultations) {
                replaceFragment(new ConsultationsFragment());
                return true;
            } else if (item.getItemId() == R.id.medication) {
                replaceFragment(new MedicationFragment());
                return true;
            }
            return false;
        });

        db = DatabaseHelper.getInstance(getApplicationContext());

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Toast.makeText(this, "Welcome back, " + user.getEmail(), Toast.LENGTH_SHORT).show();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            mAuth.signOut();
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_notifications) {
            Toast.makeText(this, "You have no new notifications", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
