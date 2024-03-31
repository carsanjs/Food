package com.example.food;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class registro_producto extends AppCompatActivity {
    private TextView addCode, addName, addDescription, addPrice, addValue;
    private Button btnAdd, btnAddBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_producto);

        this.addCode = findViewById(R.id.addCode);
        this.addName = findViewById(R.id.addName);
        this.addDescription = findViewById(R.id.addDescription);
        this.addPrice = findViewById(R.id.addPrice);
        this.addValue = findViewById(R.id.addValue);


//        #botones
        this.btnAdd = (Button) findViewById(R.id.btnAdd);
        this.btnAddBack = (Button) findViewById(R.id.btnAddBack);

//        #botones de navegacion
        this.btnAddBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        btnAdd.setOnClickListener(view ->{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Productos");

            if(addCode.getText().toString().isEmpty() && addName.getText().toString().isEmpty() && addDescription.getText().toString().isEmpty() && addPrice.getText().toString().isEmpty() && addValue.getText().toString().isEmpty()){

                CharSequence text = "Hubo un error y no se pudo guardar el producto.";
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

            }
            else{

                Map<String, Object> map = new HashMap<>();
                map.put("Codigo",addCode.getText().toString());
                map.put("Nombre Producto",addName.getText().toString());
                map.put("Descripcion", addDescription.getText().toString());
                map.put("Precio", addPrice.getText().toString());
                map.put("Valor",addValue.getText().toString());
                myRef.child(addCode.getText().toString()).updateChildren(map);

                AlertDialog.Builder confirm = new AlertDialog.Builder(this);
                confirm.setTitle("Confirmación");
                confirm.setMessage("¿Desea registrar un Producto?");
                confirm.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CharSequence text = "Producto guardado correctamente";
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        }
                        catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
//                            #me lleva a al menu
                        Intent intent = new Intent(getApplicationContext(), activity_menu.class);
                        startActivity(intent);
                    }
                });
                confirm.setNegativeButton("Cancelar", null);
                AlertDialog ventana = confirm.create();
                ventana.show();
                clear();


            }
        });

//        btnAdd.setOnClickListener(view -> {
//
//            try {
////                String code, name, description, price, value;
////                code = addCode.getText().toString();
////                name = addName.getText().toString();
////                description = addDescription.getText().toString();
////                price = addPrice.getText().toString();
////                value = addValue.getText().toString();
//
//                if(! addCode.getText().toString().isEmpty() && !addName.getText().toString().isEmpty() && !addDescription.getText().toString().isEmpty() && !addPrice.getText().toString().isEmpty() && !addValue.getText().toString().isEmpty()){
//                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    DatabaseReference myRef = database.getReference("Productos");
//
//
//
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("Codigo",addCode.getText().toString());
//                    map.put("Nombre Producto",addName.getText().toString());
//                    map.put("Descripcion", addDescription.getText().toString());
//                    map.put("Precio", addPrice.getText().toString());
//                    map.put("Valor",addValue.getText().toString());
//                    myRef.child(addCode.getText().toString()).updateChildren(map);
//
//                    AlertDialog.Builder confirm = new AlertDialog.Builder(this);
//                    confirm.setTitle("Confirmación");
//                    confirm.setMessage("¿Desea registrar un Producto?");
//                    confirm.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            CharSequence text = "Producto guardado correctamente";
//                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
//                            try {
//                                TimeUnit.SECONDS.sleep(1);
//                            }
//                            catch (InterruptedException e) {
//                                throw new RuntimeException(e);
//                            }
////                            #me lleva a al menu
//                            Intent intent = new Intent(getApplicationContext(), activity_menu.class);
//                            startActivity(intent);
//                        }
//                    });
//                    confirm.setNegativeButton("Cancelar", null);
//                    AlertDialog ventana = confirm.create();
//                    ventana.show();
//                    clear();
//                }
//                else{
//                    CharSequence text = "Hubo un error y no se pudo guardar el producto.";
//                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
//                }
//            }
//            catch (Exception e){
//                Log.d("Register Message", "Error-> "+e.getMessage());
//            }
//        });
    }
    private void clear(){
        addCode.setText("");
        addName.setText("");
        addDescription.setText("");
        addPrice.setText("");
        addValue.setText("");
    }


}