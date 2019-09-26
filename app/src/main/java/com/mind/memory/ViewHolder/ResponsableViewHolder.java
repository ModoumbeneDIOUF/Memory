package com.mind.memory.ViewHolder;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mind.memory.Interface.ItemClickListener;
import com.mind.memory.Model.Responsable;
import com.mind.memory.R;

import java.util.List;

public class ResponsableViewHolder {
    private Context context;
    private ResponsableAdapter responsableAdapter;

    public void setConfig(RecyclerView recyclerView,Context mcontext,List<Responsable>responsables,List<String> keys){
        context = mcontext;
        responsableAdapter = new ResponsableAdapter(responsables,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
        recyclerView.setAdapter(responsableAdapter);
    }

    class ResponsableItemView extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nomR,adresseR,numR;
        private String key;
        public ItemClickListener listener;

        public ResponsableItemView(ViewGroup parent){
            super(LayoutInflater.from(context).
            inflate(R.layout.list_reponsable,parent,false));

            nomR = itemView.findViewById(R.id.nom_responsable);
            adresseR = itemView.findViewById(R.id.adresse_responsable);
            numR = itemView.findViewById(R.id.numero_responsable);

        }

        public void bind(Responsable responsable,String key){
            nomR.setText(responsable.getPrenom()+" "+responsable.getNom());
            adresseR.setText(responsable.getAdresse());
            numR.setText(responsable.getPhone());
            this.key = key;
        }

        public void setItemClickListener(ItemClickListener listener){

            this.listener = listener;
        }
        @Override
        public void onClick(View v) {
            listener.onclick(v,getAdapterPosition(),false);
        }
    }

    class ResponsableAdapter extends RecyclerView.Adapter<ResponsableItemView>{

        private List<Responsable> mresponsables;
        private List<String> mkeys;
        String number = "551191111113";
        Intent intent;

        public ResponsableAdapter (List<Responsable> mresponsables,List<String> mkeys){
            this.mresponsables = mresponsables;
            this.mkeys = mkeys;
        }

        public ResponsableAdapter() {
            super();
        }

        @NonNull
        @Override
        public ResponsableItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ResponsableItemView(viewGroup);

        }

        @Override
        public void onBindViewHolder(@NonNull ResponsableItemView holder, int position) {
            holder.bind(mresponsables.get(position),mkeys.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });
        }

        @Override
        public int getItemCount() {
            return mresponsables.size();
        }
    }


}
