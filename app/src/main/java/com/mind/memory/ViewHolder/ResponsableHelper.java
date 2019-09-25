package com.mind.memory.ViewHolder;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mind.memory.Model.Responsable;

import java.util.ArrayList;
import java.util.List;

public class ResponsableHelper {
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private List<Responsable> responsables = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoad(List<Responsable> rest,List<String> keys);
    }

    public ResponsableHelper() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
    }
    public void readResponsable(final DataStatus dataStatus){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                responsables.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode:dataSnapshot.getChildren()){
                    String yes = keyNode.child("profil").getValue().toString();
                    if (yes.equals("Volontaire")){
                        keys.add(keyNode.getKey());
                        Responsable responsable = keyNode.getValue(Responsable.class);
                        responsables.add(responsable);
                    }

                }
                dataStatus.DataIsLoad(responsables,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
