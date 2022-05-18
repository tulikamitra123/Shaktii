package com.example.shaktii;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    FirebaseUser user;
    TextView t;
    Button selfdef, button3, call, nearbyhelp;
    ImageButton btn;
    DatabaseReference myRef ;
    String uid;
    String num1;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         t = findViewById(R.id.textView6);
        btn = findViewById(R.id.emergencybtn);
        selfdef = findViewById(R.id.btnlast);
        button3 = findViewById(R.id.button3);
        call = findViewById(R.id.btnCenter);
        nearbyhelp = findViewById(R.id.btnlast);


        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getEmail();

        t.setText(uid);




        // calling feature
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "1091";
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+phoneNumber));
                startActivity(intent);

            }
        });








        selfdef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openActivity3();
        }
    });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), sosMyLocation.class));
            }
        });

//        nearbyhelp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), nearbyHelp.class));
//
//            }
//        });


}


    public void openActivity2(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
    public void openActivity3(){
        Intent intent = new Intent(this, contactlists.class);
        startActivity(intent);
    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();  //logout
        startActivity(new Intent(getApplicationContext(),login.class));
        finish();


    }



}