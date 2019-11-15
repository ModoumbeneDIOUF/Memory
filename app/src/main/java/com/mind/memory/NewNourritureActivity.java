package com.mind.memory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.annotations.SerializedName;
import com.koushikdutta.ion.Ion;
import com.mind.memory.Api.ApiNourritureOffert;
import com.mind.memory.Model.NourritureOffer;
import com.mind.memory.Retrof.RetrofitNourritureOffert;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class NewNourritureActivity extends AppCompatActivity {
    private ImageView nouritureImage;
    EditText type,desc,provenance,lieu,contact,quantite,jourRestant;
    private Button btnValider;
    Uri targetUri;
    String typen,provenancen,descn,lieun,quantiten,contactn,saveCurrentDate,saveCurrentTime,jour;
    Bitmap bitmap;
    final int PICK_IMAGE_REQUEST = 234;
    private ProgressDialog loadingBar;
    private int GALLERY = 1, CAMERA = 2;

    /*
        Nourriture offert class
    */
    class NourritureOffert{

        private String typeNourritureOffert;
        private String descriptionNourritureOffert;
        private String lieu;
        private String provenanceNourritureOffert;
        private String quantiteNourritureOffert;
        private String numero;
        private String jourRestant;
        private String dateAjoutNourritureOffert;

        public NourritureOffert(String typeNourritureOffert, String descriptionNourritureOffert, String lieu, String provenanceNourritureOffert, String quantiteNourritureOffert, String numero) {
            this.typeNourritureOffert = typeNourritureOffert;
            this.descriptionNourritureOffert = descriptionNourritureOffert;
            this.lieu = lieu;
            this.provenanceNourritureOffert = provenanceNourritureOffert;
            this.quantiteNourritureOffert = quantiteNourritureOffert;
            this.numero = numero;
        }

        public String getTypeNourritureOffert() {
            return typeNourritureOffert;
        }

        public String getDescriptionNourritureOffert() {
            return descriptionNourritureOffert;
        }

        public String getLieu() {
            return lieu;
        }

        public String getProvenanceNourritureOffert() {
            return provenanceNourritureOffert;
        }

        public String getQuantiteNourritureOffert() {
            return quantiteNourritureOffert;
        }

        public String getNumero() {
            return numero;
        }

        public String getJourRestant() {
            return jourRestant;
        }

        public String getDateAjoutNourritureOffert() {
            return dateAjoutNourritureOffert;
        }
    }

    /*
     Upload class
 */
    public class Myuploader{
        private static final String data_upload_url = "http://192.168.43.216/back/public/api/offrirNourriture";
        private final Context c;
        public  Myuploader(Context c){this.c = c;}

        public void upload(NourritureOffert s,View...inputView){
            if (s == null){
                Toast.makeText(c, "Pas de donnés à enrigistrer", Toast.LENGTH_SHORT).show();
            }
            else {
                File imageFile;
                try {
                    imageFile = new File(getImagePath(targetUri));

                }catch (Exception e){
                    Toast.makeText(c, "Veillez choisir une image", Toast.LENGTH_SHORT).show();
                    return;
                }
                //progressBar
                AndroidNetworking.upload(data_upload_url)
                            .addMultipartFile("image",imageFile)
                            .addMultipartParameter("type",s.getTypeNourritureOffert())
                            .addMultipartParameter("desc",s.getDescriptionNourritureOffert())
                            .addMultipartParameter("provenance",s.getProvenanceNourritureOffert())
                            .addMultipartParameter("lieu",s.getLieu())
                            .addMultipartParameter("quantite",s.getQuantiteNourritureOffert())
                            .addMultipartParameter("numero",s.getNumero())
                            .addMultipartParameter("date","date")
                            .addMultipartParameter("jourRestant","2")
                            .addMultipartParameter("name","upload")
                            .setTag("MYSQL_UPLOAD")
                            .setPriority(Priority.HIGH)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    if (response != null){
                                        try {
                                            //show response from server
                                            String message = response.get("message").toString();
                                            Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
                                            if (message.equalsIgnoreCase("success")){
                                                Toast.makeText(c, "Bien", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
                                            }
                                        }catch (Exception e){
                                            Toast.makeText(c, "JsonException", Toast.LENGTH_SHORT).show();
                                        }

                                    }else{
                                        Toast.makeText(c, "Null response", Toast.LENGTH_SHORT).show();
                                    }
                                    //progressBar.dismis()
                                }

                                @Override
                                public void onError(ANError anError) {
                                    //progressBar.dismis()
                                    Toast.makeText(c, "unsuccessful", Toast.LENGTH_SHORT).show();
                                }
                            });

            }
        }
    }

    private void openGallery(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery",
                "Camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    // String path = saveImage(bitmap);
                    targetUri = data.getData();
                    //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    nouritureImage.setImageBitmap(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(NewNourritureActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");
            targetUri = data.getData();
            nouritureImage.setImageBitmap(bitmap);
            //  saveImage(thumbnail);
            //Toast.makeText(ShadiRegistrationPart5.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }


    }

    //getImage path
    public String getImagePath(Uri uri){
        String [] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
        if (cursor == null){
            return null;
        }
        int columnIndex =  cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(columnIndex);
        cursor.close();
        return s;
    }
     private boolean validateData(){
        if (type.getText().toString() == null || desc.getText().toString() == null || lieu.getText().toString() == null || provenance.getText().toString() == null || quantite.getText().toString() == null || contact.getText().toString() == null )
        {
            return false;
        }
         if (type.getText().toString() == "" || desc.getText().toString() == "" || lieu.getText().toString() == "" || provenance.getText().toString() == "" || quantite.getText().toString() == "" || contact.getText().toString() == "" )
         {
             return false;
         }
         if (targetUri == null){
            return false;
         }
        return  true;
     }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_nourriture);

        loadingBar =  new ProgressDialog(this);

        nouritureImage = findViewById(R.id.select_food_pic);
        type = findViewById(R.id.typeAliment);
        desc = findViewById(R.id.descAliment);
        provenance = findViewById(R.id.provenance);
        lieu = findViewById( R.id.lieu);
        quantite = findViewById(R.id.quantiteNoutiture);
        contact = findViewById(R.id.contactNoutiture);
        jourRestant = findViewById(R.id.jourRestantNourriture);
        btnValider = findViewById(R.id.btnAddNourriture);
        //btnValider.setEnabled(false);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()){
                    typen = type.getText().toString();
                    descn = desc.getText().toString();
                    provenancen = provenance.getText().toString();
                    lieun = lieu.getText().toString();
                    quantiten = quantite.getText().toString();
                    contactn = contact.getText().toString();
                    jour = jourRestant.getText().toString();

                    NourritureOffert nourritureOffert = new NourritureOffert(typen,descn,provenancen,lieun,quantiten,contactn);

                    new Myuploader(NewNourritureActivity.this).upload(nourritureOffert,type,desc,lieu,provenance,contact);
                }
                else {
                    Toast.makeText(NewNourritureActivity.this, "Invalide", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nouritureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }



    private void clear() {
        type.setText("");
        provenance.setText("");
        quantite.setText("");
        lieu.setText("");
        contact.setText("");
        jourRestant.setText("");
        nouritureImage.setImageResource(R.mipmap.cam);
    }


}
