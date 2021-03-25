package com.fashion.rest.view.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fashion.rest.R;
import com.fashion.rest.functions.Functions;
import com.fashion.rest.model.Brand;
import com.fashion.rest.model.FilterItemsModel;
import com.fashion.rest.model.ItemTest;
import com.fashion.rest.presnter.JsonPlaceHolderApi;
import com.fashion.rest.view.fragments.fragmentHomeMainScreen.FragmentFilter;
import com.fashion.rest.view.fragments.fragmentHomeMainScreen.FragmentResults;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import static com.fashion.rest.apiURL.API.apiURLBase;
import static com.fashion.rest.functions.Functions.getTextEngOrLocal;
import static com.fashion.rest.functions.FunctionsResultsNumber.fillNumberOfItemsResult;
import static com.fashion.rest.functions.RetrofitFunctions.getBrand;
import static com.fashion.rest.functions.RetrofitFunctions.getBrandSubCat;
import static com.fashion.rest.functions.RetrofitFunctions.getNumberOfItemsResults;
import static com.fashion.rest.sharedPreferences.Language.getLanguageFromSP;
import static com.fashion.rest.view.activity.mainScreem.MainActivity.setLocale;

public class ResultActivity extends AppCompatActivity {

    FragmentResults fragmentResults = new FragmentResults();
    FilterItemsModel filterItemsModel;
    ImageView backIV,brand_iv;
    RelativeLayout back_rl;
    TextView sub_category_name_tv,result_number_tv;
    FragmentFilter fragmentFilter = new FragmentFilter();
    int filterOnTheTop=0;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLocale(this);
        setContentView(R.layout.activity_result);

        statusBarColor();
        getInfoFromCat();
        inti();
        intiRetrofit();
        fillSubCategoryName();
        getImageBrand();
        changeFont();
        fillNumberOfItemsResult(this,result_number_tv,filterItemsModel);
        fillBackImageViewDepLanguage();
        actionListenerToBack();

        handelResultFragment();
    }

    private void fillSubCategoryName() {
        sub_category_name_tv.setText(getTextEngOrLocal(getApplicationContext(),filterItemsModel.getSub_cat().getName_en(),filterItemsModel.getSub_cat().getName_local()));
    }

    private void getImageBrand() {
        Call<Brand> callHome = jsonPlaceHolderApi.getBrandSubCat();
        callHome.enqueue(new Callback<Brand>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<Brand> call, Response<Brand> response) {
                if (!response.isSuccessful())
                { return; }
                Brand itemsList = response.body();
                Picasso.get()
                        .load(apiURLBase()+itemsList.getFlag().getUrl())
                        .fit()
                        .centerCrop()
                        .into(brand_iv);
            }

            @Override
            public void onFailure(Call<Brand> call, Throwable t) {
                Log.i("TAG","getItemsFromServer  in error");
                Log.i("TAG Error",t.getMessage());
            }
        });

    }

    private void intiRetrofit() {
        retrofit = getBrandSubCat(filterItemsModel.getSub_cat().getBrand());
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
    }

    private void changeFont() {
        sub_category_name_tv.setTypeface(Functions.changeFontBold(this));
        result_number_tv.setTypeface(Functions.changeFontGeneral(this));
    }

    private void actionListenerToBack() {
        back_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void fillBackImageViewDepLanguage() {
        if (getLanguageFromSP(this).equals("en"))
        {
            backIV.setImageResource(R.drawable.arrow_en);
        } else
        {
            backIV.setImageResource(R.drawable.arrow_ar);
        }
    }

    private void inti() {
        backIV = (ImageView) findViewById(R.id.backIV);
        back_rl = (RelativeLayout) findViewById(R.id.back_rl);
        sub_category_name_tv = (TextView) findViewById(R.id.sub_category_name_tv);
        result_number_tv = (TextView) findViewById(R.id.result_number_tv);
        brand_iv = (ImageView) findViewById(R.id.brand);
    }

    private void getInfoFromCat() {
        filterItemsModel = (FilterItemsModel) getIntent().getParcelableExtra("filter_object");
    }

    private void handelResultFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("sub_cat_id",filterItemsModel.getSub_cat().getId());
        bundle.putString("cat_id",filterItemsModel.getSub_cat().getCategory_id());
        bundle.putParcelable("filter_object",filterItemsModel);

        fragmentResults.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_result_activity, fragmentResults)
                .commit();
    }

    private void statusBarColor() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

}