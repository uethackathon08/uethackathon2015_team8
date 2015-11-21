package com.j4f.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.j4f.R;
import com.j4f.customizes.MyGifView;
import com.j4f.customizes.MyTextView;

public class DialogProgress extends DialogFragment {
	public Context mContext;
	public LayoutInflater mInflater;
	protected Dialog mDialog;
	protected MyGifView mLoadingView;
	protected MyTextView mContent;
	protected String mContentText;
	public DialogProgress() {
		
	}
	public DialogProgress(Context context, String content) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		this.mContentText = content;
	}

	public static DialogFragment newInstance(Context context, String content ) {
		DialogProgress dialog = new DialogProgress(context, content);
		Bundle bundle = new Bundle();
		dialog.setArguments(bundle);
		return dialog;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			
		}
	}
	
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mDialog = new Dialog(mContext);
		mDialog.setCanceledOnTouchOutside(false);
		mDialog.setCancelable(false);
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = mInflater.inflate(R.layout.dialog_progress, null);
	
		initViews(view);
		setViews();
		initOnClick();
		initAnimation();
		
		mDialog.setContentView(view);
		return mDialog;
	}
	
	public void initViews(View view) {
		mContent = (MyTextView) view.findViewById(R.id.dialog_progress_content);
		mLoadingView = (MyGifView) view.findViewById(R.id.dialog_progress_loading);
	}
	public void setViews() {
		mContent.setText(mContentText);
		mLoadingView.setMovieResource(R.mipmap.loading);
	}
	
	public void initOnClick() {
		
	}
	
	public void initAnimation() {
		
	}
}
