package com.mycompany.mynews;


import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
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
public class SocialNewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {

    private MyNewsAdapter myNewsAdapter;

    private ListView socialListView ;

    private static final String APIUrl = "https://api.tianapi.com/social/?";
    private static final String KEY = "b89464eb3a045df413329379481743b6";

    private static  final int News_LOADER_ID = 1;

    private TextView emptyText ;

    private ProgressBar noWorkProgressBar;



    public SocialNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.social_news, container, false );

        List<News> newsList = new ArrayList<News>();

        myNewsAdapter =new MyNewsAdapter(getActivity(), newsList);

        socialListView = (ListView)rootView.findViewById(R.id.society_news_list_view);

        //设置progressBar 和emptyText
        emptyText = (TextView) rootView.findViewById(R.id.no_network_info);
        noWorkProgressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);


        if (socialListView != null) {
            socialListView.setAdapter(myNewsAdapter);
            //设置点击事件 使用webView来打开
            socialListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //使用webView实现
                    News itemNews = myNewsAdapter.getItem(position);
                    new FinestWebView.Builder(getActivity()).titleDefault(itemNews.getTitile())
                            .show(itemNews.getUrl());

                }
            });

        }

        //实现Loader加载数据
        LoaderManager socialNewsLoaderManager = getLoaderManager();

        //检测网络连接状态，当网络连接时才会开始获取数据, 获取连接状态
        ConnectivityManager connectivityManager =(ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取网络连接信息
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //如果有网络连接
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            socialNewsLoaderManager.initLoader(News_LOADER_ID, null , this );

        }else {
            //没有网络连接状态
            noWorkProgressBar.setVisibility(View.GONE);
            emptyText.setText(R.string.no_network);


        }




        return rootView;


    }

    @Override
    public android.support.v4.content.Loader<List<News>> onCreateLoader(int id, Bundle args) {

        //偏好设置


        //构建查询url
        Uri baseUri = Uri.parse(APIUrl);
        //开始构建
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("key", KEY);
        uriBuilder.appendQueryParameter("num", "20");
        return new NewsLoader(getActivity(), uriBuilder.toString());
        //  return new NewsLoader(this, Urlnews);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<News>> loader, List<News> data) {
        myNewsAdapter.clear();
        noWorkProgressBar.setVisibility(View.GONE);

        if (data != null &&  !data.isEmpty()) {

            myNewsAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<News>> loader) {
        myNewsAdapter.clear();
    }



}


