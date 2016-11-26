package com.example.gurjap.internshala_notes_app;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class mydbhelper extends SQLiteOpenHelper
{


    final private static String DATABASE_NAME="Internshala_not";
    final private static String INTERNSHALA_NOTE="internshala_notes";
    final private static String INTERNSHALA_USER="Internshala_users";
    final private static String NOTE_ID="note_id";
    final private static String NOTE_HEADING="heading";
    final private static String NOTES_NOTE="notes";
    final private static String NOTES_MODIFIED="Modified_date";
 
    final private static String NOTES_USERNAME="username";
    final private static String NOTES_PASSWORD="password";
    final private static int DB_VERSION=1;
    private Context mycontext;
    SQLiteDatabase mydb;

   mydbhelper(Context context)
   {
       super(context,DATABASE_NAME,null,DB_VERSION);
       mycontext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String q="create table if not exists "+INTERNSHALA_NOTE+"("+NOTES_NOTE+" text,"+NOTES_MODIFIED+" INTEGER,"+NOTES_USERNAME+" text,"+NOTE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NOTE_HEADING+" text)";
        String q1="create table if not exists "+INTERNSHALA_USER+"("
                +NOTES_USERNAME+" text primary key," +NOTES_PASSWORD+" text)";
        db.execSQL(q);
        db.execSQL(q1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
    public void open() throws SQLException
    {
        mydb=this.getReadableDatabase();
    }

    long onInsert_Notes(String note, String Heading, long note_id)
    {
        ContentValues myvalues=new ContentValues();
        myvalues.clear();
        Login_session_shared_pref a=new Login_session_shared_pref(this.mycontext);
        if(note_id>=0)myvalues.put(NOTE_ID,note_id);
        if(note!=null) myvalues.put(NOTES_NOTE,note);
        if(Heading!=null)  myvalues.put(NOTE_HEADING,Heading);
        myvalues.put(NOTES_MODIFIED,String.valueOf(System.currentTimeMillis()));
        myvalues.put(NOTES_USERNAME,a.getusername());
        long internshala_notes_in= mydb.insertWithOnConflict(INTERNSHALA_NOTE,null,myvalues, SQLiteDatabase.CONFLICT_IGNORE);
        if(internshala_notes_in<0) {
            return mydb.update(INTERNSHALA_NOTE, myvalues, NOTE_ID+" =?", new String[]{String.valueOf(note_id)});
        }
        return internshala_notes_in;
    }

    long onnewRegister(String username, String password)
    {
        ContentValues myvalues=new ContentValues();
        myvalues.clear();

        myvalues.put(NOTES_USERNAME,username);
        myvalues.put(NOTES_PASSWORD,password);

        return mydb.insertWithOnConflict(INTERNSHALA_USER,null,myvalues, SQLiteDatabase.CONFLICT_IGNORE);

    }


    long Delete_note(String note_id){
    return mydb.delete(INTERNSHALA_NOTE,NOTE_ID+"=?",new String[]{note_id});
    }
}
