package com.example.myquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class registerActivity extends AppCompatActivity {
    private FirebaseAuth mauth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mauth=FirebaseAuth.getInstance();
        EditText nom= (EditText) findViewById(R.id.nom);
        EditText prenom= (EditText) findViewById(R.id.prenom);
        EditText email= (EditText) findViewById(R.id.email);
        EditText mot= (EditText) findViewById(R.id.mot);
        Button btn =(Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nomv = nom.getText().toString() ;
                String prenomv = prenom.getText().toString() ;
                String emailv = email.getText().toString() ;
                String motv = mot.getText().toString() ;


                if(nomv.isEmpty()) {
                    nom.setError("full name is required");
                    nom.requestFocus();
                    return;
                }

                if(prenomv.isEmpty()) {
                    prenom.setError("full surname is required");
                    prenom.requestFocus();
                    return;
                }

                if(emailv.isEmpty()) {
                    email.setError("full email is required");
                    email.requestFocus();
                    return;
                }

                //forme de l'email
                if(!Patterns.EMAIL_ADDRESS.matcher(emailv).matches()){
                    email.setError("Please provide valid email");
                    email.requestFocus();
                    return;

                }
                if(motv.isEmpty()) {
                    mot.setError("full name is required");
                    mot.requestFocus();
                    return;
                }

                if(motv.length()<6) {
                    mot.setError("length should be 6 char");
                    mot.requestFocus();
                    return;
                }

                mauth.createUserWithEmailAndPassword(emailv,motv)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    User user = new User(nomv,prenomv,emailv,motv);
                                    //firebase bch ta3ml objet w tepointi 3al bd de l'app en cour
                                    //getReference acceder a un emplacement de la bd eli houwa esmou users
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(registerActivity.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(registerActivity.this, "Failed to register try again", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                                }else{
                                    Toast.makeText(registerActivity.this,
                                            "Failed to register ",
                                            Toast.LENGTH_LONG).show();
                                }

                            }

                        });




            }
        });




        Button btn2= (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(registerActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}