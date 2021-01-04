package com.example.myquiz;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {
    FirebaseAuth mauth ;
    private Button bouton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        EditText email = (EditText) findViewById(R.id.email);
        EditText motpasse = (EditText) findViewById(R.id.mot);
        TextView compte = (TextView) findViewById(R.id.compte);
        TextView oublierpassword = (TextView) findViewById(R.id.oublier);


        bouton = (Button) findViewById(R.id.btn);
        mauth = FirebaseAuth.getInstance();
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailvalue = email.getText().toString().trim();
                String passwordvalue = motpasse.getText().toString().trim();

                if (emailvalue.isEmpty()) {
                    email.setError(" email required ");
                    email.requestFocus();
                    return;

                }

                if (!Patterns.EMAIL_ADDRESS.matcher(emailvalue).matches()) {
                    email.setError("please enter a valide email ");
                    email.requestFocus();
                    return;

                }

                if (passwordvalue.isEmpty()) {
                    motpasse.setError("pasword is required");
                    motpasse.requestFocus();
                    return;
                }

                if (passwordvalue.length() < 6) {
                    motpasse.setError("min password length is 6 caractére");
                    motpasse.requestFocus();
                    return;

                }

//ta3ml verification est ce que mawjoud welle
                mauth.signInWithEmailAndPassword(emailvalue,passwordvalue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;

                            if (user.isEmailVerified()) {
                                Intent i = new Intent(loginActivity.this, MainActivity.class);
                                startActivity(i);
                            }else
                            {
                                user.sendEmailVerification() ;
                                Toast.makeText(loginActivity.this, "check you email to verified account", Toast.LENGTH_LONG).show();

                            }

                        } else {

                            Toast.makeText(loginActivity.this, "failed to log in ", Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }
        });
        compte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(loginActivity.this, registerActivity.class);
                startActivity(i);

            }
        });
        oublierpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(loginActivity.this, ForgetPassword.class);
                startActivity(a);
            }
        });
    }
}

