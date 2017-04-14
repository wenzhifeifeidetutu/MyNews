package com.mycompany.mynews;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.app.LoaderManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.List;

public class SocialNews extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categlory);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SocialNewsFragment()).commit();

    }
}
