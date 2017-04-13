package com.mycompany.mynews;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */

public class Query {


    public  Query(){

    }
    //创建Url
    private static URL creatUrl(String urlString){
         URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    //进行http请求
    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponseString = "";
        if (url == null ) {
            return null;
        }

        //开始http请求
        HttpURLConnection httpURLConnection  = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponseString = readStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }


        }

        return jsonResponseString;


    }
    //进行返回的input流优化
    private String readStream(InputStream inputStream) throws IOException {

        StringBuilder outer = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader  inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);


                String line = bufferedReader.readLine();
                while (line != null){
                    outer.append(line);
                    line = bufferedReader.readLine();
                }

        }

        return outer.toString();
    }
    //json对象的解析
    private List<News> jsonExtract(String jsonResponseString){
        if (TextUtils.isEmpty(jsonResponseString)){
            return null;
        }

        List<News> list_news = new ArrayList<News>();

        try{
            JSONObject baseObject = new JSONObject(jsonResponseString);
            JSONArray newslistArray = baseObject.getJSONArray("newslist");
            //遍历json数组
            for (int i = 0; i<newslistArray.length(); i++){
                JSONObject itemObject = newslistArray.getJSONObject(i);
                String ctime = itemObject.getString("ctime");
                String title = itemObject.getString("title");
                String description = itemObject.getString("description");
                String picUrl = itemObject.getString("picUrl");
                String url = itemObject.getString("url");
                list_news.add(new News(title,description,url, picUrl,ctime));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list_news;

    }


    //查询函数的开始
    public  List<News> fetchNews(String requsetUrl){
        URL url = creatUrl(requsetUrl);

        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<News> myNews = jsonExtract(jsonResponse);

        return myNews;
    }


}
