package com.kallolsarkar.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText email,password;
    Button registerbutton,loginbutton,logoutbutton;

    private FirebaseAuth mAuth;
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Toast.makeText(this, "Already In", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email =findViewById(R.id.email);
        password =findViewById(R.id.password);
        registerbutton=findViewById(R.id.registerbutton);
        loginbutton=findViewById(R.id.loginbutton);
        logoutbutton=findViewById(R.id.logoutbutton);

        mAuth = FirebaseAuth.getInstance();
    }
}
