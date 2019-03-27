package com.example.yoursy.wew;

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
import android.widget.EditText;
import android.widget.TextView;


public class Edit_Note extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    EditText editNoteTitle, editNoteText;
    TextView categoryTVed;
    String sqlQuery;
    SQLiteDatabase SQLITEDATABASE;
    NoteCheck SQLITEHELPER;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SQLITEHELPER = new NoteCheck(this);

        editNoteTitle = (EditText) findViewById(R.id.editNoteTitle);
        editNoteText = (EditText) findViewById(R.id.editNoteText);
        categoryTVed = (TextView) findViewById(R.id.categoryTVed);

        setValuse();
    }

    @Override
    public void onBackPressed() {
        String ids = getIntent().getStringExtra("ids");
        String title = getIntent().getStringExtra("title");
        String textss = getIntent().getStringExtra("textss");
        String category =  getIntent().getStringExtra("category");
        String datesaved = getIntent().getStringExtra("datesaved");
        String timeSaved = getIntent().getStringExtra("timeSaved");

        Intent intent = new Intent(getBaseContext(), Preview_Note.class);
        intent.putExtra("ids", ids);
        intent.putExtra("title", title);
        intent.putExtra("textss", textss);
        intent.putExtra("category", category);
        intent.putExtra("datesaved", datesaved);
        intent.putExtra("timeSaved", timeSaved);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit__note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.updateNote:
                updateNote();
                break;
            case R.id.exitUpdate:
                Intent hintent = new Intent(getBaseContext(), Notes.class);
                startActivity(hintent);
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

    public void setValuse() {
        String ids = getIntent().getStringExtra("ids");
        String title = getIntent().getStringExtra("title");
        String textss = getIntent().getStringExtra("textss");
        String category = getIntent().getStringExtra("category");
        String datesaved = getIntent().getStringExtra("datesaved");
        String timeSaved = getIntent().getStringExtra("timeSaved");

        editNoteTitle.setText(title);
        editNoteText.setText(textss);
        categoryTVed.setText(category);
    }

    public void updateNote() {

        String ids = getIntent().getStringExtra("ids");


        try {
            SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();
            sqlQuery = "UPDATE NOTES SET TITLE = '" + editNoteTitle.getText().toString().replace("'", "''") + "', NOTE = '" + editNoteText.getText().toString().replace("'", "''") +
                    "' WHERE id = '" + ids + "'";
            SQLITEDATABASE.execSQL(sqlQuery);
            Intent intent = new Intent(getBaseContext(), Notes.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void exitUpdate() {
        Intent intent = new Intent(getBaseContext(), Notes.class);
        startActivity(intent);
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
