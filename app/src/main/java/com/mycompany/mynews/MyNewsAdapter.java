package com.mycompany.mynews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */

public class MyNewsAdapter extends ArrayAdapter<News> {
    private TextView descriptionTextView;

    private TextView contentTextView;

    private TextView timeofmouth;
    private TextView dataofyear;

    private ImageView pictureImage;




    public MyNewsAdapter(Context context, List<News> newsList) {
        super(context, 0, newsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View newsItemView = convertView;
        if (newsItemView == null) {
            newsItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }

        News newsItem = getItem(position);

        descriptionTextView = (TextView)newsItemView.findViewById(R.id.description_of_item);
        contentTextView = (TextView)newsItemView.findViewById(R.id.title_of_item);
        timeofmouth = (TextView)newsItemView.findViewById(R.id.moth_of_time);
        dataofyear = (TextView)newsItemView.findViewById(R.id.year_of_data);
        pictureImage = (ImageView)newsItemView.findViewById(R.id.image_of_item);

        descriptionTextView.setText(newsItem.getDescription());
        contentTextView.setText(newsItem.getTitile());
        String time = newsItem.getTime();
        String[] times = time.split(" ");
        dataofyear.setText(times[0]);
        timeofmouth.setText(times[1]);

        //设置图片
        String picUriString = newsItem.getPicUrl();
        //去掉字符串中的_ss
        String theTruePic = picUriString.replace("_ss", "");

        DownloadImageTask downloadImageTask = new DownloadImageTask(pictureImage);
        //执行
        downloadImageTask.execute(theTruePic);



        return newsItemView;



    }
    //使用AsyncTask加载图片
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{

        ImageView imageView;

        public DownloadImageTask(ImageView imageView){
            this.imageView = imageView;


        }

        @Override
        protected Bitmap doInBackground(String... params) {

            String urldisplay  = params[0];
            Bitmap iCon = null;
            try {

                InputStream in = new URL(urldisplay).openStream();
                if (in != null) {
                    iCon = BitmapFactory.decodeStream(in);
                    return iCon;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            }

        }
    }



}
