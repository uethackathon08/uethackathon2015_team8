package com.j4f.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j4f.R;
import com.j4f.cores.CoreActivity;
import com.j4f.cores.CoreFragment;

/**
 * Created by hunter on 11/21/2015.
 */
public class QuestionsFragment extends CoreFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        initViews(view);
        initListener();
        initModels();
        initAnimations();
        return view;
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    protected void initModels() {

    }

    @Override
    protected void initViews(View v) {
    }

    @Override
    protected void initAnimations() {

    }

    @Override
    protected void initListener() {
    }

    public static final long serialVersionUID = 6036846677812555352L;
    public static CoreActivity mActivity;
    public static QuestionsFragment mInstance;
    public static QuestionsFragment getInstance(CoreActivity activity) {
        if (mInstance == null) {
            mInstance = new QuestionsFragment();
        }
        mActivity = activity;
        return mInstance;
    }
    private QuestionsFragment() {

    }
}
