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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.mind.memory.Model.Url;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewNourritureActivity extends AppCompatActivity {
    private ImageView nouritureImage;
    EditText desc,provenance,lieu,contact,quantite,jourRestant;
    Spinner type;
    private Button btnValider;
    Uri targetUri;
    String typen,typeChoist,provenancen,descn,lieun,quantiten,contactn,dateAjout,jour,randomLey,timeAjout;
    Bitmap bitmap;
    final int PICK_IMAGE_REQUEST = 234;
    private ProgressBar upLoadProgressBar;
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
        private String donRandomKey;


        public NourritureOffert(String donRandomKey,String typeNourritureOffert, String descriptionNourritureOffert, String provenanceNourritureOffert, String lieu, String quantiteNourritureOffert, String numero,String jourRestant,String dateAjoutNourritureOffert) {
            this.typeNourritureOffert = typeNourritureOffert;
            this.descriptionNourritureOffert = descriptionNourritureOffert;
            this.lieu = lieu;
            this.provenanceNourritureOffert = provenanceNourritureOffert;
            this.quantiteNourritureOffert = quantiteNourritureOffert;
            this.numero = numero;
            this.jourRestant = jourRestant;
            this.dateAjoutNourritureOffert = dateAjoutNourritureOffert;
            this.donRandomKey = donRandomKey;
        }

        public String getDonRandomKey() {
            return donRandomKey;
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
        private static final String data_upload_url =Url.url+"offrirNourriture";
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
                upLoadProgressBar.setVisibility(View.VISIBLE);
                //progressBar
                AndroidNetworking.upload(data_upload_url)
                        .addMultipartFile("image",imageFile)
                        .addMultipartParameter("type",s.getTypeNourritureOffert())
                        .addMultipartParameter("desc",s.getDescriptionNourritureOffert())
                        .addMultipartParameter("provenance",s.getProvenanceNourritureOffert())
                        .addMultipartParameter("lieu",s.getLieu())
                        .addMultipartParameter("quantite",s.getQuantiteNourritureOffert())
                        .addMultipartParameter("numero",s.getNumero())
                        .addMultipartParameter("date",s.getDateAjoutNourritureOffert())
                        .addMultipartParameter("donRandomKey",s.getDonRandomKey())
                        .addMultipartParameter("jourRestant",s.getJourRestant())
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
                                        if (message.equalsIgnoreCase("ok")){
                                            Toast.makeText(c, "Don offert avec success", Toast.LENGTH_SHORT).show();
                                            // Intent intent = new Intent(NewNourritureActivity.this,NourritureOffertActivity.class);
                                            //startActivity(intent);

                                        }else {
                                            Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }catch (Exception e){
                                        Toast.makeText(c, "JsonException", Toast.LENGTH_SHORT).show();
                                    }

                                }else{
                                    Toast.makeText(c, "Null response", Toast.LENGTH_SHORT).show();
                                }
                                upLoadProgressBar.setVisibility(View.GONE);
                                //progressBar.dismis()
                            }

                            @Override
                            public void onError(ANError anError) {
                                //progressBar.dismis()
                                upLoadProgressBar.setVisibility(View.GONE);
                                Toast.makeText(c, "une erreur est survenue veillez recommencer", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        }
    }


    private void openGallery(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Choisir une photo");
        String[] pictureDialogItems = {
                "Photo Gallery",
        };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
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

        nouritureImage = findViewById(R.id.select_food_pic);
        type = findViewById(R.id.typeAliment);
        desc = findViewById(R.id.descAliment);
        provenance = findViewById(R.id.provenance);
        lieu = findViewById( R.id.lieu);
        quantite = findViewById(R.id.quantiteNoutiture);
        contact = findViewById(R.id.contactNoutiture);
        jourRestant = findViewById(R.id.jourRestantNourriture);
        btnValider = findViewById(R.id.btnAddNourriture);
        upLoadProgressBar = findViewById(R.id.uploadBar);
        upLoadProgressBar.setVisibility(View.INVISIBLE);


        final List<String> profil = new ArrayList<>();
        profil.add(0,"Précisez le type de don");
        profil.add("Plats");
        profil.add("Fruits ou légumes");
        profil.add("Céréale");
        profil.add("Vêtements ou chessures");
        profil.add("Argent");

        ArrayAdapter<String> dataProfil;
        dataProfil = new ArrayAdapter(this,android.R.layout.simple_spinner_item,profil);
        dataProfil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(dataProfil);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("Choisir un Profil")){
                    typeChoist = "vide";

                }
                else {
                    typeChoist = parent.getItemAtPosition(position).toString();
                    if (typeChoist.equals("Vêtements ou chessures")|| typeChoist.equals("Argent")){
                        jourRestant.setEnabled(false);
                        jourRestant.setText("illimite");
                        btnValider.setText("Ajouter");
                    }
                    else if(typeChoist.equals("Plats")){
                        jourRestant.setEnabled(false);
                        jourRestant.setText("1");
                        btnValider.setText("Proposer");
                    }
                    else {
                        jourRestant.setEnabled(true);
                        btnValider.setText("Ajouter");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                typen = typeChoist;
                descn = desc.getText().toString();
                provenancen = provenance.getText().toString();
                lieun = lieu.getText().toString();
                quantiten = quantite.getText().toString();
                contactn = contact.getText().toString();
                jour = jourRestant.getText().toString();

                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat currentDate = new SimpleDateFormat("MM dd yyyy");
                dateAjout = currentDate.format(calendar.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                timeAjout = currentTime.format(calendar.getTime());

                randomLey = dateAjout + timeAjout;
                if ( descn.equals("") ||provenancen.equals("") ||lieun.equals("") || quantiten.equals("")||contactn.equals("") || jour.equals(""))
                {

                    desc.setError("requis");provenance.setError("requis"); lieu.setError("requis");
                    quantite.setError("requis");contact.setError("requis");jourRestant.setError("requis");
                    Toast.makeText(NewNourritureActivity.this, "Veillez remplir tous les champs", Toast.LENGTH_LONG).show();

                 }
                else if(targetUri == null)
                {
                    Toast.makeText(NewNourritureActivity.this, "Veillez choisir une image", Toast.LENGTH_LONG).show();
                }
                else if(typen.equals("Précisez le type de don")){
                    Toast.makeText(NewNourritureActivity.this, "Précisez le type de don", Toast.LENGTH_LONG).show();
                }
                else {

                    clear();

                    NourritureOffert nourritureOffert = new NourritureOffert(randomLey,typen,descn,provenancen,lieun,quantiten,contactn,jour,dateAjout);

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
        desc.setText("");
        provenance.setText("");
        quantite.setText("");
        lieu.setText("");
        contact.setText("");
        jourRestant.setText("");
        nouritureImage.setImageResource(R.mipmap.cam);
    }


}
