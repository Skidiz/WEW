package com.example.yoursy.wew;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class Splash extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(5 * 1000);

                    Intent i = new Intent(getBaseContext(), Home.class);
                    startActivity(i);

                    finish();
                } catch (Exception e) {

                }
            }
        };

        background.start();
    }

    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }
}



