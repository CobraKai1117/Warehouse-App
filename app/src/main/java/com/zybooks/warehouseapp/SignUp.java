package com.zybooks.warehouseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;


public class SignUp extends AppCompatActivity implements View.OnClickListener {

    Button newUser;
    EditText firstName;
    EditText lastName;
    EditText jobTitle;
    EditText userName;
    EditText password;
    String firstNameValue;
    String lastNameValue;
    String jobTitleValue;
    String userNameValue;
    String passwordValue;
    String accessLevel;
    Context context;
    CharSequence signUpMessage;
    int duration;
    boolean addSuccessful = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        newUser = findViewById(R.id.signUpUser);
        newUser.setOnClickListener(this);
        firstName = findViewById(R.id.editTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
        jobTitle = findViewById(R.id.jobTitleText);
        userName = findViewById(R.id.userNameText);
        password = findViewById(R.id.passwordTextValue);
        context = getApplicationContext();
        signUpMessage = "Account successfully created";
        duration = Toast.LENGTH_SHORT;

    }



    public void onClick(View view)
    {
        DatabaseManager employeeDatabase = new DatabaseManager(SignUp.this);
        firstNameValue = firstName.getText().toString();
        lastNameValue = lastName.getText().toString();
        jobTitleValue = jobTitle.getText().toString();
        userNameValue = userName.getText().toString();
        passwordValue = password.getText().toString();
        boolean isManager = jobTitleValue.toLowerCase().contains("manager");

        if(isManager) {accessLevel = "3";} else {accessLevel = "1";}

            addSuccessful = employeeDatabase.addEmployee(firstNameValue, lastNameValue, jobTitleValue, userNameValue, passwordValue, accessLevel);
            //signUpMessage = "Account successfully created";
        if(addSuccessful)
        {
            Toast toast = Toast.makeText(context, signUpMessage, duration);
            toast.show();
            this.finish();
        }

        else
        {
            signUpMessage = "Entry could not be added";
            Toast toast = Toast.makeText(context,signUpMessage,duration);
            toast.show();
        }
    }



    }
