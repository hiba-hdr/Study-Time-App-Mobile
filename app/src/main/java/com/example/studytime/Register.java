package com.example.studytime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private EditText name,age_,email_,mdp;
    private Button enregistrer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        enregistrer = (Button) findViewById(R.id.enregister);
        enregistrer.setOnClickListener(this);

        name = (EditText) findViewById(R.id.fullname);
        age_= (EditText) findViewById(R.id.age);
        email_ = (EditText) findViewById(R.id.email2);
        mdp = (EditText) findViewById(R.id.Password2);




    }

    @Override
    public void onClick(View v) {
        registerUser();

    }

    private void registerUser() {
            String email= email_.getText().toString().trim();
            String password = mdp.getText().toString().trim();
            String fullname = name.getText().toString().trim();
            String age = age_.getText().toString().trim();

            if(fullname.isEmpty()){
                name.setError("champs est vide!");
                name.requestFocus();
                return;
            }
            if(age.isEmpty()){
                age_.setError("champs est vide!");
                age_.requestFocus();
                return;
            }
            if(email.isEmpty()){
                email_.setError("champs est vide!");
                email_.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                email_.setError("email non valid");
                email_.requestFocus();
                return;
            }
            if(password.isEmpty()){
                mdp.setError("champs est vide!");
                mdp.requestFocus();
                return;
            }
            if(password.length()<6){
                mdp.setError("mdp doit avoir au min 6 characters!");
                mdp.requestFocus();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Users users = new Users(fullname,age,email);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(Register.this, "vous vous êtes enregistré avec succès", Toast.LENGTH_LONG).show();

                                        }else{
                                            Toast.makeText(Register.this,"oups! essayez encore une fois", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                });

                            }else {
                                Toast.makeText(Register.this," oups! essayez encore une fois", Toast.LENGTH_LONG).show();


                            }
                        }
                    });


        }
}