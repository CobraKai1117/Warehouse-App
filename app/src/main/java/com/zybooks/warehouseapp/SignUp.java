package com.zybooks.warehouseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


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

    }



    public void onClick(View view)
    {
        Log.i("NumberGenerated","LYDIA IS WEIRD");
        DatabaseManager employeeDatabase = new DatabaseManager(SignUp.this);
        firstNameValue = firstName.getText().toString();
        lastNameValue = lastName.getText().toString();
        jobTitleValue = jobTitle.getText().toString();
        userNameValue = userName.getText().toString();
        passwordValue = password.getText().toString();
        boolean isManager = jobTitleValue.toLowerCase().contains("manager");
        if(isManager) {accessLevel = "3";} else {accessLevel = "1";}
        employeeDatabase.addEmployee(firstNameValue,lastNameValue,jobTitleValue,userNameValue,passwordValue,accessLevel);
        employeeDatabase.close();

    }
}