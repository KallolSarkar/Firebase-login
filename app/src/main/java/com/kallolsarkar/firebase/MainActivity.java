package com.kallolsarkar.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
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
    SignInButton button;
    private final static int RC_SIGN_IN = 2;

    GoogleApiClient mGoogleApiClient;
    FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        Toast.makeText(this, "Already In" + currentUser, Toast.LENGTH_SHORT).show();
        mAuth.addAuthStateListener(mAuthListener);
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
        button =findViewById(R.id.googleBtn);

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
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myEmail = email.getText().toString();
                String myPass = password.getText().toString();
                if (TextUtils.isEmpty(myEmail) || TextUtils.isEmpty(myPass)) {
                    Toast.makeText(MainActivity.this, "Please provide valid email and password", Toast.LENGTH_SHORT).show();

                } else if (myPass.length() <= 6) {
                    Toast.makeText(MainActivity.this, "Password not matched", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(myEmail, myPass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, toast with the signed-in user's information
                                        Log.i("Tag", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        String userID = user.getUid().toString();
                                        Toast.makeText(MainActivity.this, "Auth Success " + user, Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If sign in fails
                                        Log.i("Tag", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Login Failed ,Please recheck your email and password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Sign out successful", Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(MainActivity.this, Logged_In_Activity.class));
                }

            }
        };
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this, "Somthing went wrong", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}