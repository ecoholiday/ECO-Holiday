package com.NationalParks.ecoholiday.Activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.NationalParks.ecoholiday.R;


public class ChartFragment extends Fragment {


    public ChartFragment() {
        // Required empty public constructor
    }


    private View topLevelView;


    // save a reference to show the pie chart
    private WebView webview;

    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container,
                savedInstanceState);

        boolean attachToRoot = false;
        topLevelView = inflater.inflate(
                R.layout.fragment_chart,
                container,
                attachToRoot);

        Bundle fromStat = getArguments();
        String chartType = fromStat.getString("type");

        // call now or after some condition is met
        initChart(chartType);

        return topLevelView;
    }

    // initialize the WebView and the pie chart
    public void initChart(String chartType)
    {
        View stub = topLevelView.findViewById(
                R.id.chartStub);

        if (stub instanceof ViewStub)
        {
            ((ViewStub)stub).setVisibility(View.VISIBLE);

            webview = (WebView)topLevelView.findViewById(
                    R.id.chartWebview);

            WebSettings webSettings =
                    webview.getSettings();

            webSettings.setJavaScriptEnabled(true);

            webview.setWebChromeClient(
                    new WebChromeClient());

            webview.setWebViewClient(                    // load the pie chart
                    new WebViewClient()
            {
                @Override
                public void onPageFinished(
                        WebView view,
                        String url)
                {


                }
            });

            webview.loadUrl("file:///android_asset/" +
                    "HTML/"+ chartType +".html");
            //"HTML/world.html");
            //"HTML/piechart.html");

        }
    }

}
