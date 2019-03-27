package com.example.yoursy.wew;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewNote extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String sqlQuery;
    SQLiteDatabase SQLITEDATABASE;

    EditText noteTitleText, noteText;

    String title, texts, categ = "Uncategorized";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        noteTitleText = (EditText) findViewById(R.id.noteTitleText);
        noteText = (EditText) findViewById(R.id.noteText);

        Spinner spinner = (Spinner) findViewById(R.id.categDrop);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categitems, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categ = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categ = "Uncategorized";
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), Notes.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cancelNote:
                Toast.makeText(getApplicationContext(),
                        "You cancel the note",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), Notes.class);
                startActivity(intent);
                break;
            case R.id.saveNote:
                saveNotes();
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
            addNewNote();
        } else if (id == R.id.editNote) {
            editNotes();
        } else if (id == R.id.viewAllNote) {
            viewAllNotes();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void saveNotes() {

        DBCreate();

        try {

            title = noteTitleText.getText().toString().replace("'", "''");
            texts = noteText.getText().toString().replace("'", "''");

            if (title.isEmpty() || equals(null)) {
                if (!texts.isEmpty() || equals(null)) {
                    sqlQuery = "INSERT INTO NOTES (TITLE, NOTE, CATEGORY) VALUES('NO TITLE', '" + texts + "','" + categ + "' )";
                    SQLITEDATABASE.execSQL(sqlQuery);
                    Toast.makeText(NewNote.this, "Note saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), Notes.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(NewNote.this, "You can not save an empty note", Toast.LENGTH_SHORT).show();
                }


            } else if (texts.isEmpty() || equals(null)) {
                Toast.makeText(NewNote.this, "You can not save an empty note", Toast.LENGTH_SHORT).show();
            } else {

                sqlQuery = "INSERT INTO NOTES (TITLE, NOTE,CATEGORY) VALUES('" + title + "', '" + texts + "','" + categ + "')";
                SQLITEDATABASE.execSQL(sqlQuery);

                Toast.makeText(NewNote.this, "Note saved", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getBaseContext(), Notes.class);
                startActivity(intent);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelNote() {
        Intent intent = new Intent(getBaseContext(), Notes.class);
        startActivity(intent);
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


    public void editNotes() {
        Intent intent = new Intent(getBaseContext(), Notes.class);
        startActivity(intent);
    }

    public void addNewNote() {
        Intent intent = new Intent(getBaseContext(), NewNote.class);
        startActivity(intent);
    }

    public void viewAllNotes() {
        Intent intent = new Intent(getBaseContext(), Notes.class);
        startActivity(intent);
    }
}
