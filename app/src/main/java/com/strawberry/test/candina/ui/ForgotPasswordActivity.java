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
import android.widget.TextView;

import com.strawberry.test.candina.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText forgotUsername;
    Button forgotButton;
    SQLiteDatabase sqLiteDatabase;
    String givenPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        sqLiteDatabase = getApplicationContext().openOrCreateDatabase("UserDB", Context.MODE_PRIVATE, null);
        forgotUsername = findViewById(R.id.forgot_username);
        forgotButton = findViewById(R.id.forgot_button);
        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = forgotUsername.getText().toString();
                if (forgotUsername.getText().toString().trim().isEmpty()) {
                    createDialog("Username cannot be empty");
                } else if (checkUser(username)) {
                    createDialog("Your password is '" + givenPassword + "'");
                } else {
                    createDialog("Username doesn't exist");
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
                givenPassword = cursor.getString(cursor.getColumnIndex("PASSWORD"));
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
        AlertDialog alertDialog = new AlertDialog.Builder(ForgotPasswordActivity.this).create();
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
