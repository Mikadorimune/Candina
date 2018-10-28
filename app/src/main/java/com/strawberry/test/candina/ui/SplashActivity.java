package com.strawberry.test.candina.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.strawberry.test.candina.R;

public class SplashActivity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            sqLiteDatabase = getApplicationContext().openOrCreateDatabase("UserDB", Context.MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS userTable(USERNAME TEXT PRIMARY KEY, PASSWORD TEXT)");
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS logTable(USERNAME TEXT, PASSWORD TEXT)");
        } catch (SQLiteException e) {
            Toast.makeText(SplashActivity.this, "Error: " + e, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            sqLiteDatabase.execSQL("INSERT INTO userTable(USERNAME, PASSWORD) VALUES('user', 'pass')");
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM logTable", null);
        if(cursor.moveToFirst()) {
            Intent intent = new Intent(SplashActivity.this, GalleryActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
