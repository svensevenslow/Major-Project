package com.example.carbonfootprintmajorproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity {

    private Button dataCollection;
    private  Button getEstimate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        dataCollection = (Button) findViewById(R.id.button5);
        getEstimate = (Button) findViewById(R.id.button6);


        dataCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ChooseUserTypeActivity.class);
                startActivity(i);
            }
        });
    }
}
