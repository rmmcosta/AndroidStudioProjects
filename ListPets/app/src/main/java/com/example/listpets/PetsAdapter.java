package com.example.listpets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.PetsViewHolder> {
    private String[] petsNames, petsDescriptions;
    private int[] petsImages;
    private Context context;

    public PetsAdapter(String[] petsNames, String[] petsDescriptions, int[] petsImages, Context context) {
        this.petsNames = petsNames;
        this.petsDescriptions = petsDescriptions;
        this.petsImages = petsImages;
        this.context = context;
    }

    @NonNull
    @Override
    public PetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //we need to inflate the pet item layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.pet_item, parent, false);
        return new PetsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PetsViewHolder holder, int position) {
        holder.petImage.setImageResource(petsImages[position]);
        holder.petDescription.setText(petsDescriptions[position]);
        holder.petName.setText(petsNames[position]);
    }

    @Override
    public int getItemCount() {
        return petsNames.length;
    }

    public class PetsViewHolder extends RecyclerView.ViewHolder {
        private TextView petName, petDescription;
        private ImageView petImage;
        public PetsViewHolder(@NonNull View itemView) {
            super(itemView);
            petName = itemView.findViewById(R.id.tvPetName);
            petDescription = itemView.findViewById(R.id.tvPetDescription);
            petImage = itemView.findViewById(R.id.ivPet);
        }
    }
}
