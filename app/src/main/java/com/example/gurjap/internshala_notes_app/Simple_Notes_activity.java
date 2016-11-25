package com.example.gurjap.internshala_notes_app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class Simple_Notes_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple__notes);
if(savedInstanceState==null){
    getSupportFragmentManager().beginTransaction().add(R.id.contentfragment1,new Login_fragment()).commit();
}

    }


}
