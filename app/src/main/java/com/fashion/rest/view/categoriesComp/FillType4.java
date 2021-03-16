package com.fashion.rest.view.categoriesComp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fashion.rest.R;
import com.fashion.rest.model.Home;
import com.fashion.rest.model.ItemTest;
import com.fashion.rest.presnter.JsonPlaceHolderApi;
import com.fashion.rest.view.Adapters.AdapterLoadingType4;
import com.fashion.rest.view.Adapters.AdapterType4;
import com.fashion.rest.view.activity.ResultActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import static com.fashion.rest.functions.Functions.getTextEngOrLocal;
import static com.fashion.rest.functions.RetrofitFunctions.getItemHome;

public class FillType4 {

    static AdapterType4 adapterType4;
    static AdapterLoadingType4 adapterLoadingType4;
    static RecyclerView.LayoutManager layoutManager,layoutManagerLoading;
    public static ArrayList<ItemTest> dealsArrayList = new ArrayList<>();
    static JsonPlaceHolderApi jsonPlaceHolderApi2;
    static Retrofit retrofit2;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void fillCase4Item(RecyclerView recyclerView, Context context, TextView catName, RelativeLayout seeAllType4, Home home,RecyclerView lodaingRVT4) {
        createLodaingRV(lodaingRVT4,context);
        fillText(context,home,catName);
        intiRetrofit(home);
        getItemsFromServer(recyclerView,context,lodaingRVT4);
        actionListenerToSeeAll(seeAllType4,context,home);
    }

    private static void createLodaingRV(RecyclerView lodaingRVT4, Context context) {
        lodaingRVT4.setNestedScrollingEnabled(false);
        lodaingRVT4.setHasFixedSize(true);
        layoutManagerLoading = new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false);

        lodaingRVT4.setLayoutManager(layoutManagerLoading);
        adapterLoadingType4 =new AdapterLoadingType4(context);
        lodaingRVT4.setAdapter(adapterLoadingType4);
    }

    private static void getItemsFromServer(final RecyclerView recyclerView, final Context context, final RecyclerView loadingRV) {
        Call<List<ItemTest>> callHome = jsonPlaceHolderApi2.getAllItems(0,15);
        callHome.enqueue(new Callback<List<ItemTest>>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<List<ItemTest>> call, Response<List<ItemTest>> response) {
                if (!response.isSuccessful())
                { return; }
                List<ItemTest> itemsList = response.body();

                hideLoading(loadingRV);
                createRVSuggested(recyclerView, context,itemsList);
            }

            @Override
            public void onFailure(Call<List<ItemTest>> call, Throwable t) {
                Log.i("TAG Error",t.getMessage());
            }
        });
    }

    private static void hideLoading(final RecyclerView loadingRV) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                loadingRV.setVisibility(View.GONE);
            }
        }, 500);
    }

    private static void intiRetrofit(Home home) {
        retrofit2 = getItemHome(home.getSub_cat().getId(),home.getSub_cat().getCategory_id());
        jsonPlaceHolderApi2 = retrofit2.create(JsonPlaceHolderApi.class);
    }

    private static void fillText(Context context,Home home, TextView catName) {
        catName.setText(getTextEngOrLocal(context,home.getSub_cat().getName_en(),home.getSub_cat().getName_local()));
    }

    private static void actionListenerToSeeAll(RelativeLayout seeAllType2, final Context context, final Home home) {
        seeAllType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToResultActivity(context,home);
            }
        });
    }

    private static void moveToResultActivity(Context context, Home home) {
        Bundle bundle = new Bundle();
        bundle.putString("sub_cat_id",home.getSub_cat().getId());
        bundle.putString("cat_id",home.getSub_cat().getCategory_id());
        bundle.putString("sub_cat_name",getTextEngOrLocal(context,home.getSub_cat().getName_en(),home.getSub_cat().getName_local()));

        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtras(bundle);
        ((Activity)context).startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.right_to_left, R.anim.no_animation);
    }
    
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void createRVSuggested(RecyclerView recyclerView, Context context,List<ItemTest> homeList) {
        dealsArrayList = new ArrayList<>();
        dealsArrayList.addAll(homeList);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        adapterType4 =new AdapterType4(context
                ,dealsArrayList,"category");
        recyclerView.setAdapter(adapterType4);
    }

}
