package com.mind.memory;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.mind.memory.Model.Url;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

public class VendreProduitActivity extends AppCompatActivity {
    private EditText venteDesc,venteAdresse,venteNumero,vendreQuantite, vendrePrix;
    private TextView vendreExpiretion;
    Spinner typeVendu;
    String produitRandomKey,typeChoist,typen,desc,adr,numero,quantite, prix, expiration ="",dateVente,timeAjout,saveCurrentDate, saveCurrentTime;
    private ImageView vendreImage;
    private Button vendreBtn;
    public Uri imageUri;
    Bitmap bitmap;
    private int GALLERY = 1;
    private DatePickerDialog datePickerDialog;
    int year,month,dayOfMonth;
    Calendar calendar;

    private ProgressDialog loadingBar;
    private DatePickerDialog.OnDateSetListener dateSetListener;

     /*
        produit vendu class
    */
        class VendreProduit{
         private String typeProduitVendu;
         private String descriptionProduitVendu;
         private String adresseProduitVendu;
         private String prixProduitVendu;
         private String quantiteProduitVendu;
         private String numero;
         private String validiteProduitVendu;
         private String dateVente;
         private String venteRandomKey;

         public VendreProduit(String typeProduitVendu, String descriptionProduitVendu, String adresseProduitVendu, String prixProduitVendu, String quantiteProduitVendu, String numero, String validiteProduitVendu, String dateVente, String venteRandomKey) {
             this.typeProduitVendu = typeProduitVendu;
             this.descriptionProduitVendu = descriptionProduitVendu;
             this.adresseProduitVendu = adresseProduitVendu;
             this.prixProduitVendu = prixProduitVendu;
             this.quantiteProduitVendu = quantiteProduitVendu;
             this.numero = numero;
             this.validiteProduitVendu = validiteProduitVendu;
             this.dateVente = dateVente;
             this.venteRandomKey = venteRandomKey;
         }

         public String getTypeProduitVendu() {
             return typeProduitVendu;
         }

         public String getDescriptionProduitVendu() {
             return descriptionProduitVendu;
         }

         public String getAdresseProduitVendu() {
             return adresseProduitVendu;
         }

         public String getPrixProduitVendu() {
             return prixProduitVendu;
         }

         public String getQuantiteProduitVendu() {
             return quantiteProduitVendu;
         }

         public String getNumero() {
             return numero;
         }

         public String getValiditeProduitVendu() {
             return validiteProduitVendu;
         }

         public String getDateVente() {
             return dateVente;
         }

         public String getVenteRandomKey() {
             return venteRandomKey;
         }
     }
        /*
     Upload class
 */
    public class Myuploader{
            private static final String data_upload_url =Url.url+"vendreProduit";
            private final Context c;

            public  Myuploader(Context c){this.c = c;}

            public void upload(VendreProduit s,View...inputView){

                if (s == null){
                    Toast.makeText(c, "Pas de donnés à enrigistrer", Toast.LENGTH_LONG).show();
                }
                else {
                    File imageFile;
                    try {
                        imageFile = new File(getImagePath(imageUri));

                    }catch (Exception e){
                        Toast.makeText(c, "Veillez choisir une image", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    AndroidNetworking.upload(data_upload_url)
                            .addMultipartFile("image",imageFile)
                            .addMultipartParameter("typeProduitVendu",s.getTypeProduitVendu())
                            .addMultipartParameter("descriptionProduitVendu",s.getDescriptionProduitVendu())
                            .addMultipartParameter("adresseProduitVendu",s.getAdresseProduitVendu())
                            .addMultipartParameter("numero",s.getNumero())
                            .addMultipartParameter("quantiteProduitVendu",s.getQuantiteProduitVendu())
                            .addMultipartParameter("prixProduitVendu",s.getPrixProduitVendu())
                            .addMultipartParameter("validiteProduitVendu",s.getValiditeProduitVendu())
                            .addMultipartParameter("dateVente",s.getDateVente())
                            .addMultipartParameter("venteRandomKey",s.getVenteRandomKey())
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
                                                Toast.makeText(c, "Produit ajouter avec success", Toast.LENGTH_LONG).show();
                                                // Intent intent = new Intent(NewNourritureActivity.this,NourritureOffertActivity.class);
                                                //startActivity(intent);

                                            }else {
                                                Toast.makeText(c, message, Toast.LENGTH_LONG).show();
                                            }
                                        }catch (Exception e){
                                            Toast.makeText(c, "JsonException", Toast.LENGTH_LONG).show();
                                        }

                                    }else{
                                        Toast.makeText(c, "Null response", Toast.LENGTH_LONG).show();
                                    }
                                   // upLoadProgressBar.setVisibility(View.GONE);
                                    //progressBar.dismis()
                                }

