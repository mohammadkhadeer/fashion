package com.fashion.rest.view.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fashion.rest.R;
import com.fashion.rest.functions.Functions;
import com.fashion.rest.model.ItemTest;
import com.fashion.rest.view.activity.ItemDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.fashion.rest.apiURL.API.apiURLBase;
import static com.fashion.rest.functions.Functions.getTextEngOrLocal;

public class AdapterLoadingType4 extends RecyclerView.Adapter<AdapterLoadingType4.ViewHolder>{

    private final Context context;
    public AdapterLoadingType4
            (Context context)
                {
                     this.context = context;
                }

    public AdapterLoadingType4.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.adapter_loading_typ4, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterLoadingType4.ViewHolder holder, final int position) {

    }


    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.adapter_type4_price_IV) ;

        }

    }

}