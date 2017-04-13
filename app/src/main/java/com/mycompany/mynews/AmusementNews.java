package com.mycompany.mynews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */

public class AmusementNews extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{
    private ListView amusementListView;

    private MyNewsAdapter myNewsAdapter;

    private static  final  String KEY = "b89464eb3a045df413329379481743b6";

    private static final int LOADER_ID = 5;

    private static final String AMUSEMENTURI = "https://api.tianapi.com/huabian/?";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amusement_news);

        myNewsAdapter = new MyNewsAdapter(this, new ArrayList<News>());

        amusementListView = (ListView)findViewById(R.id.amusement_news_list_view);

        if (amusementListView != null) {
            amusementListView.setAdapter(myNewsAdapter);

            amusementListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    News amusementNews = myNewsAdapter.getItem(position);

                    new FinestWebView.Builder(AmusementNews.this).titleDefault(amusementNews.getTitile())
                            .show(amusementNews.getUrl());
                }
            });
        }

        LoaderManager loaderManager = getLoaderManager();


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            loaderManager.initLoader(LOADER_ID, null, this);

        }else {


            }
        }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        //偏好设置


        //构建UriBUilder
        Uri baseUri = Uri.parse(AMUSEMENTURI);

        Uri.Builder builder = baseUri.buildUpon();

        builder.appendQueryParameter("key", KEY);

        builder.appendQueryParameter("num", "20");

        return new NewsLoader(this, builder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
            myNewsAdapter.clear();

        if (data != null && !data.isEmpty()) {
            myNewsAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

        myNewsAdapter.clear();

    }
}

