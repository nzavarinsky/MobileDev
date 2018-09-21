package com.example.zava.mymobileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.zava.mymobileapp.Utilities.Validations;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

  private EditText firstName, lastName, email;
  private EditText phone, password, confirmPassword;
  private Button submitButton;
  private String emailText, phoneText, passwordText, confPassText;
  private String firstNameText, lastNameText;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);
    initFields();
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
    checkFields();
  }

  public void checkFields() {
    if (!Validations.isValidPassword(passwordText)
        || !Validations.isValidPassword(confPassText)
        || !passwordText.equals(confPassText)
        || !Validations.isValidPhoneNumber(phoneText)
        || !Validations.isValidEmail(emailText)) {

      if (!Validations.isValidFirstName(firstNameText)) {
        firstName.setError(getText(R.string.badFirtsname));
      }
      if (!Validations.isValidLastName(lastNameText)) {
        lastName.setError(getText(R.string.badLastname));
      }
      if (!Validations.isValidEmail(emailText)) {
        email.setError(getText(R.string.badEmail));
      }
      if (!Validations.isValidPhoneNumber(phoneText)) {
        phone.setError(getText(R.string.badPhone));
      }
      if (!Validations.isValidPassword(passwordText)) {
        password.setError(getText(R.string.badPassword));
      }
      if (!Validations.isValidPassword(confPassText)) {
        confirmPassword.setError(getText(R.string.badPassword));
      }
      if (!passwordText.equals(confPassText)) {
        confirmPassword.setError(getText(R.string.badPasswordConfirmation));
      }
    }
  }

  public void initFields() {
    firstName = findViewById(R.id.firstNameEt);
    lastName = findViewById(R.id.lastNameEt);
    phone = findViewById(R.id.phoneEt);
    email = findViewById(R.id.email);
    password = findViewById(R.id.passwordEt);
    confirmPassword = findViewById(R.id.passwordConfirm);
    submitButton = findViewById(R.id.submitButton);
    submitButton.setOnClickListener(this);
  }
}