package com.example.studytime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Reset extends AppCompatActivity {
    private EditText email3;
    private Button reni;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        email3 = (EditText) findViewById(R.id.Email3);
        reni = (Button) findViewById(R.id.reni);

        auth = FirebaseAuth.getInstance();

        reni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }

    private void resetPassword() {
        String email  = email3.getText().toString().trim();

        if(email.isEmpty()){
            email3.setError("Email is required");
            email3.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email3.setError("Please provide valide mail");
            email3.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Reset.this,"Vérifier votre Eamil Aadress pour rénitialiser le mdp",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(Reset.this,"Oups! Essayez econre un fois", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}