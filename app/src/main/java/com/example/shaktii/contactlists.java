package com.example.shaktii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class contactlists extends AppCompatActivity {
    ProgressBar progressBar;
    EditText contact1,contact2,contact3,contact4,contact5;
    Button btnsave;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    EditText yournum;
    FirebaseAuth auth;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlists);
        contact1= findViewById(R.id.contact1);
        contact2= findViewById(R.id.contact2);
        contact3= findViewById(R.id.contact3);
        contact4= findViewById(R.id.contact4);
        contact5= findViewById(R.id.contact5);
        btnsave = findViewById(R.id.contactsave);
        progressBar = findViewById(R.id.progressBar2);
        yournum = findViewById(R.id.yournum);

        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();



        btnsave.setOnClickListener(view -> {
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("users");

            String num1 = contact1.getText().toString();
            String num2 = contact2.getText().toString();
            String num3 = contact3.getText().toString();
            String num4 = contact4.getText().toString();
            String num5 = contact5.getText().toString();
            String em = yournum.getText().toString();

            progressBar.setVisibility(View.VISIBLE);


            User helperclass = new User( uid, num1, num2, num3, num4, num5);
            reference.child(uid).setValue(helperclass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "contacts are saved", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "error: "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });






        });
    }
}