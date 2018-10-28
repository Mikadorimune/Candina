package com.strawberry.test.candina.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.strawberry.test.candina.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView registerText, forgotText;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sqLiteDatabase = getApplicationContext().openOrCreateDatabase("UserDB", Context.MODE_PRIVATE, null);
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        registerText = findViewById(R.id.register_text);
        forgotText = findViewById(R.id.forgot_text);
        loginButton.setOnClickListener(this);
        registerText.setOnClickListener(this);
        forgotText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == loginButton) {
            String username = loginUsername.getText().toString();
            String password = loginPassword.getText().toString();
            if (loginUsername.getText().toString().trim().isEmpty() || loginPassword.getText().toString().trim().isEmpty()) {
                createDialog("Username and/or password cannot be empty");
            } else if (checkUser(username, password)) {
                sqLiteDatabase.execSQL("INSERT INTO logTable(USERNAME, PASSWORD) VALUES('" + username + "', '" + password + "')");
                Intent intent = new Intent(LoginActivity.this, GalleryActivity.class);
                startActivity(intent);
                finish();
            } else {
                createDialog("Username and/or password is incorrect");
            }
        }
        if (v == registerText) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
        if (v == forgotText) {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        }
    }

    public boolean checkUser(String username, String password) {
        Cursor cursor = null;
        try {
            String loginQuery = "SELECT * FROM userTable WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "'";
            cursor = sqLiteDatabase.rawQuery(loginQuery, null);
            if (cursor.moveToFirst()) {
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
            }
        } catch (Exception ex) {
            cursor.close();
            return false;
        }
    }

    public void createDialog(String dialogMessage) {
        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        alertDialog.setCancelable(false);
        alertDialog.setMessage(dialogMessage);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "CLOSE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
