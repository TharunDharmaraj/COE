package com.example.coe.seatallotmentactivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coe.R;
import com.example.coe.resultactivity.ResultActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class SeatAllotmentActivity extends AppCompatActivity {

    Button getresult;
    StorageReference storageReference;
    String message;
    String year;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_seat);

        ImageView pre = (ImageView) findViewById(R.id.imageViewres);
        getresult = (Button) findViewById(R.id.GetResultButton);

        String[] str = new String[]{"Year 1", "Year 2","Year 3","Year 4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, str);

        AutoCompleteTextView dropdown_ = (AutoCompleteTextView) findViewById(R.id.ScrollDown_2);
        dropdown_.setAdapter(adapter);
        dropdown_.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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


        getresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                storageReference.child(year+"/Seat_Allotment_"+year+".pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        message = uri.toString();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message));
                        startActivity(intent);
                        // Got the download URL for 'users/me/profile.png'
                        //downloadFile(ResultActivity.this, "Result", ".pdf", DIRECTORY_DOWNLOADS, uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        Toast.makeText(SeatAllotmentActivity.this,"Error Occured",Toast.LENGTH_SHORT).show();
                    }
                });

//               try{
//                   Toast.makeText(getApplicationContext(),"Please Wait..",Toast.LENGTH_SHORT).show();
//                   Thread.sleep(10);
//
//
//               }catch (Exception e){
//
//
//               }
            }
        });
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void downloadFile(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {

        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);

        downloadmanager.enqueue(request);
    }
}
