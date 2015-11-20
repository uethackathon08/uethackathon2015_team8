package com.j4f.interfaces;

import com.android.volley.VolleyError;
import com.j4f.cores.CoreInterface;

public interface StringRequestListener  extends CoreInterface {
	public void onBefore();
	public void onResponse(String response);
	public void onErrorResponse(VolleyError error);
}
