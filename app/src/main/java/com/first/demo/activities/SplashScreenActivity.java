package com.first.demo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.first.demo.MainActivity;
import com.first.demo.R;
import com.first.demo.activities.ui.login.LoginActivity;
import com.first.demo.databinding.ActivitySplashScreenBinding;

import java.util.Timer;
import java.util.TimerTask;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashScreenActivity extends AppCompatActivity {

    private ActivitySplashScreenBinding binding;
    private FirebaseAuth myAuth;

    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);

        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        editTextEmail = binding.username;
//        editTextPassword = binding.password;
//
//        myAuth = FirebaseAuth.getInstance();

        setTimerForMainActivity();

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = myAuth.getCurrentUser();
//        if(currentUser != null){
//            //User is signed in
//            Toast.makeText(this, "Welcome to MedConect ", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void setTimerForMainActivity(){
        Timer timer = new Timer();
        TimerTask timerTask =  new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        };
        timer.schedule(timerTask,1000);
    }}

//    public void btnSignupClick(View view) {
//
//    }
//
//    public void btnLoginClick(View view) {
//
//    }
//}