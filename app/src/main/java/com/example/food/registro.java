package com.example.food;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class registro extends AppCompatActivity{

    TextView userNick, userName, userLastName, userEmail, userPassword;
    Button btnSignup, btnBackLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);


        this.userNick = findViewById(R.id.userNick);
        this.userName = findViewById(R.id.userName);
        this.userLastName = findViewById(R.id.userLastName);
        this.userEmail = findViewById(R.id.userEmail);
        this.userPassword = findViewById(R.id.userPassword);

        this.btnBackLogin = (Button) findViewById(R.id.btnBackLogin);
        this.btnSignup = (Button) findViewById(R.id.btnSignup);

        this.btnBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), iniciosesion.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(view ->{

            try {
                String usernick, username, userlasname, useremail, userpassword;
                usernick =userNick.getText().toString();
                username = userName.getText().toString();
                userlasname = userLastName.getText().toString();
                useremail = userEmail.getText().toString();
                userpassword = userPassword.getText().toString();

                if (!usernick.isEmpty() && !username.isEmpty() && !userlasname.isEmpty() && !useremail.isEmpty()  && !userpassword.isEmpty()){

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Clientes");

                    Map<String, Object> map = new HashMap<>();
                    map.put("Id",userNick.getText().toString());
                    map.put("Nombre", userName.getText().toString());
                    map.put("Apellido", userLastName.getText().toString());
                    map.put("Email", userEmail.getText().toString());
                    map.put("Pass", userPassword.getText().toString());
                    myRef.child(userNick.getText().toString()).updateChildren(map);


                    AlertDialog.Builder confirm = new AlertDialog.Builder(this);
                    confirm.setTitle("Confirmación");
                    confirm.setMessage("¿Desea registrar el usuario?");

                    confirm.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            CharSequence text = "Usuario registrado con éxito";
                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            }
                            catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
//                            #me lleva a inicio index
                            Intent intent = new Intent(getApplicationContext(), inicio.class);
                            startActivity(intent);
                        }
                    });
                    confirm.setNegativeButton("Cancelar", null);
                    AlertDialog ventana = confirm.create();
                    ventana.show();
                    clear();

                }else{
                    CharSequence text = "Algunos campos están vacíos o incorrectos.";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){
                Log.d("Register Message", "Error-> "+e.getMessage());
            }

        });
    }
    private void clear(){
        userNick.setText("");
        userName.setText("");
        userLastName.setText("");
        userEmail.setText("");
        userPassword.setText("");
    }
}
