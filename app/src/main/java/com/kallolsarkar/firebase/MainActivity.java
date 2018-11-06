package com.kallolsarkar.firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button registerbutton, loginbutton, logoutbutton;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Toast.makeText(this, "Already In" + currentUser, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerbutton = findViewById(R.id.registerbutton);
        loginbutton = findViewById(R.id.loginbutton);
        logoutbutton = findViewById(R.id.logoutbutton);

        mAuth = FirebaseAuth.getInstance();
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String myEmail = email.getText().toString();
                 String myPass = password.getText().toString();

                if (TextUtils.isEmpty(myEmail) || TextUtils.isEmpty(myPass)) {
                    Toast.makeText(MainActivity.this, "Please provide valid email and password", Toast.LENGTH_SHORT).show();

                }
                else if(myPass.length()<=6){
                    Toast.makeText(MainActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                }

                else {
                    mAuth.createUserWithEmailAndPassword(myEmail, myPass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.i("TAG", "createUserWithEmail:success");
                                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

                                    }
                                    else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(MainActivity.this, "This email ID already registered", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });


    }
}