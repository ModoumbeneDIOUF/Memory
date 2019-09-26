package com.mind.memory;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class VendreProduitActivity extends AppCompatActivity {
    private EditText vendreQuantite, vendrePrix;
    private TextView vendreExpiretion;
    String quantite, prix, expiration, saveCurrentDate, saveCurrentTime;
    private ImageView vendreImage;
    private Button vendreBtn;
    private Uri imageUri;
    private DatePickerDialog datePickerDialog;
    int year,month,dayOfMonth;
    Calendar calendar;
    private String produitRandomKey, dowloadImageUri;
    private StorageReference produitImageRef;
    private DatabaseReference produitRef;
    private ProgressDialog loadingBar;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendre_produit);

        produitImageRef = FirebaseStorage.getInstance().getReference().child("Images Produit Vendu");
        produitRef = FirebaseDatabase.getInstance().getReference().child("ProduitVendu");

        loadingBar = new ProgressDialog(this);

        vendreQuantite = findViewById(R.id.vendreQuantite);
        vendrePrix = findViewById(R.id.vendrePrix);
        vendreExpiretion = findViewById(R.id.vendreExpiration);
        vendreImage = findViewById(R.id.vendreImage);
        vendreBtn = findViewById(R.id.vendreBtn);

        vendreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidateProduitData();
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

                String date = month + "/" + day + "/" + year;
                vendreExpiretion.setText(date);
            }
        };

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            imageUri = data.getData();

            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                vendreImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    private void ValidateProduitData() {
        quantite = vendreQuantite.getText().toString();
        prix = vendrePrix.getText().toString();
        expiration = vendreExpiretion.getText().toString();

        if (imageUri == null) {

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER,0,0);
            TextView tv = new TextView(VendreProduitActivity.this);
            tv.setBackgroundColor(Color.WHITE);
            tv.setTextColor(Color.RED);
            tv.setTextSize(15);

            Typeface t = Typeface.create("serif",Typeface.BOLD_ITALIC);
            tv.setTypeface(t);
            tv.setPadding(10,10,10,10);
            tv.setText("Veillez mettre l'image de la nourriture svp");
            toast.setView(tv);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();

           // Toast.makeText(this, "Vous devez mettre l'image du produit", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(quantite) || TextUtils.isEmpty(prix) || TextUtils.isEmpty(expiration)) {
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER,0,0);
            TextView tv = new TextView(VendreProduitActivity.this);
            tv.setBackgroundColor(Color.WHITE);
            tv.setTextColor(Color.RED);
            tv.setTextSize(15);

            Typeface t = Typeface.create("serif",Typeface.BOLD_ITALIC);
            tv.setTypeface(t);
            tv.setPadding(10,10,10,10);
            tv.setText("Tous les champs sont");
            toast.setView(tv);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();

            //Toast.makeText(this, "Veillez remplirre tous les champs svp", Toast.LENGTH_SHORT).show();

        } else {
            StorageProduitInfo();
        }
    }

    private void StorageProduitInfo() {
        loadingBar.setTitle("Enrigistrement");
        loadingBar.setMessage("Veillez patienter un instant nous traitons votre demande");
        loadingBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingBar.setMax(100);
        loadingBar.getMax();
        loadingBar.getProgress();
        loadingBar.incrementProgressBy(2);
        loadingBar.setCancelable(false);
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        produitRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = produitImageRef.child(imageUri.getLastPathSegment() + produitRandomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER,0,0);
                TextView tv = new TextView(VendreProduitActivity.this);
                tv.setBackgroundColor(Color.WHITE);
                tv.setTextColor(Color.RED);
                tv.setTextSize(15);

                Typeface t = Typeface.create("serif",Typeface.BOLD_ITALIC);
                tv.setTypeface(t);
                tv.setPadding(10,10,10,10);
                tv.setText(message);
                toast.setView(tv);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();

                //Toast.makeText(VendreProduitActivity.this, "Erreur " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               // Toast.makeText(VendreProduitActivity.this, "Image enrigistré avec succes", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        dowloadImageUri = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            dowloadImageUri = task.getResult().toString();
                            //Toast.makeText(VendreProduitActivity.this, "getting url success", Toast.LENGTH_SHORT).show();

                            saveProduitToDatabase();
                            clear();
                        }
                    }
                });

            }
        });
    }

    private void saveProduitToDatabase() {
        HashMap<String,Object> produitHashmap = new HashMap<>();
        produitHashmap.put("produitVenduId",produitRandomKey);
        produitHashmap.put("date",saveCurrentDate);
        produitHashmap.put("time",saveCurrentTime);
        produitHashmap.put("quantiteVendu", quantite);
        produitHashmap.put("prixVente",prix);
        produitHashmap.put("dateExpiration",expiration);
        produitHashmap.put("imageVented",dowloadImageUri);

        produitRef.child(produitRandomKey).updateChildren(produitHashmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            loadingBar.dismiss();

                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER,0,0);
                            TextView tv = new TextView(VendreProduitActivity.this);
                            tv.setBackgroundColor(Color.WHITE);
                            tv.setTextColor(Color.BLUE);
                            tv.setTextSize(15);

                            Typeface t = Typeface.create("serif",Typeface.BOLD_ITALIC);
                            tv.setTypeface(t);
                            tv.setPadding(10,10,10,10);
                            tv.setText("Produit publié avec succes");
                            toast.setView(tv);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.show();
                            //Toast.makeText(VendreProduitActivity.this, "Produit ajouter avec success", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            loadingBar.dismiss();

                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER,0,0);
                            TextView tv = new TextView(VendreProduitActivity.this);
                            tv.setBackgroundColor(Color.WHITE);
                            tv.setTextColor(Color.RED);
                            tv.setTextSize(15);

                            Typeface t = Typeface.create("serif",Typeface.BOLD_ITALIC);
                            tv.setTypeface(t);
                            tv.setPadding(10,10,10,10);
                            tv.setText("Une erreur est survenue veillez recomencer");
                            toast.setView(tv);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.show();

                            //Toast.makeText(VendreProduitActivity.this, "Erreur de l'ajout du produit", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void clear() {
        vendreQuantite.setText("");
        vendreExpiretion.setText("");
        vendrePrix.setText("");
        vendreImage.setImageResource(R.mipmap.cam);
    }
}


