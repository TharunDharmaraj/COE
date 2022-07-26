package com.example.coe.adminactivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coe.R;
import com.example.coe.adminactivity.admincreateexamactivity.CreateExamActivity;
import com.example.coe.createnotification.CreateNotificationActivity;

public class CreateAlertActivity extends AppCompatActivity {
    public static String title = "Sample";
    public static String desc = "Example";
    EditText alert_t;
    EditText alert_d;
    Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alert);

        alert_t = (EditText) findViewById(R.id.alertTitle);
        alert_d = (EditText) findViewById(R.id.alertDescription);
        title = alert_t.getText().toString();
        desc = alert_d.getText().toString();

        but = (Button) findViewById(R.id.btnCreateAlert);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNotificationActivity cre = new CreateNotificationActivity();
                cre.CreateNotification(CreateAlertActivity.this);
                cre.sendTopic(v);
            }
        });



    }
}
