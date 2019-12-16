package com.example.hariomloginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Map;

public class s_hw_retrieve extends AppCompatActivity {

    //declaration
    TextView tvHmw,tfn;
    Button retBtn, tmpBtn, postBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_hw_retrieve);

//initialization
        final FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
        tfn=(TextView)findViewById(R.id.tfn);
        tvHmw=(TextView)findViewById(R.id.tvHmw);
        retBtn=(Button)findViewById(R.id.retBtn);
        tmpBtn=(Button)findViewById(R.id.tmpBtn);
        postBtn=(Button)findViewById(R.id.checkHwBtn);

//on click "retrieve homework"
        retBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tfn.setVisibility(View.VISIBLE);
                DocumentReference docReference = dbFirestore.collection("t_hw_post").document("hwPost");     //getting the reference of firebase firestore
                docReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot dSnapshot = task.getResult();     //saving reference result to a variable
                            if (dSnapshot.exists()) {
                                Map<String, Object> hwrk = dSnapshot.getData();   // saving retrieved data in a map
                                String name= (String) hwrk.get("hw");              //getting the value of the field "hw"
                                tvHmw.setText(name);
                            }
                        }
                    }
                });
            }
        });


//on click "Clear fields"
        tmpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvHmw.setText("");
                tfn.setVisibility(View.INVISIBLE);
            }
        });


//on click "post homework"
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(s_hw_retrieve.this, s_hw_upload.class);
                startActivity(intent);                                                              // switching to s_hw_upload activity
            }
        });
    }
}

