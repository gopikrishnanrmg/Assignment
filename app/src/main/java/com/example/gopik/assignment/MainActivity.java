package com.example.gopik.assignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {
    EditText name,pass,mail,name1,pass1;
    Button reg,log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        reg  = (Button) findViewById(R.id.button2);
        log  = (Button) findViewById(R.id.button3);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    attemptRegister();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseLogin();
            }
        });
    }


    private void attemptRegister(){
        ParseUser currentUser = ParseUser.getCurrentUser();//This is to sign out the current user who is registered
        currentUser.logOut();                              //otherwise we won't be able to add(Register) multiple users
        name = (EditText) findViewById(R.id.editText6);
        mail = (EditText) findViewById(R.id.editText5);
        pass = (EditText) findViewById(R.id.editText7);
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
                    Toast.makeText(getApplicationContext(),title+"\n"+message,Toast.LENGTH_LONG).show();
                } else {                                  //not null means there is some error in creating a new user
                    final String title = "Error Account Creation failed";
                    final String message = "Account could not be created";
                    Toast.makeText(getApplicationContext(),title+"\n"+message+"\n"+e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void parseLogin() {
        name1 = (EditText) findViewById(R.id.editText8);
        pass1 = (EditText) findViewById(R.id.editText9);
        try{
        ParseUser.logInInBackground(name1.getText().toString(), pass1.getText().toString(), new LogInCallback() {   //method requesting log in
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {                            // checks if such a user user with provided credentials exist
                    if(parseUser.getBoolean("emailVerified")) { // emailverified is string that is assigned to users who verified their email
                        Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                        intent.putExtra("name",name1.getText().toString());
                        startActivity(intent);
                    }
                    else {                                           // if its not verified user is logged out and asked to verify his/her account
                        parseUser.logOut();
                        Toast.makeText(getApplicationContext(),"Login Fail"+"\n"+"Please Verify Your Email first",Toast.LENGTH_LONG).show();
                    }
                } else {                                            // if the login was not successful because of errors like wrong credentials the user is notified to try again
                    Toast.makeText(getApplicationContext(),"Login fail"+"\n"+ e.getMessage()+ "Please retry",Toast.LENGTH_LONG).show();
                }
            }
        });}catch(Exception e){                                     // if there is some error in logging in process the error is displayed
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }

    }
}
