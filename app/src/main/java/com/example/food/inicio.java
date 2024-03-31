package com.example.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class inicio extends AppCompatActivity {

    private Button btnRedirectLogin, btnRedirectSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.inicio);

        this.btnRedirectLogin = (Button) findViewById(R.id.btnRedirectLogin);
        this.btnRedirectSignup = (Button) findViewById(R.id.btnRedirectSignup);

        this.btnRedirectSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), registro.class);
                startActivity(intent);
            }
        });

        this.btnRedirectLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), iniciosesion.class);
                startActivity(intent);
            }
        });

    }
}