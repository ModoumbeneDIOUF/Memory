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
import com.mind.memory.Model.ListVente;
import com.mind.memory.ViewHolder.ProduitVenduViewHolder;
import com.squareup.picasso.Picasso;

public class AdminProduitVenduActivity extends AppCompatActivity {
    Context context;
    private DatabaseReference VenduRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_produit_vendu);

        recyclerView = findViewById(R.id.admin_listProduitVendre);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        VenduRef = FirebaseDatabase.getInstance().getReference().child("ProduitVendu");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<ListVente> options = new
                FirebaseRecyclerOptions.Builder<ListVente>()
                .setQuery(VenduRef,ListVente.class)
                .build();

        FirebaseRecyclerAdapter<ListVente,ProduitVenduViewHolder> adapter =
                new FirebaseRecyclerAdapter<ListVente, ProduitVenduViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProduitVenduViewHolder holder, int position, @NonNull final ListVente model) {

                        holder.quantiteVendu.setText(model.getQuantiteVendu());
                        holder.dateExpirationVendu.setText(model.getDateExpiration());
                        holder.prixVendu.setText(model.getPrixVente());
                        Picasso.get().load(model.getImageVented()).into(holder.imageNourritureVendu);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminProduitVenduActivity.this);
                                builder.setTitle("Suppression");builder.setMessage("Voulez-vous vraiment supprimer ce produit ?");
                                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Query query =VenduRef.orderByChild("produitVenduId").equalTo(model.getProduitVenduId());
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
                                        StorageReference imrageRef = FirebaseStorage.getInstance().getReferenceFromUrl(model.getImageVented());
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
                    public ProduitVenduViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produit_a_vendre,parent,false);
                        ProduitVenduViewHolder holder = new ProduitVenduViewHolder(view);
                        return holder;
                    }
                };


        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}
