package com.j4f.cores;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.j4f.application.MyApplication;
import com.j4f.configs.Configs;
import com.j4f.dialogs.DialogProgress;
import com.j4f.interfaces.ImageRequestListener;
import com.j4f.interfaces.JSONArrayRequestListener;
import com.j4f.interfaces.JSONObjectRequestListener;
import com.j4f.interfaces.StringRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;

public abstract class CoreActivity extends AppCompatActivity implements Serializable, View.OnClickListener {
    private static final long serialVersionUID = -6161155222390513466L;
    private static String TAG = "J4F";

    @Override
    public void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public void logd(final Object msg) {
        if (msg == null) {
            Log.d(TAG, "NULL");
        } else {
            Log.d(TAG, msg.toString());
        }
    }

    public void logi(final Object msg) {
        if (msg == null) {
            Log.i(TAG, "NULL");
        } else {
            Log.i(TAG, msg.toString());
        }
    }

    public void loge(final Object msg) {
        if (msg == null) {
            Log.e(TAG, "NULL");
        } else {
            Log.e(TAG, msg.toString());
        }
    }

    public String readStringFromSDCard(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            File myFile = new File(path);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
            String aDataRow = "";
            while ((aDataRow = myReader.readLine()) != null) {
                sb.append(aDataRow).append("\n");
            }
            myReader.close();
            logi(new StringBuilder().append("Done. Read SDCard [").append(path).append(sb.toString()).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void writeStringToSDCard(String path, String msg) {
        File myFile = new File(path);
        try {
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(msg);
            myOutWriter.close();
            fOut.close();
            logi(new StringBuilder().append("Done. Write to SDCard [").append(path).append("]: ").append(msg).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasCamera(CoreActivity context) {
        boolean state = false;
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return state = true;
        }
        logi(new StringBuilder().append("Done. Check has Camera: ").append(state).toString());
        return state;
    }

    public boolean hasSDCard() {
        boolean state = false;
        String sd = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(sd)) {
            state = true;
        }
        logi(new StringBuilder().append("Done. Check has SDCard: ").append(state).toString());
        return state;
    }

    public void showToastShort(final Object message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (message == null) {
                    Toast.makeText(CoreActivity.this, "NULL", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CoreActivity.this, message.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        logi(new StringBuilder().append("Done. Show short Toast: ").append(message).toString());
    }

    public void showToastLong(final Object message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (message == null) {
                    Toast.makeText(CoreActivity.this, "NULL", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CoreActivity.this, message.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        logi(new StringBuilder().append("Done. Show long Toast: ").append(message).toString());
    }

    public String readSharedPreferences(String preferencesName, String elementName) {
        SharedPreferences pre = getSharedPreferences(preferencesName, MODE_PRIVATE);
        String s = pre.getString(elementName, "");
        logi(new StringBuilder().append("Done. Read shared preferences [ ")
                .append(preferencesName).append("]:[").append(elementName)
                .append("]: ").append(s).toString());
        return s;
    }

    public void saveSharedPreferences(String preferencesName, String elementName, String data) {
        SharedPreferences pre = getSharedPreferences(preferencesName, MODE_PRIVATE);
        SharedPreferences.Editor edit = pre.edit();
        edit.putString(elementName, data);
        edit.commit();
        logi(new StringBuilder().append("Done. Write shared preferences [ ")
                .append(preferencesName).append("]:[").append(elementName)
                .append("]: ").append(data).toString());
    }

    private android.support.v4.app.FragmentManager mFragmentManager = getSupportFragmentManager();
    public void removePreviousDialog(String tag) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        Fragment prev = mFragmentManager.findFragmentByTag(tag);
        if (prev != null) ft.remove(prev);
        ft.commit();
    }
    private DialogFragment mDialog;
    public DialogFragment showProgressDialog(final String tag, final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                removePreviousDialog(tag);
                mDialog = DialogProgress.newInstance(CoreActivity.this, msg);
                mDialog.show(getSupportFragmentManager(), tag);
            }
        });
        return mDialog;
    }

    public void cancelAllRequestWithTag(String tag) {
        MyApplication.getInstance().getRequestQueue().cancelAll(tag);
    }

    public void makeJsonObjectRequest(String url, final JSONObjectRequestListener mListener) {
        mListener.onBefore();
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mListener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mListener.onError(error);
            }
        });
        MyApplication.getInstance().addToRequestQueue(jsonObjRequest, Configs.TAG_JSONOBJ_REQUEST);
    }

    public void makeJsonArrayRequest(String url, final JSONArrayRequestListener mListener) {
        mListener.onBefore();
        JsonArrayRequest jsonArrRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mListener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mListener.onError(error);
            }
        });
        MyApplication.getInstance().addToRequestQueue(jsonArrRequest, Configs.TAG_JSONARR_REQUEST);
    }

    public void makeStringRequest(String url, final StringRequestListener mListener) {
        mListener.onBefore();
        StringRequest stringRequest = new StringRequest(Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mListener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mListener.onErrorResponse(error);
            }
        });
        MyApplication.getInstance().addToRequestQueue(stringRequest, Configs.TAG_STRING_REQUEST);
    }

    public void makeImageRequest(String url, final ImageRequestListener mListener) {
        mListener.onBefore();
        ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();
        imageLoader.get(url, new ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mListener.onErrorResponse(error);
            }

            @Override
            public void onResponse(ImageContainer paramImageContainer, boolean paramBoolean) {
                mListener.onResponse(paramImageContainer, paramBoolean);
//				imageView.setImageBitmap(paramImageContainer.getBitmap());
            }
        });
    }

    public abstract void initViews();

    public abstract void initModels();

    public abstract void initListeners();

    public abstract void initAnimations();

}
