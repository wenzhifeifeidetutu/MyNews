package com.mycompany.mynews;

/**
 * Created by Administrator on 2017/4/9.
 */

public class News {
    private String titile;
    private String url;
    private String time;
    private String description;
    private String picUrl;

    public News(){

    }

    public News(String titile, String description, String url,String picUrl, String time ){
        this.titile = titile;
        this.url = url;
        this.time = time;
        this.picUrl = picUrl;
        this.description = description;

    }
    public String getTitile(){
        return titile;

    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getUrl() {
        return url;
    }

    public String getPicUrl(){
        return picUrl;
    }

}
