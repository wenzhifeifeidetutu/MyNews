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
import android.widget.Toast;

import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */

public class ScienceNews extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{
    private MyNewsAdapter myNewsAdapter;

    private ListView scienceListView ;

    private static final String APIScienceUrl = "https://api.tianapi.com/keji/?";
    private static final String KEY = "b89464eb3a045df413329379481743b6";

    private static  final int News_LOADER_ID_2 = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.science_new);

        myNewsAdapter = new MyNewsAdapter(this, new ArrayList<News>());
        scienceListView = (ListView)findViewById(R.id.science_news_list_view);
        if (scienceListView != null) {
            scienceListView.setAdapter(myNewsAdapter);
            scienceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    News scienceItemNews= myNewsAdapter.getItem(position);

                    new FinestWebView.Builder(ScienceNews.this).titleDefault(scienceItemNews.getTitile())
                            .show(scienceItemNews.getUrl());
                }
            });
        }




        LoaderManager scienceLoaderManager = getLoaderManager();

        //检测网络连接
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            scienceLoaderManager.initLoader(News_LOADER_ID_2, null,this);
            //没有网络状态的操作
        }else {

        }





    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        //偏好设置
        myNewsAdapter.clear();

        //构建Url
        Uri baseUri = Uri.parse(APIScienceUrl);
        Uri.Builder builder = baseUri.buildUpon();

        //开始构建uri
        builder.appendQueryParameter("key", KEY);
        builder.appendQueryParameter("num", "20");


        return new NewsLoader(this, builder.toString());
//        return new NewsLoader(this, "https://api.tianapi.com/keji/?key=b89464eb3a045df413329379481743b6&num=10");
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        myNewsAdapter.clear();

        if (loader != null && !data.isEmpty()) {
            myNewsAdapter.addAll(data);
        }


    }



    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
            myNewsAdapter.clear();
    }
}
