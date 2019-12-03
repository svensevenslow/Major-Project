package com.example.carbonfootprintmajorproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ChooseUserTypeActivity extends AppCompatActivity {
    private Button newUser;
    private Button existingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

        newUser = (Button) findViewById(R.id.button7);
        existingUser = (Button) findViewById(R.id.button8);


        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NewUser.class);
                startActivity(i);
            }
        });
        existingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ExistingUser.class);
                startActivity(i);
            }
        });
    }
}
