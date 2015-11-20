package com.j4f.interfaces;

import com.android.volley.VolleyError;
import com.j4f.cores.CoreInterface;

import org.json.JSONObject;

public interface JSONObjectRequestListener extends CoreInterface {
	public void onBefore();
	public void onResponse(JSONObject response);
	public void onError(VolleyError error);
}
