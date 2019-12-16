package com.example.hariomloginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class t_hw_post extends AppCompatActivity {

//declaration
    private Button postBtn, delBtn, checkHwBtn;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_hw_post);

        //initialization
        postBtn = (Button) findViewById(R.id.postbtn);
        text = (EditText) findViewById(R.id.tf);
        delBtn = (Button) findViewById(R.id.delBtn);
         checkHwBtn= (Button) findViewById(R.id.checkHwBtn);
        final FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
//on click of "post hw " button
        postBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String input=text.getText().toString().trim();   //getting hw from teachers
        Map<String, Object> hmw = new HashMap<>();
        hmw.put("hw", input);
        text.setText("");
        dbFirestore.collection("t_hw_post").document("hwPost").set(hmw)       //posting hmw on firebase
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Homework Posted on Database Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Please Try again", Toast.LENGTH_SHORT).show();
            }
        });
    }


});


//on click of "remove prev homework " button
delBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Map<String, String> clearData = new HashMap<>();  //creating blank map
        clearData.put("hw", " ");
        dbFirestore.collection("t_hw_post").document("hwPost").set(clearData) //deleting data
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Homework deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Task Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


});
//on click of " evaluate homework" button
        checkHwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(t_hw_post.this, t_hw_check.class);
                startActivity(intent);
            }
        });

    }
}