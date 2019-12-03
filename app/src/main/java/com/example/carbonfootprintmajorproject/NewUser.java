package com.example.carbonfootprintmajorproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NewUser extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);

        Spinner carType = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> myAdaptar = new ArrayAdapter<String>(NewUser.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.car_type));
        myAdaptar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carType.setAdapter(myAdaptar);

    }
}