                                @Override
                                public void onError(ANError anError) {
                                    //progressBar.dismis()
                                    //upLoadProgressBar.setVisibility(View.GONE);
                                    Toast.makeText(c, ""+anError.toString(), Toast.LENGTH_LONG).show();
                                }
                            });

                }
            }
        }
    private void openGallery() {
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
                    imageUri = data.getData();
                    //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    vendreImage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                        Toast.makeText(VendreProduitActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_vendre_produit);


        loadingBar = new ProgressDialog(this);
        typeVendu = findViewById(R.id.typVendu);
        venteDesc = findViewById(R.id.vendreDes);
        venteAdresse = findViewById(R.id.vendreAdresse);
        venteNumero = findViewById(R.id.vendreNumero);
        vendreQuantite = findViewById(R.id.vendreQuantite);
        vendrePrix = findViewById(R.id.vendrePrix);
        vendreExpiretion = findViewById(R.id.vendreExpiration);
        vendreImage = findViewById(R.id.vendreImage);
        vendreBtn = findViewById(R.id.vendreBtn);

        final List<String> typeV = new ArrayList<>();
        typeV.add(0,"Précisez le type du produit");
        typeV.add("Cosmétique");
        typeV.add("Fruits ou légumes");
        typeV.add("Céréale");
        typeV.add("Vêtements ou chessures");

        ArrayAdapter<String> datatype;
        datatype = new ArrayAdapter(this,android.R.layout.simple_spinner_item,typeV);
        datatype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeVendu.setAdapter(datatype);
        typeVendu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Précisez le type du produit")){
                    typeChoist = "vide";
                }
                else {
                    typeChoist = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        vendreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        vendreExpiretion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(VendreProduitActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year,month,dayOfMonth);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                datePickerDialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                expiration = month + "/" + day + "/" + year;
                vendreExpiretion.setText(expiration);
            }
        };
        vendreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                typen = typeChoist;
                desc = venteDesc.getText().toString();
                adr = venteAdresse.getText().toString();
                prix = vendrePrix.getText().toString();
                numero = venteNumero.getText().toString();
                quantite = vendreQuantite.getText().toString();

                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat currentDate = new SimpleDateFormat("MM dd yyyy");
                dateVente = currentDate.format(calendar.getTime());

                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                timeAjout = currentTime.format(calendar.getTime());

                produitRandomKey = dateVente + timeAjout;
                if (desc.equals("") || adr.equals("") || prix.equals("") || numero.equals("") || quantite.equals("")){
                    venteDesc.setError("requis");
                    venteAdresse.setError("requis");
                    vendrePrix.setError("requis");
                    venteNumero.setError("requis");
                    vendreQuantite.setError("requis");

                }else if(imageUri == null){
                    Toast.makeText(VendreProduitActivity.this, "Veillez mettre l'image du produit", Toast.LENGTH_SHORT).show();
                }
                else if (expiration.equals("")){
                    Toast.makeText(VendreProduitActivity.this, "Veillez préciser la date d'expiration", Toast.LENGTH_SHORT).show();
                }
                else if(typen.equals("vide")){
                    Toast.makeText(VendreProduitActivity.this, "Veillez préciser le type du produit", Toast.LENGTH_LONG).show();
                }
                else {
                        clear();
                        VendreProduit vendreProduit = new VendreProduit(typeChoist,desc,adr,prix,quantite,numero,expiration,dateVente,produitRandomKey);
                        new Myuploader(VendreProduitActivity.this).upload(vendreProduit);
                }
            }
        });

    }

    private void clear() {
        vendreQuantite.setText("");
        vendreExpiretion.setText("");
        vendrePrix.setText("");
        venteNumero.setText("");
        venteAdresse.setText("");
        venteDesc.setText("");
        typeChoist = "Précisez le type du produit";
        vendreImage.setImageResource(R.mipmap.cam);
    }
}


