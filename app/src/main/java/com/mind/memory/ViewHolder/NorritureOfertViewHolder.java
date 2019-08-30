package com.mind.memory.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mind.memory.Interface.ItemClickListener;
import com.mind.memory.R;

public class NorritureOfertViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView typeNourriture,proveNourriture,quantite,adressNourriture,contactNourriture,jourRestantNou;
    public ImageView imageViewNourriture;
    public ItemClickListener listener;

    public NorritureOfertViewHolder(@NonNull View itemView) {
        super(itemView);

        imageViewNourriture = itemView.findViewById(R.id.list_nourriture_pic);
        typeNourriture = itemView.findViewById(R.id.listTypeNourriture);
        proveNourriture = itemView.findViewById(R.id.provenanceNourriture);
        adressNourriture = itemView.findViewById(R.id.listAdresse);
        contactNourriture = itemView.findViewById(R.id.listContact);
        jourRestantNou = itemView.findViewById(R.id.tempsRestant);

    }

    public void setItemClickListener(ItemClickListener listener){

            this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onclick(view,getAdapterPosition(),false);
    }

}
