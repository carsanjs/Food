package com.example.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class editarProducto extends AppCompatActivity {
    String idd;

    TextView editCode, editName, editDescription, editPrice, editValue;
    Button btnUpdate, btnBack;
    //    private DatabaseReference database;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private FirebaseFirestore databasefirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_product);

//        this.setTitle("Productos");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ProgressBar progressBar = findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.VISIBLE);
//        progressBar.setProgress(50);
        String id = getIntent().getStringExtra("id_producto");
        databasefirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

//    #declaramos las variables a utilizar
        this.editCode = findViewById(R.id.editCode);
        this.editName = findViewById(R.id.editName);
        this.editDescription = findViewById(R.id.editDescription);
        this.editPrice = findViewById(R.id.editPrice);
        this.editValue = findViewById(R.id.editValue);
//        #botones
        this.btnUpdate = (Button) findViewById(R.id.btnUpdate);
        this.btnBack = (Button) findViewById(R.id.btnBack);

//  navegation anterior
        this.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btnUpdate.setOnClickListener(view ->  {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Productos");
            myRef.child(editCode.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Map<String, Object> map = new HashMap<>();

                    map.put("Nombre Producto", editName.getText().toString());
                    map.put("Descripcion", editDescription.getText().toString());
                    map.put("Precio", editPrice.getText().toString());
                    map.put("Valor", editValue.getText().toString());
                    myRef.child(editCode.getText().toString()).updateChildren(map);

                    Context context = getApplicationContext();
                    String mensaje = "producto actualizado correctamente";
                    Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                    clear();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    String mensaje = "La búsqueda de datos fue cancelada. Error: " + error.getMessage();
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                }
            });
        });

        if (id == null || id == ""){
        }
        else{
            idd = id;
            btnUpdate.setText("Update");
            getProducto(id);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String np = editCode.getText().toString().trim();
                    String dp = editDescription.getText().toString().trim();
                    String pu = editPrice.getText().toString().trim();
                    String valor = editValue.getText().toString().trim();

                    if(np.isEmpty() && dp.isEmpty() && pu.isEmpty() && valor.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Ingresar los datos", Toast.LENGTH_SHORT).show();
                    }else{
                        updateProducto(np, dp, pu,valor, id);
                    }
                }
            });
        }
    }

    private void updateProducto(String edittName, String edittDescription, String edittPrice, String edittValue, String codigo) {

        Map<String, Object> map = new HashMap<>();
        map.put("Nombre Producto",edittName);
        map.put("Descripcion", edittDescription);
        map.put("Precio", edittPrice);
        map.put("Valor",edittValue);


        databasefirestore.collection("Productos").document(codigo).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Producto Actualizado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al actualizar producto", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getProducto(String codigo){
        databasefirestore.collection("Productos").document(codigo).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                DecimalFormat format = new DecimalFormat("0.00");
                String nombreProd = documentSnapshot.getString("Nombre_Producto");
                String descripProd = documentSnapshot.getString("Descripcion");
                String PrecUniProduc = documentSnapshot.getString("Precio_Unidad");
                String ValorProduct= documentSnapshot.getString("Valor");

                editName.setText(nombreProd);
                editDescription.setText(descripProd);
                editPrice.setText(PrecUniProduc);
                editValue.setText(ValorProduct);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clear(){
        editName.setText("");
        editDescription.setText("");
        editPrice.setText("");
        editValue.setText("");
    }
}