package com.example.gurjap.internshala_notes_app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class Login_fragment extends Fragment {
Fragment myfrag;
    FragmentTransaction fragmentTransaction;
    public Login_fragment() {
    }

 @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
           View myloginview=inflater.inflate(R.layout.login_fragment,container,false);
     Button login=(Button)myloginview.findViewById(R.id.login_btn);
     Button registor=(Button)myloginview.findViewById(R.id.register_btn);
        EditText usename=(EditText)myloginview.findViewById(R.id.username);
        EditText password=(EditText)myloginview.findViewById(R.id.password);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    myfrag=new Notes_list_fragment();
                    fragmentTransaction=getFragmentManager().beginTransaction().replace(R.id.contentfragment1,myfrag);
                    fragmentTransaction.commit();


                }
            });
     registor.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {


         }
     });
       return myloginview;
    }
}
