package edu.hzuapps.androidlabs.net1814080903129;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class knowledge extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge);

        final Activity thisActivity = this;
        Button btnOpen = (Button) findViewById(R.id.button_back1);

        View view = findViewById(R.id.button_back1);

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thisActivity, ShiftActivity.class);
                thisActivity.startActivity(intent);
            }
        });
    }
}