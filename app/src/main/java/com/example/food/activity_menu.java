package com.example.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;

public class activity_menu extends AppCompatActivity {

    private Button addProduct, listProduct, editProduct2, camera,ubicacion;
    private DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        this.addProduct = (Button) findViewById(R.id.addProduct);
        this.listProduct = (Button) findViewById(R.id.listProduct);
        this.editProduct2 = (Button) findViewById(R.id.editProduct2);
        this.camera = (Button) findViewById(R.id.camera);
        this.ubicacion = (Button) findViewById(R.id.ubicacion);

        this.addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), registro_producto.class);
                startActivity(intent);
            }
        });

        this.editProduct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), editarProducto.class);
                startActivity(intent);
            }


        });
        this.listProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity_menu.this, ListaProducts.class));
            }
        });

        this.camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity_menu.this, camera.class));
            }
        });


        this.ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity_menu.this,ubicacion.class));
            }
        });
    }
}