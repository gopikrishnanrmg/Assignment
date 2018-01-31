package com.example.gopik.assignment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    EditText name1,pass1;
    Button log;
    View parentholder;
    Activity referenceActivity;
    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        referenceActivity = getActivity();
        parentholder=inflater.inflate(R.layout.fragment_login, container, false);
        log = (Button) parentholder.findViewById(R.id.button3);
        name1 = (EditText) parentholder.findViewById(R.id.editText5);
        pass1 = (EditText) parentholder.findViewById(R.id.editText6);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    ParseUser.logInInBackground(name1.getText().toString(), pass1.getText().toString(), new LogInCallback() {   //method requesting log in
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if (parseUser != null) {                            // checks if such a user user with provided credentials exist
                                if(parseUser.getBoolean("emailVerified")) { // emailverified is string that is assigned to users who verified their email
                                    Intent intent = new Intent(getContext(),Main2Activity.class);
                                    intent.putExtra("name",name1.getText().toString());
                                    startActivity(intent);
                                }
                                else {                                           // if its not verified user is logged out and asked to verify his/her account
                                    parseUser.logOut();
                                    Toast.makeText(getContext(),"Login Fail"+"\n"+"Please Verify Your Email first",Toast.LENGTH_LONG).show();
                                }
                            } else {                                            // if the login was not successful because of errors like wrong credentials the user is notified to try again
                                Toast.makeText(getContext(),"Login fail"+"\n"+ e.getMessage()+ "Please retry",Toast.LENGTH_LONG).show();
                            }
                        }
                    });}catch(Exception e){                                     // if there is some error in logging in process the error is displayed
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                }

            }
        });
        return parentholder;
    }

}
