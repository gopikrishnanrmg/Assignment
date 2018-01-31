package com.example.gopik.assignment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {

    EditText name,pass,mail;
    Button reg;
    View parentholder;
    Activity referenceActivity;
    public Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        referenceActivity = getActivity();
        parentholder = inflater.inflate(R.layout.fragment_register, container, false);
        reg = (Button) parentholder.findViewById(R.id.button2);
        name = (EditText) parentholder.findViewById(R.id.editText2);
        mail = (EditText) parentholder.findViewById(R.id.editText3);
        pass = (EditText) parentholder.findViewById(R.id.editText4);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  ParseUser currentUser = ParseUser.getCurrentUser();//This is to sign out the current user who is registered
              //  currentUser.logOut();                              //otherwise we won't be able to add(Register) multiple users
                String email = mail.getText().toString();
                String password = pass.getText().toString();
                ParseUser user = new ParseUser();
                user.setUsername(name.getText().toString());
                user.setPassword(password);
                user.setEmail(email);

                user.signUpInBackground(new SignUpCallback(){     //mehod used for signing up a new user
                    @Override
                    public void done(ParseException e) {          //to check if the new user was successfully created
                        if (e == null) {                          //null means successfully created, that is there is no error
                            final String title = "Account Created Successfully!";
                            final String message = "Please verify your email before Login";
                            Toast.makeText(getContext(),title+"\n"+message,Toast.LENGTH_LONG).show();
                        } else {                                  //not null means there is some error in creating a new user
                            final String title = "Error Account Creation failed";
                            final String message = "Account could not be created";
                            Toast.makeText(getContext(),title+"\n"+message+"\n"+e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        return parentholder;
    }

}
