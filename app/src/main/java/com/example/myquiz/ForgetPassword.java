package com.example.myquiz;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ForgetPassword extends AppCompatActivity {
    FirebaseAuth auth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        EditText email = (EditText) findViewById(R.id.emailr) ;
        Button reset =(Button) findViewById(R.id.forget1)         ;
        auth = FirebaseAuth.getInstance() ;
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Restpassword()  ;
            }
        }) ;


    }

    private void Restpassword()
    {  EditText email = (EditText) findViewById(R.id.emailr) ;
        String emailvalue = email.getText().toString().trim() ;


        if(emailvalue.isEmpty()) {
            email.setError("full name is required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailvalue).matches()){
            email.setError("Please provide valid email");
            email.requestFocus();
            return;

        }

        auth.sendPasswordResetEmail(emailvalue).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;
                if (task.isSuccessful())

                {
                    Toast.makeText(ForgetPassword.this,"chech you email ",Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(ForgetPassword.this,"verifie your email  ",Toast.LENGTH_LONG).show();

                }

            }
        }) ;

    }


}
