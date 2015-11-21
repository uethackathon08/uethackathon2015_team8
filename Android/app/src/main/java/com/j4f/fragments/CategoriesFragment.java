package com.j4f.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j4f.R;
import com.j4f.adapters.CategoryAdapter;
import com.j4f.cores.CoreActivity;
import com.j4f.cores.CoreFragment;
import com.j4f.models.Category;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;

/**
 * Created by hunter on 11/21/2015.
 */
public class CategoriesFragment extends CoreFragment {

    private ArrayList<Category> categoryList;
    private CategoryAdapter categoryAdapter;
    private UltimateRecyclerView categoryRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        initViews(view);
        initListener();
        initModels();
        initAnimations();
        return view;
    }

    public void initCategoryList() {
        categoryList = new ArrayList<Category>();
        categoryList.add(new Category("C1", "Android", "https://lh6.googleusercontent.com/-ewfAMUBQ-nM/AAAAAAAAAAI/AAAAAAAAAC4/_Mw-qQKu9zA/photo.jpg", 5, 5));
        categoryList.add(new Category("C1", "Java", "https://lh6.googleusercontent.com/-ewfAMUBQ-nM/AAAAAAAAAAI/AAAAAAAAAC4/_Mw-qQKu9zA/photo.jpg", 5, 5));
        categoryList.add(new Category("C1", "Android", "https://lh6.googleusercontent.com/-ewfAMUBQ-nM/AAAAAAAAAAI/AAAAAAAAAC4/_Mw-qQKu9zA/photo.jpg", 5, 5));
        categoryList.add(new Category("C1", "Java", "https://lh6.googleusercontent.com/-ewfAMUBQ-nM/AAAAAAAAAAI/AAAAAAAAAC4/_Mw-qQKu9zA/photo.jpg", 5, 5));
        categoryList.add(new Category("C1", "Android", "https://lh6.googleusercontent.com/-ewfAMUBQ-nM/AAAAAAAAAAI/AAAAAAAAAC4/_Mw-qQKu9zA/photo.jpg", 5, 5));
        categoryList.add(new Category("C1", "Java", "https://lh6.googleusercontent.com/-ewfAMUBQ-nM/AAAAAAAAAAI/AAAAAAAAAC4/_Mw-qQKu9zA/photo.jpg", 5, 5));
        categoryList.add(new Category("C1", "Android", "https://lh6.googleusercontent.com/-ewfAMUBQ-nM/AAAAAAAAAAI/AAAAAAAAAC4/_Mw-qQKu9zA/photo.jpg", 5, 5));
        categoryList.add(new Category("C1", "Java", "https://lh6.googleusercontent.com/-ewfAMUBQ-nM/AAAAAAAAAAI/AAAAAAAAAC4/_Mw-qQKu9zA/photo.jpg", 5, 5));
        categoryList.add(new Category("C1", "Android", "https://lh6.googleusercontent.com/-ewfAMUBQ-nM/AAAAAAAAAAI/AAAAAAAAAC4/_Mw-qQKu9zA/photo.jpg", 5, 5));
        categoryList.add(new Category("C1", "Java", "https://lh6.googleusercontent.com/-ewfAMUBQ-nM/AAAAAAAAAAI/AAAAAAAAAC4/_Mw-qQKu9zA/photo.jpg", 5, 5));
        categoryList.add(new Category("C1", "Android", "https://lh6.googleusercontent.com/-ewfAMUBQ-nM/AAAAAAAAAAI/AAAAAAAAAC4/_Mw-qQKu9zA/photo.jpg", 5, 5));
        categoryList.add(new Category("C1", "Java", "https://lh6.googleusercontent.com/-ewfAMUBQ-nM/AAAAAAAAAAI/AAAAAAAAAC4/_Mw-qQKu9zA/photo.jpg", 5, 5));
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    protected void initModels() {
        initCategoryList();
        categoryAdapter = new CategoryAdapter(categoryList, mActivity);
        categoryRecyclerView.setHasFixedSize(true);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    @Override
    protected void initViews(View v) {
        categoryRecyclerView = (UltimateRecyclerView) v.findViewById(R.id.category_list);
    }

    @Override
    protected void initAnimations() {

    }

    @Override
    protected void initListener() {
    }

    public static final long serialVersionUID = 6036846677812555352L;
    public static CoreActivity mActivity;
    public static CategoriesFragment mInstance;
    public static CategoriesFragment getInstance(CoreActivity activity) {
        if (mInstance == null) {
            mInstance = new CategoriesFragment();
        }
        mActivity = activity;
        return mInstance;
    }
}
