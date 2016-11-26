package com.example.gurjap.internshala_notes_app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Gurjap on 11/24/2016.
 */

public class Login_session_shared_pref {

    SharedPreferences mySharedPreferences;
    SharedPreferences.Editor myEditor;
    Context mainContext;
    public Login_session_shared_pref(Context context){
    this.mainContext=context;
    mySharedPreferences=mainContext.getSharedPreferences("Intershala_note",Context.MODE_PRIVATE);
    myEditor=mySharedPreferences.edit();
}
    public void setsession(boolean b){
        myEditor.putBoolean("session",b);
        myEditor.commit();
    }
    public void putusername(String user){
        myEditor.putString("username",user);
        myEditor.commit();
    }
    public  boolean checksession(){
        return mySharedPreferences.getBoolean("session",false);
    }
         String getusername(){
        return  mySharedPreferences.getString("username","");
    }
}
