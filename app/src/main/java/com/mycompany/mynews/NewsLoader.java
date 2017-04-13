package com.mycompany.mynews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    public String myUrl;

    public NewsLoader(Context context, String myurl){
        super(context);
        myUrl = myurl;
    }


    @Override
    public List<News> loadInBackground() {
        if (myUrl == null){
            return null;
        }
        Query query = new Query();
        List<News> newsList = query.fetchNews(myUrl);

        return newsList;
    }
    //使用forceLoad（）
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
