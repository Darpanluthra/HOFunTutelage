package com.example.hariomloginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class s_login extends AppCompatActivity {

//declaration
    EditText remail,rpswd;
    Button rSignIn;
    TextView tvSingnUp;
    FirebaseAuth mFirebaseAuth;

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
      //initialization
      mFirebaseAuth = FirebaseAuth.getInstance();
        remail = findViewById(R.id.tfemail);
        rpswd = findViewById(R.id.tfpswd);
        rSignIn = findViewById(R.id.retBtn);
        tvSingnUp = findViewById(R.id.tvSignUp);

      //on click of "SignIn " button
        rSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String varEmail = remail.getText().toString();
                String varPassword = rpswd.getText().toString();
                if (varEmail.isEmpty()) {                                  //check if any field is left blank
                    remail.setError("Email not Entered");
                    remail.requestFocus();
                } else if (varPassword.isEmpty()) {
                    rpswd.setError("Password not Entered");
                    rpswd.requestFocus();
                } else if (varEmail.isEmpty() && varPassword.isEmpty()) {
                    remail.setError("Email not Entered");
                    rpswd.setError("Password not Entered");
                    Toast.makeText(s_login.this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                } else if (!(varEmail.isEmpty() && varPassword.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(varEmail, varPassword).addOnCompleteListener(s_login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {  //signin using email and password
                            if (task.isSuccessful()) {
                                Toast.makeText(s_login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(s_login.this, s_hw_retrieve.class);    //switching activity
                                startActivity(intent);
                            } else {
                                Toast.makeText(s_login.this, "Login Error, Incorrect ID/Password", Toast.LENGTH_SHORT).show();
                                remail.setText("");
                                rpswd.setText("");    //clezring fields
                                remail.requestFocus();
                            }
                        }
                    });
                }
            }
        });

//on click of "SignUp" Tv
        tvSingnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(s_login.this, s_signUp.class));
            }
        });
    }
}


