package com.example.gopik.assignment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.parse.Parse;
import com.parse.ParseInstallation;


public class MainActivity extends AppCompatActivity {
    Button swappy;
    Boolean chk=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swappy = (Button) findViewById(R.id.button);
        Parse.initialize(this);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    swappy.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            swap();
        }
    });
    }



    public void swap(){
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();
    if(chk==true){
         Register frag1 = new Register();
         transaction.replace(R.id.frameLayout,frag1);
         transaction.commit();
         chk=false;
     }
     else
     {
         Login frag2 = new Login();
         transaction.replace(R.id.frameLayout,frag2);
         transaction.commit();
         chk=true;
     }
    }

}
