/*
package com.example.zava.mymobileapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public EditText etName;
    TextView textWithName;
    Button clearText, sayHello,nextLabButton;
    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Hello World :)");

        etName = findViewById(R.id.etName);
        textWithName = findViewById(R.id.textWithName);
        clearText = findViewById(R.id.clearText);
        sayHello = findViewById(R.id.sayHello);
        nextLabButton = findViewById(R.id.nextLabButton);

        nextLabButton.setOnClickListener(this);
        sayHello.setOnClickListener(this);
        clearText.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) { // switch to conrol clicks between two different buttons
            case R.id.clearText:
                etName.getText().clear();
                break;
            case R.id.sayHello:
                textWithName.setText("Hello, " + etName.getText().toString());// output our congratulation
                etName.getText().clear();
                break;
            case R.id.nextLabButton:
                Intent intent = new Intent(this, SignUpActivity.class);
                this.startActivity ( intent );
                break;
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
*/
