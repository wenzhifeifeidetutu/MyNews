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

public class GymNews extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{

    private ListView gymNewsListView;

    private MyNewsAdapter myNewsAdapter;

    private static final String KEY = "b89464eb3a045df413329379481743b6";

    private static final String APIURLGYM = "https://api.tianapi.com/tiyu/?";

    private static final int LODERID = 4;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gym_news);

        myNewsAdapter = new MyNewsAdapter(this, new ArrayList<News>());

        gymNewsListView = (ListView)findViewById(R.id.gym_news_list_view);

        if (gymNewsListView != null) {
            gymNewsListView.setAdapter(myNewsAdapter);

            gymNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    News gymNews = myNewsAdapter.getItem(position);
                    new FinestWebView.Builder(GymNews.this).titleDefault(gymNews.getTitile())
                            .show(gymNews.getUrl());

                }
            });
        }
        LoaderManager loaderManager = getLoaderManager();

        //判断网络连接
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            //打开创建loder

            loaderManager.initLoader(LODERID, null, this );

        }else {
            //没网络情况
        }





    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        //偏好设置

        //构建uriBilder

        Uri  baseUri = Uri.parse(APIURLGYM);

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
