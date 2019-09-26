package com.mind.memory;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mind.memory.Model.NourritureOffer;
import com.mind.memory.ViewHolder.NorritureOfertViewHolder;
import com.squareup.picasso.Picasso;

public class AdminOffertActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private DatabaseReference NourritureRef;
    Context context;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_offert);

        recyclerView = findViewById(R.id.admin_listNourritureOffert);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        progressDialog = new ProgressDialog(AdminOffertActivity.this);

        NourritureRef = FirebaseDatabase.getInstance().getReference().child("Nourriture");



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<NourritureOffer> options =
                new FirebaseRecyclerOptions.Builder<NourritureOffer>()
                        .setQuery(NourritureRef,NourritureOffer.class)
                        .build();

        FirebaseRecyclerAdapter<NourritureOffer,NorritureOfertViewHolder> adapter =
                new FirebaseRecyclerAdapter<NourritureOffer, NorritureOfertViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final NorritureOfertViewHolder holder, int position, @NonNull final NourritureOffer model) {
                        holder.typeNourriture.setText(model.getDescription());
                        holder.proveNourriture.setText(model.getProvenance());
                        holder.adressNourriture.setText(model.getLieu());
                        holder.contactNourriture.setText(model.getNumero());
                        Picasso.get().load(model.getImage()).resize(500,500 ).into(holder.imageViewNourriture);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminOffertActivity.this);
                                builder.setTitle("Suppression");builder.setMessage("Voulez-vous vraiment supprimer cette offre ?");
                                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Query query =NourritureRef.orderByChild("nourritureId").equalTo(model.getNourritureId());
                                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot ds:dataSnapshot.getChildren()){
                                                    ds.getRef().removeValue();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        StorageReference imrageRef = FirebaseStorage.getInstance().getReferenceFromUrl(model.getImage());
                                        imrageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });
                                    }
                                });
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                builder.create().show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public NorritureOfertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_nourriture_added,parent,false);
                        NorritureOfertViewHolder holder = new NorritureOfertViewHolder(view);
                        return holder;
                    }
                };


        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}
