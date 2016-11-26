package com.example.gurjap.internshala_notes_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Gurjap on 11/24/2016.
 */

class Login_session_shared_pref {

    private SharedPreferences mySharedPreferences;
    private SharedPreferences.Editor myEditor;
    private Context mainContext;
    @SuppressLint("CommitPrefEdits")
    Login_session_shared_pref(Context context){
    this.mainContext=context;
    mySharedPreferences=mainContext.getSharedPreferences("Intershala_note",Context.MODE_PRIVATE);
    myEditor=mySharedPreferences.edit();
}
    void setsession(boolean b)
    {
        myEditor.putBoolean("session",b);
        myEditor.commit();
    }
    void putusername(String user){
        myEditor.putString("username",user);
        myEditor.commit();
    }
    boolean checksession(){
        return mySharedPreferences.getBoolean("session",false);
    }
         String getusername(){
        return  mySharedPreferences.getString("username","");
    }
}
