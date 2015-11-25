/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.qianmi.qmapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

public class MainActivity extends CordovaActivity {

    SystemWebView webView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        super.init();
        loadUrl(launchUrl);
        ImageButton btnShare = (ImageButton) findViewById(R.id.btnShare);

//        btnShare.setAlpha(0f);
//        btnShare.animate()
//                .alpha(1f)
//                .setDuration(1500)
//                .setInterpolator(new DecelerateInterpolator())
//                .setStartDelay(1000);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT, "欢迎访问" + appView.getUrl());
                startActivity(Intent.createChooser(intent, "分享到"));
            }

        });

        (findViewById(R.id.btnBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appView.canGoBack()) {
                    appView.backHistory();
                } else {
                    finish();
                }
            }
        });

        (findViewById(R.id.btnRefresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appView.loadUrl(appView.getUrl());
            }
        });
    }

    @Override
    protected CordovaWebView makeWebView() {
        webView = (SystemWebView) findViewById(R.id.cordovaWebView);
        return new CordovaWebViewImpl(new SystemWebViewEngine(webView));
    }

    @Override
    protected void createViews() {
        //Why are we setting a constant as the ID? This should be investigated
//        appView.getView().setId(100);
//        appView.getView().setLayoutParams(new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//
//        setContentView(appView.getView());

        if (preferences.contains("BackgroundColor")) {
            int backgroundColor = preferences.getInteger("BackgroundColor", Color.BLACK);
            // Background of activity:
            appView.getView().setBackgroundColor(backgroundColor);
        }

        appView.getView().requestFocusFromTouch();
    }

//    @Override
//    public Object onMessage(String id, Object data) {
//        Log.d("onMessage", id + data.toString() + webView.getTitle());
//        if (id.equals("onPageFinished")) {
//            String pageTitle = webView.getTitle();
//            if (TextUtils.isEmpty(pageTitle)) {
//                ((TextView) findViewById(R.id.tvTitle)).setText(R.string.app_name);
//            } else {
//                ((TextView) findViewById(R.id.tvTitle)).setText(webView.getTitle());
//            }
//        }
//        super.onMessage(id, data);
//        return null;
//    }

}
