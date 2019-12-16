package com.example.hariomloginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class t_login extends AppCompatActivity {

//declaration
    EditText remail, rpswd;
    Button loginBtn;
    TextView tvSingnUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_login);
        //initialization
        final FirebaseAuth mFirebaseAuth;
        mFirebaseAuth = FirebaseAuth.getInstance();
        remail = findViewById(R.id.tfemail);
        rpswd = findViewById(R.id.tfpswd);
        loginBtn = findViewById(R.id.loginBtn);
        tvSingnUp = findViewById(R.id.tvSignUp);

        loginBtn.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(t_login.this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                } else if (!(varEmail.isEmpty() && varPassword.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(varEmail, varPassword).addOnCompleteListener(t_login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {      //signin using email and password
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(t_login.this, t_hw_post.class);
                                startActivity(intent);                                                                      //switching activity
                                Toast.makeText(t_login.this, "Login Successful", Toast.LENGTH_SHORT).show(); //toast
                            } else {
                                Toast.makeText(t_login.this, "Login Failed", Toast.LENGTH_SHORT).show();  //toast
                            }
                        }
                    });
                }
            }
        });
//on click of " signUP" tv
        tvSingnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(t_login.this, t_signUp.class));
            }
        });
    }
}