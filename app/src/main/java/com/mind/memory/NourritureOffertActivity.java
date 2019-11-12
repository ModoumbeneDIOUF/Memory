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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mind.memory.Model.NourritureOffer;
import com.mind.memory.ViewHolder.NorritureOfertViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

public class NourritureOffertActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private DatabaseReference NourritureRef;
    Context context;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nourriture_offert);

        recyclerView = findViewById(R.id.listNourritureOffert);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        progressDialog = new ProgressDialog(NourritureOffertActivity.this);

        NourritureRef = FirebaseDatabase.getInstance().getReference().child("Nourriture");

        //listView = findViewById(R.id.listNourritureOffert);
        //models = DetailNourritureOffert.getListOffert();

    }

    @Override
    protected void onStart() {

        super.onStart();

        NourritureRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseRecyclerOptions<NourritureOffer> options =
                new FirebaseRecyclerOptions.Builder<NourritureOffer>()
                        .setQuery(NourritureRef,NourritureOffer.class)
                        .build();

        FirebaseRecyclerAdapter<NourritureOffer,NorritureOfertViewHolder> adapter =
                new FirebaseRecyclerAdapter<NourritureOffer, NorritureOfertViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull NorritureOfertViewHolder holder, int position, @NonNull final NourritureOffer model) {
                       calendar = Calendar.getInstance();
                       int day = calendar.get(Calendar.DAY_OF_MONTH);

                        holder.typeNourriture.setText(" Type de nourriture: "+model.getDescription());
                        holder.proveNourriture.setText("Provenance: "+model.getProvenance());
                        holder.adressNourriture.setText("Adresse: "+model.getLieu());

                        holder.jourRestantNou.setText("Jours restant:" +model.getJourRestant());

                        holder.contactNourriture.setText("Numero: "+model.getNumero());
                        Picasso.get().load(model.getImage()).resize(500,500 ).into(holder.imageViewNourriture);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(NourritureOffertActivity.this,RecupererNourritureActivity.class);
                                intent.putExtra("nourritureId",model.getNourritureId());
                                startActivity(intent);
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
