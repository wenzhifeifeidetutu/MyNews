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

public class SocialNews extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{

    private MyNewsAdapter myNewsAdapter;

    private ListView socialListView ;

    private static final String APIUrl = "https://api.tianapi.com/social/?";
    private static final String KEY = "b89464eb3a045df413329379481743b6";

    private static  final int News_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_news);

        List<News> newsList = new ArrayList<News>();

        myNewsAdapter =new MyNewsAdapter(this, newsList);

        socialListView = (ListView)findViewById(R.id.society_news_list_view);
        if (socialListView != null) {
            socialListView.setAdapter(myNewsAdapter);
            //设置点击事件 使用webView来打开
            socialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //使用webView实现
                    News itemNews = myNewsAdapter.getItem(position);
                    new FinestWebView.Builder(SocialNews.this).titleDefault(itemNews.getTitile())
                            .show(itemNews.getUrl());

                }
            });

        }

        //实现Loader加载数据
        LoaderManager socialNewsLoaderManager = getLoaderManager();

        //检测网络连接状态，当网络连接时才会开始获取数据, 获取连接状态
        ConnectivityManager connectivityManager =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取网络连接信息
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //如果有网络连接
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            socialNewsLoaderManager.initLoader(News_LOADER_ID, null , this );

        }else {
            //没有网络连接状态
        }







    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {

        //偏好设置


        //构建查询url
        Uri baseUri = Uri.parse(APIUrl);
        //开始构建
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("key", KEY);
        uriBuilder.appendQueryParameter("num", "20");
       return new NewsLoader(this, uriBuilder.toString());
      //  return new NewsLoader(this, Urlnews);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {

        myNewsAdapter.clear();

        if (data != null &&  !data.isEmpty()) {

            myNewsAdapter.addAll(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

        myNewsAdapter.clear();

    }
}
