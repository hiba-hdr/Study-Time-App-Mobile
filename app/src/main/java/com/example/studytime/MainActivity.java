package com.example.studytime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView signup,mdoublie;
    private EditText EmailAdrese, Passwd;
    private Button signin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signup =(TextView) findViewById(R.id.signup);
        signup.setOnClickListener(this);

        signin = (Button) findViewById(R.id.seconnecter);
        signin.setOnClickListener(this);

        EmailAdrese = (EditText) findViewById(R.id.EmailAddress);
        Passwd = (EditText) findViewById(R.id.Password);
        mAuth = FirebaseAuth.getInstance();
        mdoublie = (TextView) findViewById(R.id.mdpoublie);
        mdoublie.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.seconnecter:
                UserLogin();
                break;
            case R.id.mdpoublie:
                startActivity(new Intent(this,Reset.class));




        }
    }

    private void UserLogin() {
        String email = EmailAdrese.getText().toString().trim();
        String pwd = Passwd.getText().toString().trim();

        if(email.isEmpty()){
            EmailAdrese.setError("le champs est vide!");
            EmailAdrese.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            EmailAdrese.setError("Email non valid!");
            EmailAdrese.requestFocus();
            return;
        }
        if(pwd.isEmpty()){
            Passwd.setError("le champs est vide!");
            Passwd.requestFocus();
            return;
        }
        if(pwd.length()<6){
            Passwd.setError("Pwd doit avoir au 6 caractèrs!");
            Passwd.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        // direcet to user profile
                        startActivity(new Intent(MainActivity.this, Salle.class));

                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Accédez a votre email pour le vérifier ",Toast.LENGTH_LONG).show();


                    }

                }else{
                    Toast.makeText(MainActivity.this,"vérifiez vos cordonées",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}