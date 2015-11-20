package com.j4f.interfaces;

import com.android.volley.VolleyError;
import com.j4f.cores.CoreInterface;

import org.json.JSONArray;

public interface JSONArrayRequestListener extends CoreInterface {
	public void onBefore();
	public void onResponse(JSONArray response);
	public void onError(VolleyError error);
}
