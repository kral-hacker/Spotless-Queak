package com.example.spotlessqueak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    //Varibles
    TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    Button regBtn, regToLoginBtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        //Hooks
        regName = findViewById(R.id.name);
        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPhoneNo = findViewById(R.id.phoneNo);
        regPassword = findViewById(R.id.password);
        regBtn = findViewById(R.id.go);
        regToLoginBtn = findViewById(R.id.login);


    }


    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();
        if (val.isEmpty()) {
            regName.setError("Field cannot be empty.");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateuserName() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "(?::\\s+$)";
        if (val.isEmpty()) {
            regName.setError("Field cannot be empty.");
            return false;
        } else if (val.length()>=15) {
            regName.setError("Username too long.");
            return false;

        } else if (!val.matches(noWhiteSpace)) {
            regName.setError("White spaces are not allowed");
            return false;
        }
        else
        {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        if (val.isEmpty()) {
            regName.setError("Field cannot be empty.");
            return false;
        } else if (!val.matches(emailPattern)) {
            regName.setError("Invalid Email");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getEditText().getText().toString();
        if (val.isEmpty()) {
            regName.setError("Field cannot be empty.");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        if (val.isEmpty()) {
            regName.setError("Field cannot be empty.");
            return false;
        }
        else if (!val.matches(passwordVal)) {
            regName.setError("Password is too weak");
            return false;}
            else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    //Save data in firebase on the click of the button
    public void registerUser(View view) {

        if (!validateName() | !validateuserName() | !validateEmail() |validatePhoneNo() |validatePassword()){
            //return;
        }
        // get all the values

        rootNode = FirebaseDatabase.getInstance();

        reference = rootNode.getReference("users");


        String name = regName.getEditText().getText().toString();
        String username = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String phoneNo = regPhoneNo.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();

        UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);


        reference.child(username).setValue(helperClass);
        View calling = findViewById(R.id.go);
        calling.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent  = new Intent(signup.this, login.class);
                startActivity(intent);
            }
        });


    }





}
