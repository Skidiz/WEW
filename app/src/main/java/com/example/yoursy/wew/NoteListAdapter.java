package com.example.yoursy.wew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Yoursy on 13/02/2018.
 */
public class NoteListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> noteId;
    ArrayList<String> noteTitle;
    ArrayList<String> noteText;
    ArrayList<String> categText;
    ArrayList<String> noteDate;
    ArrayList<String> noteTime;


    public NoteListAdapter(Context context2, ArrayList<String> id, ArrayList<String> noteTitle2,
                           ArrayList<String> noteText2, ArrayList<String> noteCateg2, ArrayList<String> noteDate2,
                           ArrayList<String> noteTime2) {
        this.context = context2;
        this.noteId = id;
        this.noteTitle = noteTitle2;
        this.noteText = noteText2;
        this.categText = noteCateg2;
        this.noteDate = noteDate2;
        this.noteTime = noteTime2;

    }


    @Override
    public int getCount() {
        return noteId.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View child, ViewGroup parent) {
        Holder holder;
        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.note_list_layouts, null);

            holder = new Holder();

            holder.noteTitleTv = (TextView) child.findViewById(R.id.noteTitleTv);
            holder.noteDateTv = (TextView) child.findViewById(R.id.noteDateTv);
            holder.noteTextTv = (TextView) child.findViewById(R.id.noteTextTv);
            holder.noteCategTv = (TextView) child.findViewById(R.id.categoryTV);

            child.setTag(holder);
        } else {
            holder = (Holder) child.getTag();
        }

        holder.noteTitleTv.setText(noteTitle.get(position));
        holder.noteDateTv.setText(noteDate.get(position) + " : " + noteTime.get(position));
        holder.noteTextTv.setText(noteText.get(position));
        holder.noteCategTv.setText(categText.get(position));
        return child;

    }


    private class Holder {
        TextView noteTitleTv;
        TextView noteDateTv;
        TextView noteTextTv;
        TextView noteCategTv;
    }
}
