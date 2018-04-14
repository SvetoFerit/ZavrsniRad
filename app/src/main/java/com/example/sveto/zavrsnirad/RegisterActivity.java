package com.example.sveto.zavrsnirad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.sveto.zavrsnirad.SignUpActivity.*;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.ivRegisterActivity)
    ImageView ivRegisterActivity;
    @BindView(R.id.btnLogIn)
    Button btnLogIn;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.textView)
    TextView textView;

    String email, password, extra_Email, extra_Password;

    Boolean logged = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        btnSignUp.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignUp:
                startActivity(new Intent(RegisterActivity.this, SignUpActivity.class));
                break;
            case R.id.btnLogIn:
                if (validate()) {
                    startActivity(new Intent(RegisterActivity.this, SecondActivity.class));
                    logged = true;
                }

                break;


        }

    }

    private boolean validate() {
        Boolean result = false;

        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        extra_Password = getIntent().getStringExtra(EXTRA_PASSWORD);
        extra_Email = getIntent().getStringExtra(EXTRA_EMAIL);

        if (email.equals(extra_Email) && password.equals(extra_Password)) {
            result = true;
        } else if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        } else if (!email.equals(extra_Email) && !password.equals(extra_Password)) {
            Toast.makeText(this, "Incorrect email and password!", Toast.LENGTH_SHORT).show();
        } else if (!email.equals(extra_Email)) {
            Toast.makeText(this, "Incorrect email!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrect password!", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (logged) {
            startActivity(new Intent(RegisterActivity.this, SecondActivity.class));
        }

    }
}
