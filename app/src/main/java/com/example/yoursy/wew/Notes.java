package com.example.yoursy.wew;

import android.app.Activity;
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

public class Notes extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String sqlQuery;
    NoteCheck SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;

    Cursor cursor;
    NoteListAdapter ListAdapter;

    ArrayList<String> ID_ArrayList = new ArrayList<String>();
    ArrayList<String> TITLE_ArrayList = new ArrayList<String>();
    ArrayList<String> CATEGORY_ArrayList = new ArrayList<String>();
    ArrayList<String> TEXT_ArrayList = new ArrayList<String>();
    ArrayList<String> DATE_ArrayList = new ArrayList<String>();
    ArrayList<String> TIME_ArrayList = new ArrayList<String>();

    ListView LISTVIEW;

    private Menu menu;

    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewNote();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LISTVIEW = (ListView) findViewById(R.id.NotesListView);

        SQLITEHELPER = new NoteCheck(this);
    }

    @Override
    protected void onResume() {
        ShowSQLiteDBdata();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notes, menu);
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
                Intent hintent = new Intent(getBaseContext(), Notes.class);
                startActivity(hintent);
                break;
            case R.id.deleteAllNotes:
                showDeleteDialog();
                break;
            case R.id.suggestionMenu:
                Intent intents = new Intent(getBaseContext(), Suggestion.class);
                startActivity(intents);
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
        } else if (id == R.id.nav_share) {
            shareApp();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void shareApp() {
        Intent intent = new Intent(getBaseContext(), Share.class);
        startActivity(intent);
    }


    private void ShowSQLiteDBdata() {

        SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();
        cursor = SQLITEDATABASE.rawQuery("SELECT * FROM NOTES ", null);

        final ArrayList<String> ID_ArrayList = new ArrayList<String>();
        final ArrayList<String> TITLE_ArrayList = new ArrayList<String>();
        final ArrayList<String> TEXT_ArrayList = new ArrayList<String>();
        final ArrayList<String> DATE_ArrayList = new ArrayList<String>();
        final ArrayList<String> TIME_ArrayList = new ArrayList<String>();
        final ArrayList<String> CATEGORY_ArrayList = new ArrayList<String>();

        ID_ArrayList.clear();
        TITLE_ArrayList.clear();
        TEXT_ArrayList.clear();
        DATE_ArrayList.clear();
        TIME_ArrayList.clear();
        CATEGORY_ArrayList.clear();

        if (cursor.moveToFirst()) {
            do {
                ID_ArrayList.add(cursor.getString(cursor
                        .getColumnIndex(NoteCheck.KEY_ID)));
                TITLE_ArrayList.add(cursor.getString(cursor
                        .getColumnIndex(NoteCheck.KEY_CATEGORY)));
                TEXT_ArrayList.add(cursor.getString(cursor
                        .getColumnIndex(NoteCheck.KEY_NOTE)));
                CATEGORY_ArrayList.add(cursor.getString(cursor
                        .getColumnIndex(NoteCheck.KEY_CATEG)));
                DATE_ArrayList.add(cursor.getString(cursor
                        .getColumnIndex(NoteCheck.KEY_DATE_CREATED)));
                TIME_ArrayList.add(cursor.getString(cursor
                        .getColumnIndex(NoteCheck.KEY_TIME_CREATED)));

            } while (cursor.moveToNext());
        }

        ListAdapter = new NoteListAdapter(Notes.this, ID_ArrayList,
                TITLE_ArrayList, TEXT_ArrayList, CATEGORY_ArrayList, DATE_ArrayList, TIME_ArrayList);


        LISTVIEW.setAdapter(ListAdapter);

        LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String ids = ID_ArrayList.get(position).toString();
                String title = TITLE_ArrayList.get(position).toString();String category = CATEGORY_ArrayList.get(position).toString();
                String textss = TEXT_ArrayList.get(position).toString();

                String datesaved = DATE_ArrayList.get(position).toString();
                String timeSaved = TIME_ArrayList.get(position).toString();

                Intent intent = new Intent(getBaseContext(), Preview_Note.class);
                intent.putExtra("ids", ids);
                intent.putExtra("title", title);
                intent.putExtra("textss", textss);
                intent.putExtra("category", category);
                intent.putExtra("datesaved", datesaved);
                intent.putExtra("timeSaved", timeSaved);

                startActivity(intent);

            }
        });


    }

    public void showDeleteDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Confirm delete");
        alertDialogBuilder.setMessage("Do you really want to delete all notes?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAllNote();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void deleteAllNote() {

        try {
            sqlQuery = "DELETE FROM NOTES";
            SQLITEDATABASE.execSQL(sqlQuery);
            Intent intent = new Intent(getBaseContext(), EmptyNote.class);
            startActivity(intent);
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
