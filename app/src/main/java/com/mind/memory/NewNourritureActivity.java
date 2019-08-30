package com.mind.memory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class NewNourritureActivity extends AppCompatActivity {
    private ImageView nouritureImage;
    private EditText type,provenance,lieu,contact,quantite;
    private static final int galleryPick=1;
    private Button btnValider;
    private Uri imageUri,targetUri;
    String typen,provenancen,lieun,quantiten,contactn,saveCurrentDate,saveCurrentTime;
    private String nourritureRandomKey,dowloadImageUri;
    private StorageReference nourritureImageRef;
    private DatabaseReference nourritureRef;
    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_nourriture);

        nourritureImageRef = FirebaseStorage.getInstance().getReference().child("Images Nourriture");
        nourritureRef = FirebaseDatabase.getInstance().getReference().child("Nourriture");

        loadingBar =  new ProgressDialog(this);

        nouritureImage = findViewById(R.id.select_food_pic);
        type = findViewById(R.id.typeAliment);
        provenance = findViewById(R.id.provenance);
        lieu = findViewById( R.id.lieu);
        quantite = findViewById(R.id.quantiteNoutiture);
        contact = findViewById(R.id.contactNoutiture);
        btnValider = findViewById(R.id.btnAddNourriture);


        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateNourritureData();
            }
        });


        nouritureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }


    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
             targetUri = data.getData();

            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                nouritureImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        }


    private void ValidateNourritureData() {
        typen = type.getText().toString();
        provenancen = provenance.getText().toString();
        lieun = lieu.getText().toString();
        quantiten = quantite.getText().toString();
        contactn = contact.getText().toString();

        if (targetUri == null)
        {
            Toast.makeText(this, "Vous avez oublié de mettre l'image de la nourriture", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(typen) || TextUtils.isEmpty(provenancen) || TextUtils.isEmpty(lieun) || TextUtils.isEmpty(contactn) || TextUtils.isEmpty(quantiten))
        {
            Toast.makeText(this, "Tous les champs sont requis", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StorageNouritureInfo();
        }
    }

    private void StorageNouritureInfo() {
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

        nourritureRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = nourritureImageRef.child(targetUri.getLastPathSegment() + nourritureRandomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(targetUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                    String message = e.toString();
                Toast.makeText(NewNourritureActivity.this, "Erreur "+message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(NewNourritureActivity.this, "Image enrigistré avec succes", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }

                        dowloadImageUri = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            dowloadImageUri = task.getResult().toString();
                            Toast.makeText(NewNourritureActivity.this, "getting url success", Toast.LENGTH_SHORT).show();

                            saveNourritureToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void saveNourritureToDatabase() {
        HashMap<String,Object> nourritureHashmap = new HashMap<>();
        nourritureHashmap.put("nourritureId",nourritureRandomKey);
        nourritureHashmap.put("date",saveCurrentDate);
        nourritureHashmap.put("time",saveCurrentTime);
        nourritureHashmap.put("description",typen);
        nourritureHashmap.put("provenance",provenancen);
        nourritureHashmap.put("lieu",lieun);
        nourritureHashmap.put("image",dowloadImageUri);
        nourritureHashmap.put("quantite",quantiten);
        nourritureHashmap.put("numero",contactn);

        nourritureRef.child(nourritureRandomKey).updateChildren(nourritureHashmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            loadingBar.dismiss();
                            Toast.makeText(NewNourritureActivity.this, "Nourriture offert avec success", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(NewNourritureActivity.this, "Erreur de l'ajout de la nourriture", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


}
