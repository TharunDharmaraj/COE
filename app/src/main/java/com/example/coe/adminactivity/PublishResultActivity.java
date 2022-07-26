package com.example.coe.adminactivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coe.MainActivity;
import com.example.coe.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PublishResultActivity extends AppCompatActivity {
    Button upload_;
    EditText up;
    ProgressDialog dialog;
    ImageView upload_back;
    Uri imageuri = null;
    String year;
    String dept;
    @Override
    public void onCreate(Bundle SavedInstance) {
        super.onCreate(SavedInstance);
        setContentView(R.layout.admin_publish_result_activity);
        upload_ = (Button) findViewById(R.id.UploadResultButton);
        upload_back = (ImageView) findViewById(R.id.imageView_);

        String[] str1 = new String[]{"Year 1","Year 2","Year 3","Year 4"};
        String[] str2 = new String[]{"IT","CSC","MECH","ECE","EEE"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, str1);
        AutoCompleteTextView dropdown = (AutoCompleteTextView)
                findViewById(R.id.ScrollDown_3);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    year = "Year 1";
                    Toast.makeText(getApplicationContext(), "Year 1 Selected", Toast.LENGTH_SHORT).show();
                } else if (i == 1) {
                    year = "Year 2";
                    Toast.makeText(getApplicationContext(), "Year 2 Selected", Toast.LENGTH_SHORT).show();
                } else if (i == 2) {
                    year = "Year 3";
                    Toast.makeText(getApplicationContext(), "Year 3 Selected", Toast.LENGTH_SHORT).show();
                } else if (i == 3) {
                    year = "Year 4";
                    Toast.makeText(getApplicationContext(), "Year 4 Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ArrayAdapter<String> adapter_ = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, str2);
        AutoCompleteTextView dropdown_ = (AutoCompleteTextView)
                findViewById(R.id.ScrollDown_4);
        dropdown_.setAdapter(adapter_);
        dropdown_.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    dept = "IT";
                    //Toast.makeText(getApplicationContext(), "Year 1 Selected", Toast.LENGTH_SHORT).show();
                } else if (i == 1) {
                    dept = "CSC";
                    //Toast.makeText(getApplicationContext(), "Year 2 Selected", Toast.LENGTH_SHORT).show();
                } else if (i == 2) {
                    dept = "MECH";
                    //Toast.makeText(getApplicationContext(), "Year 3 Selected", Toast.LENGTH_SHORT).show();
                } else if (i == 3) {
                    dept = "ECE";
                    //Toast.makeText(getApplicationContext(), "Year 4 Selected", Toast.LENGTH_SHORT).show();
                }else if(i == 4){
                    dept = "EEE";
                }
            }
        });

        upload_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                // We will be redirected to choose pdf
                galleryIntent.setType("application/pdf");
                startActivityForResult(galleryIntent, 1);
            }
        });
        upload_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // Here we are initialising the progress dialog box
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");

            // this will show message uploading
            // while pdf is uploading
            dialog.show();
            imageuri = data.getData();
            final String timestamp = "" + System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final String messagePushID = timestamp;
            Toast.makeText(PublishResultActivity.this, imageuri.toString(), Toast.LENGTH_SHORT).show();

            // Here we are uploading the pdf in firebase storage with the name of current time
            final StorageReference filepath = storageReference.child(year+"/"+dept+"/Result_"+year+"_"+dept+".pdf");
            Toast.makeText(PublishResultActivity.this, filepath.getName(), Toast.LENGTH_SHORT).show();
            filepath.putFile(imageuri).continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // After uploading is done it progress
                        // dialog box will be dismissed
                        dialog.dismiss();
                        Uri uri = task.getResult();
                        String myurl;
                        myurl = uri.toString();
                        Toast.makeText(PublishResultActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(PublishResultActivity.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

