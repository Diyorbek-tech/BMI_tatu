package com.xasanboyevdiyorbek.bmi_tatu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Addbook extends AppCompatActivity {
    Button addbook,buttonpdf;
    RatingBar rating;
    TextInputEditText title,muaalif, addition,year;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://reklama-uchun-default-rtdb.firebaseio.com/");
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);

        addbook = findViewById(R.id.addbook);
        buttonpdf = findViewById(R.id.kitobyuklashvbtn);

        title = findViewById(R.id.titletxt);
        muaalif = findViewById(R.id.mualliftxt);
        addition = findViewById(R.id.additiontxt);
        year = findViewById(R.id.yeartxt);
        rating=findViewById(R.id.rating);


        User_info user_info = new User_info();
        String logini = user_info.getName();

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("books");

        addbook.setEnabled(false);
        buttonpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPDF();
            }
        });




    }
    private void selectPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pdf files"),101);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri =data.getData();

            //we need the file name of the pdf file, so extract the name of the pdf file
            String uriString  = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")){
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri,null,null,null,null);
                    if (cursor != null && cursor.moveToFirst()){
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else  if (uriString.startsWith("file://")){
                displayName = myFile.getName();
            }

            addbook.setEnabled(true);
            buttonpdf.setText(displayName);

            addbook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadPDF(data.getData());
                }
            });

        }

    }
    private void uploadPDF(Uri data) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("File Uploading..");
        pd.show();

        final StorageReference reference = storageReference.child("uploads/"+ System.currentTimeMillis() + ".pdf");
        // store in upload folder of the Firebase storage
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();
                        String key=databaseReference.push().getKey();
                        String pattern = "yyyy-dd-MM|HH:mm:ss";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        String date = simpleDateFormat.format(new Date());

                        assert key != null;
                        databaseReference.child(key).child("urlbook").setValue(uri.toString());
                        databaseReference.child(key).child("muallif").setValue(muaalif.getText().toString());
                        databaseReference.child(key).child("title").setValue(title.getText().toString());
                        databaseReference.child(key).child("addition").setValue(addition.getText().toString());
                        databaseReference.child(key).child("year").setValue(year.getText().toString());
                        databaseReference.child(key).child("rating").setValue(String.valueOf(rating.getRating()));
                        databaseReference.child(key).child("addedtime").setValue(date);


                       Toast.makeText(Addbook.this, "Kitob qo`shildi!!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent = (100 * snapshot.getBytesTransferred())/ snapshot.getTotalByteCount();
                        pd.setMessage("Yuklanyapti : "+ (int) percent + "%");
                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}