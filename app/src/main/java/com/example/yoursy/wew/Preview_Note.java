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
import android.widget.TextView;

public class Preview_Note extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView notePrevTitle, noteDatePrevText, notePrevText, notePrevCateg;
    private String title, datetime, texts, category;

    String sqlQuery;
    SQLiteDatabase SQLITEDATABASE;
    NoteCheck SQLITEHELPER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview__note);
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

        notePrevTitle = (TextView) findViewById(R.id.notePrevTitle);
        noteDatePrevText = (TextView) findViewById(R.id.noteDatePrevText);
        notePrevText = (TextView) findViewById(R.id.notePrevText);
        notePrevCateg = (TextView) findViewById(R.id.notePrevCateg);

        setValues();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), Notes.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.preview__note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.editReading:
                editNote();
                break;
            case R.id.deleteReading:
                deleteNote();
                break;
            case R.id.shareNotes:
                shareNote();
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

    public void shareNote() {
        texts = getIntent().getStringExtra("textss");
        title = getIntent().getStringExtra("title");
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, texts);
        startActivity(Intent.createChooser(sharingIntent, "Share with"));
    }


    public void setValues() {

        title = getIntent().getStringExtra("title");
        datetime = getIntent().getStringExtra("datesaved") + " : " + getIntent().getStringExtra("timeSaved");
        texts = getIntent().getStringExtra("textss");
        category = getIntent().getStringExtra("category");

        notePrevTitle.setText(title);
        noteDatePrevText.setText(datetime);
        notePrevText.setText(texts);
        notePrevCateg.setText(category);

    }

    public void deleteNote() {

        try {
            SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();
            String idsss = getIntent().getStringExtra("ids");
            sqlQuery = "DELETE FROM NOTES WHERE id = '" + idsss + "'";
            SQLITEDATABASE.execSQL(sqlQuery);
            Intent intent = new Intent(getBaseContext(), Home.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editNote() {

        String ids = getIntent().getStringExtra("ids");
        String title = getIntent().getStringExtra("title");
        String textss = getIntent().getStringExtra("textss");
        String datesaved = getIntent().getStringExtra("datesaved");
        String timeSaved = getIntent().getStringExtra("timeSaved");
        String categ = getIntent().getStringExtra("category");

        Intent intent = new Intent(getBaseContext(), Edit_Note.class);
        intent.putExtra("ids", ids);
        intent.putExtra("title", title);
        intent.putExtra("textss", textss);
        intent.putExtra("category", categ);
        intent.putExtra("datesaved", datesaved);
        intent.putExtra("timeSaved", timeSaved);
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
