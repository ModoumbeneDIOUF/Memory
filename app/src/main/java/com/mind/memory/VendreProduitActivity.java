package com.mind.memory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private EditText vendreQuantite, vendrePrix, vendreExpiretion;
    String quantite, prix, expiration, saveCurrentDate, saveCurrentTime;
    private ImageView vendreImage;
    private Button vendreBtn;
    private Uri imageUri;
    private String produitRandomKey, dowloadImageUri;
    private StorageReference produitImageRef;
    private DatabaseReference produitRef;
    private ProgressDialog loadingBar;

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
            Toast.makeText(this, "Vous devez mettre l'image du produit", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(quantite) || TextUtils.isEmpty(prix) || TextUtils.isEmpty(expiration)) {
            Toast.makeText(this, "Veillez remplirre tous les champs svp", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(VendreProduitActivity.this, "Erreur " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(VendreProduitActivity.this, "Image enrigistré avec succes", Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(VendreProduitActivity.this, "getting url success", Toast.LENGTH_SHORT).show();

                            saveProduitToDatabase();
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
                            Toast.makeText(VendreProduitActivity.this, "Produit ajouter avec success", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(VendreProduitActivity.this, "Erreur de l'ajout du produit", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}


