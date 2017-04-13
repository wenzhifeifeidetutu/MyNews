package com.mycompany.mynews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import java.util.List;

/**
 * Created by Administrator on 2017/4/13.
 */

public class MyloaderManager implements LoaderManager.LoaderCallbacks<List<News>> {

    private String uri;

    private String id;

    private Context context;

    public MyloaderManager(String uri, String id, Context context) {
        this.uri = uri;
        this.id = id;
        this.context = context;

    }
    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        //偏好设置

        //Uri
        UriBuilder uriBuilder = new UriBuilder(uri);


        return new NewsLoader( context, uriBuilder.builderUri());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

    }
}
