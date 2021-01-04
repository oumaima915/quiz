package com.example.myquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class accueilActivity extends AppCompatActivity {
    private Button commencer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        commencer = (Button) findViewById(R.id.commencer);
        commencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openMainActivity();
            }
        });
    }
    public void openMainActivity(){
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
    }
}