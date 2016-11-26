package com.example.gurjap.internshala_notes_app;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login_fragment extends Fragment {
Fragment myfrag;
    FragmentTransaction fragmentTransaction;
    String blank_check_user,blank_check_pass;
    public Login_fragment() {
    }

 @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
     final View myloginview=inflater.inflate(R.layout.login_fragment,container,false);
     final Button login=(Button)myloginview.findViewById(R.id.login_btn);
     final Button registor=(Button)myloginview.findViewById(R.id.register_btn);
     final EditText username=(EditText)myloginview.findViewById(R.id.username);
     final EditText password=(EditText)myloginview.findViewById(R.id.password);
final Login_session_shared_pref a=new Login_session_shared_pref(getActivity());
     onDestroyOptionsMenu();
     if(a.checksession()){

         myfrag=new Notes_list_fragment();
         fragmentTransaction=getFragmentManager().beginTransaction().replace(R.id.contentfragment1,myfrag);
         fragmentTransaction.commit();
     }
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(username.getHint().toString().equals("username")){

                        blank_check_user=username.getText().toString().replace(" ","");
                        blank_check_pass=password.getText().toString().replace(" ","");
                        if(blank_check_user.equals("")){
                            Toast.makeText(getActivity(), "Please enter username", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if(blank_check_pass.equals(""))
                        {
                            Toast.makeText(getActivity(), "Please enter passward", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        mydbhelper obj1=new mydbhelper(getActivity());
                        obj1.open();
                        Cursor myresult;
                        myresult = obj1.mydb.rawQuery("select username from internshala_users where passward=? LIMIT 1",new String[]{password.getText().toString()});


                        if(myresult.moveToNext())
                        {
                            a.setsession(true);
                            a.putusername(myresult.getString(myresult.getColumnIndex("username")));
                            myfrag=new Notes_list_fragment();
                            fragmentTransaction=getFragmentManager().beginTransaction().replace(R.id.contentfragment1,myfrag);
                            fragmentTransaction.commit();
                        }
                        else {

                            Toast.makeText(getActivity(), "Wrong Username or passward", Toast.LENGTH_SHORT).show();
                        }
                        myresult.close();
                        obj1.close1();


                    }
                    else
                    {
                        username.setText("");
                        password.setText("");
                        username.setHint("username");
                        password.setHint("password");
                        login.setText("LOGIN");

                    }




                }
            });
     registor.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(username.getHint().toString().equals("username"))

             {
            username.setHint(R.string.user_hint_reg);
            password.setHint(R.string.pass_hint_reg);
            login.setText("Back to login");
            username.setText("");
            password.setText("");

    }
             else {
        blank_check_user=username.getText().toString().replace(" ","");
        blank_check_pass=password.getText().toString().replace(" ","");
        if(blank_check_user.equals("")){
            Toast.makeText(getActivity(), "Please enter username", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(blank_check_pass.equals(""))
        {
            Toast.makeText(getActivity(), "Please enter passward", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            mydbhelper mydb= new mydbhelper(getActivity());
            mydb.open();

            long a=mydb.onnewRegister(username.getText().toString(),password.getText().toString());
            if(a>=0){

                username.setText("");
                password.setText("");

                Toast.makeText(getActivity(), "You are registered", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(getActivity(), "You already a user", Toast.LENGTH_SHORT).show();






        }



             }

         }
     });
       return myloginview;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        super.onCreateOptionsMenu(menu, inflater);
    }
}
