package com.mind.memory.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mind.memory.Interface.ItemClickListener;
import com.mind.memory.R;

public class ProduitVenduViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView quantiteVendu,prixVendu,dateExpirationVendu;
    public ImageView imageNourritureVendu;
    public ItemClickListener listener;

    public ProduitVenduViewHolder(@NonNull View itemView) {
        super(itemView);

        quantiteVendu = itemView.findViewById(R.id.listVenteQuantite);
        prixVendu = itemView.findViewById(R.id.listVentePrix);
        dateExpirationVendu = itemView.findViewById(R.id.listVenteDate);
        imageNourritureVendu = itemView.findViewById(R.id.list_vente_image);
    }

    public void setItemClickListener(ItemClickListener listener){

        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onclick(view,getAdapterPosition(),false);
    }

}
