package com.mind.memory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class NewNourritureActivity extends AppCompatActivity {
    private ImageView nouritureImage;
    private EditText type,provenance,lieu,contact,quantite,jourRestant;
    private static final int galleryPick=1;
    private Button btnValider;
    private Uri imageUri,targetUri;
    String typen,provenancen,lieun,quantiten,contactn,saveCurrentDate,saveCurrentTime,jour;
    int day;
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
        jourRestant = findViewById(R.id.jourRestantNourriture);
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
       CropImage.activity().start(NewNourritureActivity.this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if (resultCode == RESULT_OK){
                    targetUri = result.getUri();
                    nouritureImage.setImageURI(targetUri);
                }
            }


        }


    private void ValidateNourritureData() {
        typen = type.getText().toString();
        provenancen = provenance.getText().toString();
        lieun = lieu.getText().toString();
        quantiten = quantite.getText().toString();
        contactn = contact.getText().toString();
        jour = jourRestant.getText().toString();

        if (targetUri == null)
        {
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER,0,0);
            TextView tv = new TextView(NewNourritureActivity.this);
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

        }
        else if(TextUtils.isEmpty(typen) || TextUtils.isEmpty(jour) || TextUtils.isEmpty(provenancen) || TextUtils.isEmpty(lieun) || TextUtils.isEmpty(contactn) || TextUtils.isEmpty(quantiten))
        {
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER,0,0);
            TextView tv = new TextView(NewNourritureActivity.this);
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

        day = calendar.get(Calendar.DAY_OF_MONTH);

        nourritureRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = nourritureImageRef.child(targetUri.getLastPathSegment() + nourritureRandomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(targetUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                    String message = e.toString();
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER,0,0);
                TextView tv = new TextView(NewNourritureActivity.this);
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

                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

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
                            //Toast.makeText(NewNourritureActivity.this, "getting url success", Toast.LENGTH_SHORT).show();

                            saveNourritureToDatabase();
                            clear();
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
        nourritureHashmap.put("jourRestant",jour+day);
        nourritureHashmap.put("numero",contactn);

        nourritureRef.child(nourritureRandomKey).updateChildren(nourritureHashmap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            loadingBar.dismiss();

                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER,0,0);
                            TextView tv = new TextView(NewNourritureActivity.this);
                            tv.setBackgroundColor(Color.WHITE);
                            tv.setTextColor(Color.BLUE);
                            tv.setTextSize(15);

                            Typeface t = Typeface.create("serif",Typeface.BOLD_ITALIC);
                            tv.setTypeface(t);
                            tv.setPadding(10,10,10,10);
                            tv.setText("Nourriture offert avec succes");
                            toast.setView(tv);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.show();
                            //Toast.makeText(NewNourritureActivity.this, "Nourriture offert avec success", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            loadingBar.dismiss();

                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER,0,0);
                            TextView tv = new TextView(NewNourritureActivity.this);
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
                           // Toast.makeText(NewNourritureActivity.this, "Erreur de l'ajout de la nourriture", Toast.LENGTH_SHORT).show();

                        }
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
