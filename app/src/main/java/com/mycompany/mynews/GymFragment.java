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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GymFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>>{
    private ListView gymNewsListView;

    private MyNewsAdapter myNewsAdapter;

    private static final String KEY = "b89464eb3a045df413329379481743b6";

    private static final String APIURLGYM = "https://api.tianapi.com/tiyu/?";

    private static final int LODERID = 4;
    private TextView emptyText ;

    private ProgressBar noWorkProgressBar;


    public GymFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gym_news, container, false);

        myNewsAdapter = new MyNewsAdapter(getActivity(), new ArrayList<News>());

        gymNewsListView = (ListView)rootView.findViewById(R.id.gym_news_list_view);

        emptyText = (TextView)rootView.findViewById(R.id.no_network_info);
        noWorkProgressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);


        if (gymNewsListView != null) {
            gymNewsListView.setAdapter(myNewsAdapter);

            gymNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    News gymNews = myNewsAdapter.getItem(position);
                    new FinestWebView.Builder(getActivity()).titleDefault(gymNews.getTitile())
                            .show(gymNews.getUrl());

                }
            });
        }
        LoaderManager loaderManager = getLoaderManager();

        //判断网络连接
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            //打开创建loder

            loaderManager.initLoader(LODERID, null, this );

        }else {
            //没网络情况
            noWorkProgressBar.setVisibility(View.GONE);
            emptyText.setText(R.string.no_network);

        }


        return rootView;


    }

    @Override
    public android.support.v4.content.Loader<List<News>> onCreateLoader(int id, Bundle args) {
        //偏好设置

        //构建uriBilder

        Uri baseUri = Uri.parse(APIURLGYM);

        Uri.Builder builder = baseUri.buildUpon();
        builder.appendQueryParameter("key", KEY);
        builder.appendQueryParameter("num", "20");

        return new NewsLoader(getActivity(), builder.toString());
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<News>> loader, List<News> data) {
        myNewsAdapter.clear();
        noWorkProgressBar.setVisibility(View.GONE);
        if (data != null && !data.isEmpty()) {
            myNewsAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<News>> loader) {
        myNewsAdapter.clear();
    }

}


