package com.example.michalik.touchdynamic.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.michalik.touchdynamic.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMeasurementsFragment extends Fragment {

    @BindView(R.id.fragment_my_measurements_webview)
    WebView webView;

    public MyMeasurementsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_measurements, container, false);

        ButterKnife.bind(this, view);
        loadCharts();
        return view;
    }
    private void loadCharts(){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://touchdynamics.rozkmin.com/");
    }

}
