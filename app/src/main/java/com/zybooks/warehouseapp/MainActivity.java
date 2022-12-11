package com.zybooks.warehouseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button signUp;
    boolean dbExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUp = (Button)findViewById(R.id.newAccountButton);
        signUp.setOnClickListener(this);
        DatabaseManager mDatabase = new DatabaseManager(MainActivity.this);


    }

    public void onClick(View view)
    {
        switch(view.getId()) {


           case R.id.newAccountButton:
                //Log.i("NumberGenerated","THIS IS A TEST MESSAGE");
                Intent signUpI = new Intent(getApplicationContext(),SignUp.class);
                startActivity(signUpI);
                break;
        }


    }



}
