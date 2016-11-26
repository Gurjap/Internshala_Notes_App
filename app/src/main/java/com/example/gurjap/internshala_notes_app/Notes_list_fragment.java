package com.example.gurjap.internshala_notes_app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Gurjap on 11/24/2016.
 */

public class Notes_list_fragment extends Fragment{

    ArrayAdapter notes_list_ArrayAdapter;
    ArrayList<String> notes,modified_date,heading,note_id;

    String username;
    ListView mynoteslist;
    TextView headingTextView;
    TextView noteTextView;
   int list_position;

    public Notes_list_fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myNotelistview=inflater.inflate(R.layout.notes_list,container,false);
         mynoteslist= (ListView) myNotelistview.findViewById(R.id.notes_listview);
        notes=new ArrayList<>();
        note_id=new ArrayList<>();
        modified_date=new ArrayList<>();
        heading=new ArrayList<>();



        Login_session_shared_pref login_session=new Login_session_shared_pref(getActivity());
        username=login_session.getusername();
        fetchnotes(login_session.getusername());
        notes_list_ArrayAdapter=new mynotelistview(getActivity(),R.layout.notes_list_view,notes);
        mynoteslist.setAdapter(notes_list_ArrayAdapter);
        FloatingActionButton fab = (FloatingActionButton) myNotelistview.findViewById(R.id.newnote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Dialog_for_add_update(-1);

            }
        });
        return myNotelistview;
    }

    String date_to_time(String startTime) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormatter.parse(startTime);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a dd-MM-yyyy");
        return timeFormatter.format(date);
        }
    public static String convertDate(String dateInMilliseconds, String dateFormat) {
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }
    void Dialog_for_add_update(final long note_id){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View mydialogview=layoutInflater.inflate(R.layout.add_notes,null);
        builder1.setView(mydialogview);
        builder1.setCancelable(true);
          headingTextView= (TextView) mydialogview.findViewById(R.id.add_heading);
          noteTextView= (TextView) mydialogview.findViewById(R.id.add_note);
if(note_id>0){
    headingTextView.setText(heading.get(list_position));
    noteTextView.setText(notes.get(list_position));
}

        builder1.setPositiveButton(
                getResources().getString(R.string.save),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String heading_check=headingTextView.getText().toString();
                        heading_check=heading_check.replace(" ","");
                        if(heading_check.equals("")){
                            Toast.makeText(getActivity(),getResources().getString(R.string.enter_heading), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mydbhelper mydb=new mydbhelper(getActivity());
                        mydb.open();
                        mydb.onInsert_Notes(noteTextView.getText().toString(),headingTextView.getText().toString(),note_id);
                        mydb.close();
                        dialog.cancel();
                        fetchnotes(username);
                        notes_list_ArrayAdapter.notifyDataSetChanged();
                        mynoteslist.setAdapter(notes_list_ArrayAdapter);
                        Toast.makeText(getActivity(), getResources().getString(R.string.saved), Toast.LENGTH_SHORT).show();

                    }
                });

        builder1.setNegativeButton(
                getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    public void fetchnotes(String username)
    {
        notes.clear();
        modified_date.clear();
        heading.clear();
        note_id.clear();
        mydbhelper obj1=new mydbhelper(getActivity());
        obj1.open();
        Cursor myresult;
        myresult = obj1.mydb.rawQuery(getResources().getString(R.string.sql_query_fetch_note),new String[]{username});


        while(myresult.moveToNext())
        {
            notes.add(myresult.getString(myresult.getColumnIndex("notes")));
            modified_date.add(myresult.getString(myresult.getColumnIndex("Modified_date")));
            heading.add(myresult.getString(myresult.getColumnIndex("heading")));
            note_id.add(myresult.getString(myresult.getColumnIndex("note_id")));
        }
        myresult.close();
        obj1.close();
}

 class mynotelistview extends ArrayAdapter<String>
    {
        mynotelistview(Context context, int resource, ArrayList<String> objects) {
            super(context, resource, objects);
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull final ViewGroup parent) {
            LayoutInflater myinflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View myviewlist = myinflater.inflate(R.layout.notes_list_view, parent, false);

            TextView noteTextView= (TextView) myviewlist.findViewById(R.id.note_listview);
            TextView headingTextView= (TextView) myviewlist.findViewById(R.id.heading_note);
            TextView modifideTextView= (TextView) myviewlist.findViewById(R.id.modified_date);
            Button delete= (Button) myviewlist.findViewById(R.id.delete_btn);
            Button editbtn= (Button) myviewlist.findViewById(R.id.edit_btn);
            noteTextView.setText(notes.get(position));

            headingTextView.setText(heading.get(position));
            try {
                modifideTextView.setText(date_to_time(convertDate(modified_date.get(position),"yyyy-MM-dd HH:mm:ss")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
editbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        View parentview= (View) view.getParent();
        ListView listView = (ListView) parentview.getParent();
        list_position = listView.getPositionForView(parentview);
        Dialog_for_add_update(Long.parseLong(note_id.get(listView.getPositionForView(parentview))));
    }
});
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View parentview= (View) view.getParent();
                    ListView listView = (ListView) parentview.getParent();
                    list_position = listView.getPositionForView(parentview);

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setMessage(getResources().getString(R.string.delete_dialog));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.delete_yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mydbhelper mydbhelpernew=new mydbhelper(getActivity());
                                    mydbhelpernew.open();
                                    mydbhelpernew.Delete_note(note_id.get(list_position));
                                    mydbhelpernew.close();
                                    fetchnotes(username);
                                    notes_list_ArrayAdapter.notifyDataSetChanged();
                                    mynoteslist.setAdapter(notes_list_ArrayAdapter);
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.delete_no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }
            });


            return myviewlist;
        }
    }

}