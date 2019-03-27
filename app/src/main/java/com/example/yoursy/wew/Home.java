package com.example.yoursy.wew;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SQLiteDatabase SQLITEDATABASE;
    NoteCheck SQLITEHELPER;
    Cursor cursor;

    private String id, category, notes, dateE, timeE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SQLITEHELPER = new NoteCheck(this);

        checkNotes();
    }



    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addIconNote:
                Intent intent = new Intent(getBaseContext(), NewNote.class);
                startActivity(intent);
                break;
            case R.id.homeMenuNotes:
                Intent hintent = new Intent(getBaseContext(), Home.class);
                startActivity(hintent);
                break;
            case R.id.deleteAllNotes:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.addNewNote) {

        } else if (id == R.id.editNote) {

        } else if (id == R.id.viewAllNote) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void checkNotes() {
        try {

            DBCreate();

            final Context context = this;

            SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();

            cursor = SQLITEDATABASE.rawQuery("SELECT * FROM "
                    + NoteCheck.TABLE_NAME + "", null);

            if (cursor.moveToFirst()) {
                do {
//                    id, category, notes, dateE, timeE;
                    String id;
                    id = (cursor.getString(cursor
                            .getColumnIndex(NoteCheck.KEY_ID)));
                    category = (cursor.getString(cursor
                            .getColumnIndex(NoteCheck.KEY_CATEGORY)));

                    notes = (cursor.getString(cursor
                            .getColumnIndex(NoteCheck.KEY_NOTE)));

                    dateE = (cursor.getString(cursor
                            .getColumnIndex(NoteCheck.KEY_DATE_CREATED)));

                    timeE = (cursor.getString(cursor
                            .getColumnIndex(NoteCheck.KEY_TIME_CREATED)));

                    if (!id.isEmpty() || id.equals(null)) {

                        Intent intent = new Intent(getBaseContext(), Notes.class);
                        startActivity(intent);

                    } else if (id.isEmpty() || id.equals(null)) {

                        Intent i = new Intent(getBaseContext(), EmptyNote.class);
                        startActivity(i);
                    }

                } while (cursor.moveToNext());
                cursor.close();
            } else {

                Intent i = new Intent(getBaseContext(), EmptyNote.class);
                startActivity(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void DBCreate() {

        try {

            SQLITEDATABASE = openOrCreateDatabase("DOQUE",
                    Context.MODE_PRIVATE, null);
            SQLITEDATABASE
                    .execSQL("CREATE TABLE IF NOT EXISTS NOTES (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                            " TITLE TEXT, NOTE TEXT, CATEGORY TEXT," +
                            " DATE_CREATED DATETIME DEFAULT CURRENT_DATE," +
                            " TIME_CREATED DATETIME DEFAULT CURRENT_TIME );");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
