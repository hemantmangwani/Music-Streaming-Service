package com.kapil.musicplayer.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.kapil.musicplayer.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private EditText emailText,passwordText,emailSignup,passwordSignup;
    private Button loginButton,signupButton;
    private TextView signUp,loginText;
    private LinearLayout loginScreen,signupScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = findViewById(R.id.input_email);
        emailSignup = findViewById(R.id.email);

        passwordText = findViewById(R.id.input_password);
        passwordSignup = findViewById(R.id.password);

        loginButton = findViewById(R.id.btn_login);
        signupButton = findViewById(R.id.btn_signup);

        signUp = findViewById(R.id.link_signup);
        loginText = findViewById(R.id.link_login);

        loginScreen = findViewById(R.id.login_screen);
        signupScreen = findViewById(R.id.signup_screen);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(emailText.getWindowToken(),0);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginScreen.setVisibility(View.GONE);
                signupScreen.setVisibility(View.VISIBLE);
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginScreen.setVisibility(View.VISIBLE);
                signupScreen.setVisibility(View.GONE);
            }
        });
    }

    private void signUp () {
        Log.d(TAG, "signUp: ");


        String email = emailSignup.getText().toString();
        String password = passwordSignup.getText().toString();

        if ((email != null && !email.isEmpty()) &&
                (password != null && !password.isEmpty())) {
            SharedPreferences pref = this.getSharedPreferences("LoginData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("email",email);
            editor.putString("password",password);
            editor.apply();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(this,"Account Created",Toast.LENGTH_SHORT).show();
        }
    }

    private void login () {
        Log.d(TAG, "login: ");

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        Log.d(TAG, "login: " + email);
        Log.d(TAG, "login: " + password);

        if ((!email.isEmpty()) &&
                ( !password.isEmpty())) {
            SharedPreferences pref = this.getSharedPreferences("LoginData", Context.MODE_PRIVATE);
            if (pref.getString("email","0").equals(email) &&
                    pref.getString("password","0").equals(password)) {
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this,"Wrong data",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,"Wrong data",Toast.LENGTH_SHORT).show();
        }

    }

}
