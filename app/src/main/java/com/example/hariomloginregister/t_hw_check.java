package com.example.hariomloginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.util.Map;

public class t_hw_check extends AppCompatActivity {

//declaration
   Button chkHwBtn;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_hw_check);
        //initialization
        chkHwBtn = findViewById(R.id.chkHwBtn);
        iv = findViewById(R.id.iv);
        final FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();

        //on click of " evaluate hw" button
        chkHwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference docReference = dbFirestore.collection("s_hw_submition").document("imgLink");     //getting the reference of firebase firestore
                docReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot dSnapshot = task.getResult();
                            if (dSnapshot.exists()) {
                                Map<String, Object> hmw = dSnapshot.getData();   //creating a  map of data
                                String urll = (String) hmw.get("link");
                                Picasso.get().load(urll).placeholder(R.drawable.loading).fit().into(iv); // using picasso to load images
                            }
                        }
                    }
                });
            }
        });
    }
}
