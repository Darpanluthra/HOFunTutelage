package com.example.hariomloginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IstPage extends AppCompatActivity {
    Button teacher,student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
//initialization
        teacher=findViewById(R.id.btnTeacher);
        student=findViewById(R.id.btnStudent);

//on click of teacher's button
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IstPage.this, t_login.class));
            }
        });

//on click of Student's button
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IstPage.this, s_login.class));
            }
        });
    }
}
