package com.xasanboyevdiyorbek.bmi_tatu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.xasanboyevdiyorbek.bmi_tatu.R;
import com.xasanboyevdiyorbek.bmi_tatu.User_info;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

public class MakeTamirlashOrder extends AppCompatActivity {

    Button orderbtn;
    TextInputEditText product, addition;
    TextView imgnametxt;
    ImageView uploadimg;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bmi-tatu-default-rtdb.firebaseio.com/");
//    StorageReference storageReference;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference imageRef =storage.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_tamirlash_order);

        uploadimg=findViewById(R.id.uploadimg);
        orderbtn=findViewById(R.id.ordertamir2btn);
        imgnametxt=findViewById(R.id.imgnametxt);

        product=findViewById(R.id.product1txt);
        addition=findViewById(R.id.addition1txt);
        orderbtn.setEnabled(false);

        uploadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });


    }
    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Img files"),101);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            //we need the file name of the pdf file, so extract the name of the pdf file
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
            }

            orderbtn.setEnabled(true);
            imgnametxt.setText(displayName);

            orderbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadPDF(uri);
                }
            });

        }
    }


    private void uploadPDF(Uri data) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Rasm yuklanmoqda...");
        pd.show();
        User_info user_info = new User_info();
        String logini = user_info.getName();
        String tel = User_info.getTel();
        final StorageReference reference = imageRef.child("uploads/"+ System.currentTimeMillis() + ".jpg");
        UploadTask uploadTask = reference.putFile(data);

        String producttxt=product.getText().toString();
        String additiontxt=addition.getText().toString();

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri = uriTask.getResult();

                String uniqueID = UUID.randomUUID().toString();
                databaseReference.child("Orderstamir").child(logini).child(uniqueID).child("urlimg").setValue(uri.toString());
                databaseReference.child("Orderstamir").child(logini).child(uniqueID).child("product").setValue(producttxt);
                databaseReference.child("Orderstamir").child(logini).child(uniqueID).child("addition").setValue(additiontxt);
                databaseReference.child("Orderstamir").child(logini).child(uniqueID).child("phone").setValue(tel);

                pd.dismiss();
                Toast.makeText(MakeTamirlashOrder.this, "Tamirlashga buyurtma berildi.", Toast.LENGTH_SHORT).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("error",""+e);
                Toast.makeText(MakeTamirlashOrder.this, ""+e, Toast.LENGTH_SHORT).show();
                // Image upload failed
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                // Get the progress of the upload
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                // Update your UI or log the progress
                pd.setMessage("Yuklanyapti : "+ (int) progress + "%");
//                Log.d("Upload Progress", "Upload is " + progress + "% complete");
            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
