package com.example.zava.mymobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText firstName, lastName, email;
    EditText phone, password, confirmPassword;
    Button submitButton;
    SharedPreferences sharedPreferences;
    String emailText, phoneText, passwordText, confPassText;
    String firstNameText, lastNameText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.passwordConfirm);
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        emailText = email.getText().toString();
        passwordText = password.getText().toString();
        firstNameText = firstName.getText().toString();
        lastNameText = lastName.getText().toString();
        passwordText = password.getText().toString();
        confPassText = confirmPassword.getText().toString();
        phoneText = phone.getText().toString();
        

        if (!Validations.isValidPassword(passwordText) || !Validations.isValidPassword(confPassText) || !passwordText.equals(confPassText) ||
                !Validations.isValidPhoneNumber(phoneText) || !Validations.isValidEmail(emailText)) {
            if (!Validations.isValidFirstName(firstNameText)) {
                firstName.setError("Більше одного символа");
            }
            if (!Validations.isValidLastName(lastNameText)) {
                lastName.setError("Більше одного символа");
            }
            if (!Validations.isValidEmail(emailText)) {
                email.setError("Неправильний емейл");
            }
            if (!Validations.isValidPhoneNumber(phoneText)) {
                phone.setError("Перевірте чи правильно введено телефон");
            }
            if (!Validations.isValidPassword(passwordText)) {
                password.setError("8+ символів");
            }
            if (!Validations.isValidPassword(confPassText)) {
                confirmPassword.setError("8+ символів");
            }
            if (!passwordText.equals(confPassText)) {
                confirmPassword.setError("Паролі повинні співпадати");
            }
        }
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

}