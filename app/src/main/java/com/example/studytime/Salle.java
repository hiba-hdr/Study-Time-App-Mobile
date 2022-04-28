package com.example.studytime;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Salle extends AppCompatActivity {
    private Button logout;
    private Button next;
    private String chosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salle);
        logout =(Button) findViewById(R.id.sedec);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Salle.this,MainActivity.class));
            }
        });

        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Salle.this, GroupSolo.class);
                i.putExtra("city",chosen);
                startActivity(i);

            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        List<String> salles = new ArrayList<>();
        salles.add("choisisser une Salle");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,salles);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String  spinnerValue= parent.getItemAtPosition(position).toString();

                Toast.makeText(getBaseContext(),  spinnerValue, Toast.LENGTH_SHORT).show();
                chosen= spinner.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Salle").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(error != null){
                    Toast.makeText(Salle.this,"something went wrong! Try again", Toast.LENGTH_LONG).show();
                }
                for(DocumentSnapshot doc :value){
                    if(doc.get("nom")!= null){
                        salles.add(doc.getString("nom"));
                    }
                }

            }
        });

    }
}