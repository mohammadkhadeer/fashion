package com.fashion.rest.view.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.fashion.rest.R;
import com.fashion.rest.model.Categories;
import com.fashion.rest.model.Category;
import com.fashion.rest.model.CustomCategory;
import com.fashion.rest.model.Deal;
import com.fashion.rest.model.Sub_Cat;
import com.fashion.rest.view.Adapters.AdapterAllCategory;
import com.fashion.rest.view.Adapters.AdapterType3;

import java.util.ArrayList;
import java.util.Arrays;

import static com.fashion.rest.functions.Functions.fillCat2ArrayL;
import static com.fashion.rest.functions.Functions.fillCatArrayL;
import static com.fashion.rest.functions.Functions.fillSetArrayL;

public class AllCategory extends AppCompatActivity {

    RecyclerView all_cat_RV;
    NestedScrollView nestedScrollView;

    AdapterAllCategory adapterAllCategory;
    public ArrayList<Categories> allCategoriesArrayList = new ArrayList<>();
    public ArrayList<CustomCategory> customCategoriesArrayList = new ArrayList<>();
    LinearLayoutManager mLayoutManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);

        statusBarColor();
        inti();
        createAllCategoryRV();
    }

    private void createAllCategoryRV() {
        allCategoriesArrayList = new ArrayList<>();
        getInfoFromCat();

        customCategoriesArrayList = fillCustomCategory();
        all_cat_RV.setNestedScrollingEnabled(false);
        all_cat_RV.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        all_cat_RV.setLayoutManager(mLayoutManager);
        adapterAllCategory =new AdapterAllCategory(this
                ,customCategoriesArrayList);
        all_cat_RV.setAdapter(adapterAllCategory);
    }

    private void getInfoFromCat() {
        allCategoriesArrayList = (ArrayList<Categories>) getIntent().getSerializableExtra("categories");
        //Log.i("TAG sub cat ", String.valueOf(sub_catArrayList.size()));

//        for (int i=0;i<sub_catArrayList.size();i++)
//        {
//            Log.i("TAG sub cat ",sub_catArrayList.get(i).getName_en());
//        }
    }

    private ArrayList<CustomCategory> fillCustomCategory() {
        ArrayList<CustomCategory> customCategoriesInternalArrayList = new ArrayList<>();
        ArrayList<Categories> smallArrayL = new ArrayList<>();
        for (int i =0;i<allCategoriesArrayList.size();i++)
        {
            if(i % 3 == 0) {
                smallArrayL = new ArrayList<>();
                if (i==0)
                {
                    for (int j=0;j<3;j++)
                    {
                        if (i+j < allCategoriesArrayList.size())
                        {
                            smallArrayL.add(allCategoriesArrayList.get(i+j));
                        }
                    }
                    CustomCategory customCategory = new CustomCategory(smallArrayL);
                    customCategoriesInternalArrayList.add(customCategory);
                }else{
                    for (int j=0;j<3;j++)
                    {
                        if (i+j < allCategoriesArrayList.size())
                        {
                            smallArrayL.add(allCategoriesArrayList.get(i+j));
                        }
                    }
                    CustomCategory customCategory = new CustomCategory(smallArrayL);
                    customCategoriesInternalArrayList.add(customCategory);
                }

            }
        }
        return customCategoriesInternalArrayList;
    }

    private void inti() {
        all_cat_RV = (RecyclerView) findViewById(R.id.all_cat_RV);
        nestedScrollView = (NestedScrollView) findViewById(R.id.all_cat_nested);
    }

    private void statusBarColor() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}