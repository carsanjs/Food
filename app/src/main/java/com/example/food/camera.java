package com.example.food;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
public class camera extends AppCompatActivity{
    Button btn_Captura;
    ImageView imgForm;
    String rutaImg;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        btn_Captura =(Button) findViewById(R.id.bntc);
        imgForm = (ImageView) findViewById(R.id.imageView);


        btn_Captura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File imgArchivo=null;
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                try {
                    imgArchivo=crearImagen();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(imgArchivo!=null){
                    Uri fotoUri = FileProvider.getUriForFile(camera.this,"com.example.food.fileprovider",imgArchivo);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,fotoUri);
                }
                cameraLan.launch(intent);


            }
        });
    }


    ActivityResultLauncher<Intent> cameraLan=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode()==RESULT_OK){
                Bitmap image= BitmapFactory.decodeFile(rutaImg);
                imgForm.setImageBitmap(image);
            }
        }
    });

    private File crearImagen() throws IOException {
        String nombreImagen="foto_";
        File carpeta=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File img=File.createTempFile(nombreImagen,".jpg",carpeta);
        rutaImg=img.getAbsolutePath();
        return img;
    }

    ActivityResultLauncher<Intent> LanzaCamara= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK){
                Bundle extras = result.getData().getExtras();
                Bitmap imagen= (Bitmap) extras.get("data");
                imgForm.setImageBitmap(imagen);
            }
        }
    });
}
