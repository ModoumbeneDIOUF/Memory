package com.mind.memory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mind.memory.Model.ListVente;
import com.mind.memory.ViewHolder.ProduitVenduViewHolder;
import com.squareup.picasso.Picasso;

public class ProduitVenduActivity extends AppCompatActivity {
    private ListView listView;
    Context context;
    private ProgressDialog progressDialog;
    private DatabaseReference VenduRef;

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit_vendu);

        recyclerView = findViewById(R.id.listProduitVendre);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        progressDialog = new ProgressDialog(ProduitVenduActivity.this);

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
                                Intent intent = new Intent(ProduitVenduActivity.this,AcheterUnProduitActivity.class);
                                intent.putExtra("produitVenduId",model.getProduitVenduId());
                                startActivity(intent);
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
