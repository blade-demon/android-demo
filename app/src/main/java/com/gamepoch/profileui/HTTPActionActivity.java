package com.gamepoch.profileui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;


import org.json.JSONException;
import org.json.JSONObject;

public class HTTPActionActivity extends AppCompatActivity {

    private final String TAG = "HTTPActionActivity";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_httpaction);

        mTextView = findViewById(R.id.textView_data);
        doSomething();

    }

    private void doSomething() {
        // GET token
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxf2e552a466a5ea08&secret=576615e47e35df20e231f8549447ab88";
        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String token = response.getString("access_token");
                            convertLongURL(token);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        mTextView.setText("长链接转短链接失败！");
                    }
                });
    }

    private void convertLongURL(String token) {
        String url = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=" + token;
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("action", "long2short");
            requestObject.put("long_url", "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf2e552a466a5ea08&redirect_uri=https%3a%2f%2fwechatservice.gamepoch.com%2factivities%2fkofcode&response_type=code&scope=snsapi_userinfo&state=snsapi_userinfo&connect_redirect=1#wechat_redirect");
            Log.v(TAG, requestObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(url).addJSONObjectBody(requestObject).build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v(TAG, response.toString());
                try {
                    mTextView.setText(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                mTextView.setText("长链接转短链接失败！");
            }
        });
    }
}
