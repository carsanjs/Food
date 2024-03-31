package com.example.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class iniciosesion<EmailPasswordActivity> extends AppCompatActivity {

    TextView loginEmail, loginPassword;
    Button btnLogin, bnt_registrarse;
    private DatabaseReference database;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciosesion);

        mAuth = FirebaseAuth.getInstance();

        this.loginEmail = findViewById(R.id.loginEmail);
        this.loginPassword = findViewById(R.id.loginPassword);
        this.bnt_registrarse = findViewById(R.id.bnt_registrarse);
        this.btnLogin = (Button) findViewById(R.id.btnLogin);

//

//        _____________botones de navegacion:_______________________
//        #re-dirrecionar a registrar
        bnt_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(iniciosesion.this, registro.class));
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myusers = database.getReference("Clientes");

                try {
                    //String email = loginEmail.getText().toString().trim();
                    //String password = loginPassword.getText().toString().trim();

                    if (!loginEmail.getText().toString().isEmpty() && !loginPassword.getText().toString().isEmpty()){


                        myusers.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    if (loginEmail.getText().toString().equals(snapshot.child("Email").getValue()) && loginPassword.getText().toString().equals(snapshot.child("Pass").getValue())) {

                                        CharSequence text = "Login Correcto!!";
                                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), activity_menu.class);
                                        startActivity(intent);
                                        clear();
                                        return;
                                    }

                                }
                                CharSequence text = "Usuario o contraseña incorrecta.";
                                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });

                    }
                }
                catch (Exception e){
                    System.out.print(e.getMessage());
                }
            }
        });
    }

    private void reload() { }
    //    private void updateUI(FirebaseUser loginEmail) {
//
//    }
    public Boolean ValidateEmail(){
        String ValEmail=loginEmail.getText().toString();
        if (ValEmail.isEmpty()){
            loginEmail.setError("El nombre de usuario no puede estar vacío.");
            return false;
        }else{
            loginEmail.setError(null);
            return true;
        }
    }
    public Boolean ValidatePassword(){
        String ValPassword=loginPassword.getText().toString();
        if (ValPassword.isEmpty()){
            loginPassword.setError("El Contraseña no puede estar vacía.");
            return false;
        }else{
            loginPassword.setError(null);
            return true;
        }
    }
    private void clear(){
        loginEmail.setText("");
        loginPassword.setText("");
    }
}
