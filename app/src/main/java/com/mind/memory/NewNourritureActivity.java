package com.mind.memory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.mind.memory.Model.Url;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

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

        public NourritureOffert(String typeNourritureOffert, String descriptionNourritureOffert, String provenanceNourritureOffert, String lieu, String quantiteNourritureOffert, String numero) {
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
        private static final String data_upload_url = "http://10.156.116.112/back/public/api/offrirNourriture";
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
                                            if (message.equalsIgnoreCase("ok")){
                                                Toast.makeText(c, "Success", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(NewNourritureActivity.this,NourritureOffertActivity.class);
                                                startActivity(intent);
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
        pictureDialog.setTitle("Selectionnez une action");
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


        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    typen = type.getText().toString();
                    descn = desc.getText().toString();
                    provenancen = provenance.getText().toString();
                    lieun = lieu.getText().toString();
                    quantiten = quantite.getText().toString();
                    contactn = contact.getText().toString();

                    if (typen.equals("") || descn.equals("") ||provenancen.equals("") ||lieun.equals("") || quantiten.equals("")||contactn.equals(""))
                    {
                        type.setError("requis");desc.setError("requis");provenance.setError("requis"); lieu.setError("requis");
                        quantite.setError("requis");contact.setError("requis");
                        Toast.makeText(NewNourritureActivity.this, "Veillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    }
                    else if(targetUri == null)
                    {
                        Toast.makeText(NewNourritureActivity.this, "Veillez choisir une image", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        NourritureOffert nourritureOffert = new NourritureOffert(typen,descn,provenancen,lieun,quantiten,contactn);

                        new Myuploader(NewNourritureActivity.this).upload(nourritureOffert,type,desc,lieu,provenance,contact);

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
