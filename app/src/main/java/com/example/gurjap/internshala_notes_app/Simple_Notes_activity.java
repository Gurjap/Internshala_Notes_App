package com.example.gurjap.internshala_notes_app;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;


public class Simple_Notes_activity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple__notes);
if(savedInstanceState==null){
    getSupportFragmentManager().beginTransaction().add(R.id.contentfragment1,new Login_fragment()).commit();
}
  toolbar  = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.signout){
            Login_session_shared_pref a=new Login_session_shared_pref(this);
            a.putusername("");
            a.setsession(false);

           // Toast.makeText(this, "dd", Toast.LENGTH_SHORT).show();

        getSupportFragmentManager().beginTransaction().add(R.id.contentfragment1,new Login_fragment()).commit();

        }


        return super.onOptionsItemSelected(item);
    }
}
