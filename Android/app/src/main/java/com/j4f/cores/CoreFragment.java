package com.j4f.cores;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;

import java.io.Serializable;

public abstract class CoreFragment extends Fragment implements OnClickListener, Serializable {

    private static final long serialVersionUID = 7080889824192321168L;
    protected FragmentManager mFragmentManager;
    protected CoreActivity mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getFragmentManager();
        mContext = (CoreActivity) getActivity();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

    }

    public void finishFragment() {
        try {
            mFragmentManager.popBackStack();
        } catch (Exception e) {
            //	Log.e(e.getMessage());
        }
    }

    protected abstract void initModels();

    protected abstract void initViews(View view);

    protected abstract void initListener();

    protected abstract void initAnimations();
}
