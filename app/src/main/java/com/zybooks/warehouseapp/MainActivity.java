package com.zybooks.warehouseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button signUp;
    Button loginButton;
    EditText userName;
    EditText password;
    String userNameValue;
    String passwordValue;
    Context context;
    CharSequence loginMessage;
    int duration;
    boolean dbExists;
    DatabaseManager mDatabase;
    boolean userExists;
    public static ProductDatabaseManager productDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUp = (Button)findViewById(R.id.newAccountButton);
        signUp.setOnClickListener(this);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        mDatabase = new DatabaseManager(MainActivity.this);
        userName = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);
        duration = Toast.LENGTH_SHORT;
        context = getApplicationContext();

       // ProductDatabaseManager productDB = new ProductDatabaseManager(MainActivity.this,"ProductDB",null,1);

          //productDB.queryData("CREATE TABLE IF NOT EXISTS PRODUCT (Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, SKU VARCHAR, image BLOG)");


        ProductDatabaseManager productDB = new ProductDatabaseManager(MainActivity.this);



    }

    public void onClick(View view)
    {
        switch(view.getId()) {


           case R.id.newAccountButton:
                Intent signUpI = new Intent(getApplicationContext(),SignUp.class);
                startActivity(signUpI);
                break;

            case R.id.loginButton:
                userNameValue = userName.getText().toString(); // Values of username and password converted to String
                passwordValue = password.getText().toString();
                userExists = mDatabase.searchForEmployee(userNameValue,passwordValue); // calls search employee function and returns boolean result to userExists

                if(userExists) // If user is in database, login is successful, else display message saying user couldn't be found
                { loginMessage = "Successfully logged in";
                    Toast toast = Toast.makeText(context,loginMessage,duration); toast.show(); Intent openingPageI = new Intent(getApplicationContext(),mainInventoryPage.class);
                    startActivity(openingPageI);
                    break; }

                else{ loginMessage = "Invalid username or password"; Toast toast = Toast.makeText(context,loginMessage,duration); toast.show(); break;}

        }


    }



}
