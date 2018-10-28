package com.strawberry.test.candina.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.strawberry.test.candina.R;

public class RegisterActivity extends AppCompatActivity {
    EditText registerUsername, registerPassword;
    Button registerButton;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sqLiteDatabase = getApplicationContext().openOrCreateDatabase("UserDB", Context.MODE_PRIVATE, null);
        registerUsername = findViewById(R.id.register_username);
        registerPassword = findViewById(R.id.register_password);
        registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = registerUsername.getText().toString();
                String password = registerPassword.getText().toString();
                if (registerUsername.getText().toString().trim().isEmpty() || registerPassword.getText().toString().trim().isEmpty()) {
                    createDialog("Username and/or password cannot be empty", 0);
                } else if (checkUser(username)) {
                    createDialog("Username already exist", 0);
                } else {
                    sqLiteDatabase.execSQL("INSERT INTO userTable(USERNAME, PASSWORD) VALUES('" + username + "', '" + password + "')");
                    createDialog("Registration successful", 1);
                }
            }
        });
    }

    public boolean checkUser(String username) {
        Cursor cursor = null;
        try {
            String loginQuery = "SELECT * FROM userTable WHERE USERNAME = '" + username + "'";
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

    public void createDialog(String dialogMessage, final int userState) {
        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
        alertDialog.setCancelable(false);
        alertDialog.setMessage(dialogMessage);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "CLOSE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (userState == 1) {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                        dialog.dismiss();

                    }
                });
        alertDialog.show();
    }
}
