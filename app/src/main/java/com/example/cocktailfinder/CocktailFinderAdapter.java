package com.example.cocktailfinder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class CocktailFinderAdapter extends RecyclerView.Adapter<CocktailFinderAdapter.CocktailViewHolder> {

    Cocktails mCocktails;
    CocktailOnClickListener mOnClickListener;

    public CocktailFinderAdapter (CocktailOnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public CocktailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int cocktailItemId = R.layout.cocktail_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View inflatedView = inflater.inflate(cocktailItemId, parent, false);

        return new CocktailViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull CocktailViewHolder holder, int position) {
        String name = mCocktails.drinks.get(position).getAsJsonObject().get("strDrink").getAsString();
        String image = mCocktails.drinks.get(position).getAsJsonObject().get("strDrinkThumb").getAsString();
        holder.mCocktailTextView.setText(name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onClick(name, image);
            }
        });
        Glide.with(holder.itemView.getContext()).load(image).into(holder.mCocktailImageView);
    }

    @Override
    public int getItemCount() {
        return mCocktails.drinks.size();
    }

    public void setCocktails(Cocktails cocktails) {
        mCocktails = cocktails;
    }

    class CocktailViewHolder extends RecyclerView.ViewHolder {
        TextView mCocktailTextView;
        ImageView mCocktailImageView;

        public CocktailViewHolder(@NonNull View itemView) {
            super(itemView);
            mCocktailTextView = (TextView) itemView.findViewById(R.id.tv_cocktail_item);
            mCocktailImageView = (ImageView) itemView.findViewById(R.id.iv_cocktail_item);
        }
    }
}
interface CocktailOnClickListener {
    void onClick(String name, String image);
}
