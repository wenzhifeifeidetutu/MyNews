package com.mycompany.mynews;

import android.net.Uri;

/**
 * Created by Administrator on 2017/4/13.
 */

public class UriBuilder {
    private static final String KEY = "b89464eb3a045df413329379481743b6";
    private String baseUri;

    public UriBuilder(String baseUri) {
            this.baseUri = baseUri;
    }


    protected String builderUri() {
        Uri uri = Uri.parse(baseUri);

        Uri.Builder builder = uri.buildUpon();
        builder.appendQueryParameter("key",KEY );
        builder.appendQueryParameter("num", "20");

        return builder.toString();
    }
}
