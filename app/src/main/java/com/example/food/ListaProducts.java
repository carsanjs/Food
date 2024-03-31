package com.example.food;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListaProducts extends AppCompatActivity {

    Button btnBack,btnAdd;
    TableLayout tableLayout;

    ImageView deleteimage;
    EditText searchView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listadeproductos);

        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        tableLayout = findViewById(R.id.tableLayout);
        this.searchView2 = findViewById(R.id.searchView2);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaProducts.this, activity_menu.class));
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaProducts.this, registro_producto.class));
            }
        });

        searchView2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buscarProductos(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    private void buscarProductos(String textoBusqueda) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference productosRef = database.getReference("Productos");

        productosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tableLayout.removeAllViews();

                TableRow headerRow = new TableRow(ListaProducts.this);
                // Agregar encabezados de columna

                TextView header1 = new TextView(ListaProducts.this);
                header1.setText("Codigo");
                header1.setBackgroundColor(Color.GRAY);
                headerRow.addView(header1);

                TextView header2 = new TextView(ListaProducts.this);
                header2.setText("Descripción del Producto");
                header2.setBackgroundColor(Color.WHITE);
                headerRow.addView(header2);

                TextView header3 = new TextView(ListaProducts.this);
                header3.setText("Nombre del Producto");
                header3.setBackgroundColor(Color.GRAY);
                headerRow.addView(header3);

                TextView header4 = new TextView(ListaProducts.this);
                header4.setText("Precio");
                header4.setBackgroundColor(Color.WHITE);
                headerRow.addView(header4);

                TextView header5 = new TextView(ListaProducts.this);
                header5.setText("Valor");
                header5.setBackgroundColor(Color.GRAY);
                headerRow.addView(header5);

                TextView header6 = new TextView(ListaProducts.this);
                header6.setText("Eliminar");
                header6.setBackgroundColor(Color.WHITE);
                headerRow.addView(header6);

                tableLayout.addView(headerRow);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.exists()) {
                        String codigo = snapshot.child("Codigo").getValue().toString();
                        String descripcion = snapshot.child("Descripcion").getValue().toString();
                        String nombreProducto = snapshot.child("Nombre Producto").getValue().toString();
                        String precio = snapshot.child("Precio").getValue().toString();
                        String valor = snapshot.child("Valor").getValue().toString();

                        // Verificar si el producto coincide con el texto de búsqueda
                        if (codigo.contains(textoBusqueda) || descripcion.contains(textoBusqueda) || nombreProducto.contains(textoBusqueda) || precio.contains(textoBusqueda) || valor.contains(textoBusqueda)) {

                            TableRow row = new TableRow(ListaProducts.this);

                            // Agregar texto a la celda
                            TextView casilla1 = new TextView(ListaProducts.this);
                            casilla1.setText(codigo);
                            row.addView(casilla1);

                            TextView casilla2 = new TextView(ListaProducts.this);
                            casilla2.setText(descripcion);
                            row.addView(casilla2);

                            TextView casilla3 = new TextView(ListaProducts.this);
                            casilla3.setText(nombreProducto);
                            row.addView(casilla3);

                            TextView casilla4 = new TextView(ListaProducts.this);
                            casilla4.setText(precio);
                            row.addView(casilla4);

                            TextView casilla5 = new TextView(ListaProducts.this);
                            casilla5.setText(valor);
                            row.addView(casilla5);

                            Button btnEliminar = new Button(ListaProducts.this);


                            btnEliminar.setText("Eliminar");

                            String codigoProducto = codigo; // Código del producto como identificador único
                            row.addView(btnEliminar);
                            btnEliminar.setTag(codigoProducto);


                            btnEliminar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Llamar a la clase Eliminar para borrar el producto de Firebase
                                        AlertDialog.Builder confirm = new AlertDialog.Builder(ListaProducts.this);
                                        confirm.setTitle("Confirmación");
                                        confirm.setMessage("Desea Eliminar Producto?");
                                        confirm.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                CharSequence text = "Producto Eliminado con exito";
                                                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                                                try {
                                                    TimeUnit.SECONDS.sleep(1);
                                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                    DatabaseReference myRef = database.getReference("Productos");
                                                    String codigoProducto = (String) v.getTag();
                                                    myRef.child(codigoProducto).removeValue().addOnSuccessListener(command -> {
                                                    });
                                                }
                                                catch (InterruptedException e) {
                                                    throw new RuntimeException(e);
                                                }
//                                              #me lleva a la clase de nuevo
                                                startActivity(new Intent(ListaProducts.this,ListaProducts.class));
                                            }
                                        });
                                        confirm.setNegativeButton("Cancelar", null);
                                        AlertDialog ventana = confirm.create();
                                        ventana.show();


                                    }
                                });
                            tableLayout.addView(row);



                        }
                    }
                }
                if (!dataSnapshot.exists()) {
                    Context context = getApplicationContext();
                    String mensaje = "No existen producto registrados";
                    Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }
}
