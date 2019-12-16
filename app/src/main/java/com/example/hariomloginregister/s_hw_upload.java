package com.example.hariomloginregister;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import java.util.HashMap;
import java.util.Map;

public class s_hw_upload extends AppCompatActivity {
    //declaration
    Button ch,up;
    ImageView img;
    StorageReference mStorageRef;
    private StorageTask uploadTask;
    public Uri imgUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_hw_upload);
        //initialization
        mStorageRef= FirebaseStorage.getInstance().getReference("Homework");
        ch=(Button)findViewById(R.id.btnchoose);
        up=(Button)findViewById(R.id.btnupload);
        img=(ImageView) findViewById(R.id.imgview);

        //on click choose button
        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filechooser();

            }
        });

        //on click upload button
        up.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                fileUploader();
            }
        });

    }


    private String getExtention(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap =MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }

    private void fileUploader()
    {
        FirebaseAuth mAuth=FirebaseAuth.getInstance();    //creating authentication instance
        String uid=mAuth.getCurrentUser().getUid();       // getting uid of current user
        final StorageReference sRef=mStorageRef.child(uid+"."+getExtention(imgUri));  //getting link for image
        final FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();       //creating firestore instance

        uploadTask=sRef.putFile(imgUri)    //uploading image
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {    //getting download link for to forward to firestore
                                String downloadUrl = uri.toString();
                                Map<String, String> hw = new HashMap<>();     //creating hashmaps
                                hw.put("link", downloadUrl);                   // saving link to map
                                dbFirestore.collection("s_hw_submition").document("imgLink").set(hw)    //saving link of uploaded  image to firebase for teachers
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getApplicationContext(), "Image Uploaded SuccessFully", Toast.LENGTH_SHORT).show();  //toast
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Upload Failed", Toast.LENGTH_SHORT).show();   //toast
                                    }
                                });
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(s_hw_upload.this, "Not Successful ", Toast.LENGTH_SHORT).show();   //toast
                    }
                });
    }

    private void filechooser(){    //function to choose a file
        Intent imgIntent=new Intent();
        imgIntent.setType("image/");
        imgIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(imgIntent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imgUri=data.getData();
            img.setImageURI(imgUri);    //viewing image on imageView
        }
    }
}

