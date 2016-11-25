package com.example.gurjap.internshala_notes_app;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class mydbhelper extends SQLiteOpenHelper
{


 static String DATABASE_NAME="Internshala_notes_1";
    static int DB_VERSION=1;
    Context mycontext;
    SQLiteDatabase mydb;

   public mydbhelper(Context context)
   {
       super(context,DATABASE_NAME,null,DB_VERSION);
       mycontext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String q="create table if not exists internshala_notes("
               +"notes text," +
                "Modified_date INTEGER," +
                "user_name text," +
                "note_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "heading text)";
        db.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
    public void open() throws SQLException
    {
        mydb=this.getReadableDatabase();
    }
    public void close1(){
        mydb.close();

    }
 public long onInsert_Notes(String note,String Heading,long note_id)
 {
    ContentValues myvalues=new ContentValues();
    myvalues.clear();
    Login_session a=new Login_session();
     if(note_id>=0)myvalues.put("note_id",note_id);
     if(note!=null) myvalues.put("notes",note);
     if(Heading!=null)  myvalues.put("heading",Heading);
     myvalues.put("Modified_date",String.valueOf(System.currentTimeMillis()));
     myvalues.put("user_name",a.getusername());
     long internshala_notes_in= mydb.insertWithOnConflict("internshala_notes",null,myvalues, SQLiteDatabase.CONFLICT_IGNORE);
     if(internshala_notes_in<0) {
         return mydb.update("internshala_notes", myvalues, "note_id =?", new String[]{String.valueOf(note_id)});
     }
    return internshala_notes_in;
}
   public long Delete_note(String note_id){
    return mydb.delete("internshala_notes","note_id=?",new String[]{note_id});
    }
}
