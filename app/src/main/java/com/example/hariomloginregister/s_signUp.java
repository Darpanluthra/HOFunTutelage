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

public class s_signUp extends AppCompatActivity {


//declaration
    EditText remail,rpswd;
    Button rSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //initialization
        mFirebaseAuth = FirebaseAuth.getInstance();
        remail = findViewById(R.id.tfemail);
        rpswd = findViewById(R.id.tfpswd);
        rSignUp = findViewById(R.id.retBtn);
        tvSignIn = findViewById(R.id.tvSignIn);

        //on click of "Signup " button
        rSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String varEmail = remail.getText().toString();
                String varPassword = rpswd.getText().toString();
                //check if any field is left blank
                if (varEmail.isEmpty()) {
                    remail.setError("Email not Entered");
                    remail.requestFocus();
                } else if (varPassword.isEmpty()) {
                    rpswd.setError("Password not Entered");
                    rpswd.requestFocus();
                } else if (varEmail.isEmpty() && varPassword.isEmpty()) {
                    remail.setError("Email not Entered");
                    rpswd.setError("Password not Entered");
                    Toast.makeText(s_signUp.this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                } else if (!(varEmail.isEmpty() && varPassword.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(varEmail, varPassword).addOnCompleteListener(s_signUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {      //creating user using using email and password
                            if (task.isSuccessful()) {
                                Toast.makeText(s_signUp.this, "New User Registered", Toast.LENGTH_SHORT).show(); //toast
                                startActivity(new Intent(s_signUp.this, s_login.class));
                            } else if (!task.isSuccessful())
                                Toast.makeText(s_signUp.this, "Registration Failed", Toast.LENGTH_SHORT).show(); //toast
                        }
                    });
                }
            }
        });
//on click of "SignIn " tV
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(s_signUp.this, s_login.class));
            }
        });
    }
}
