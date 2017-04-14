package com.mycompany.mynews;


import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AmusementFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>>{

    private ListView amusementListView;

    private MyNewsAdapter myNewsAdapter;

    private static  final  String KEY = "b89464eb3a045df413329379481743b6";

    private static final int LOADER_ID = 5;

    private static final String AMUSEMENTURI = "https://api.tianapi.com/huabian/?";


    public AmusementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.amusement_news, container, false);

        myNewsAdapter = new MyNewsAdapter(getActivity(), new ArrayList<News>());

        amusementListView = (ListView)rootView.findViewById(R.id.amusement_news_list_view);

        if (amusementListView != null) {
            amusementListView.setAdapter(myNewsAdapter);

            amusementListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    News amusementNews = myNewsAdapter.getItem(position);

                    new FinestWebView.Builder(getActivity()).titleDefault(amusementNews.getTitile())
                            .show(amusementNews.getUrl());
                }
            });
        }

        LoaderManager loaderManager = getLoaderManager();


        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            loaderManager.initLoader(LOADER_ID, null, this);

        }else {


        }

        return rootView;
    }

    @Override
    public android.support.v4.content.Loader<List<News>> onCreateLoader(int id, Bundle args) {
        //偏好设置


        //构建UriBUilder
        Uri baseUri = Uri.parse(AMUSEMENTURI);

        Uri.Builder builder = baseUri.buildUpon();

        builder.appendQueryParameter("key", KEY);

        builder.appendQueryParameter("num", "20");

        return new NewsLoader(getActivity(), builder.toString());
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<News>> loader, List<News> data) {
        myNewsAdapter.clear();

        if (data != null && !data.isEmpty()) {
            myNewsAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<News>> loader) {
        myNewsAdapter.clear();
    }




}


