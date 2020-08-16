package com.example.myfeed.remote;

import android.content.Context;

import com.example.myfeed.App;
import com.example.myfeed.R;
import com.example.myfeed.data.response.ImageResponse;
import com.example.myfeed.data.response.VideoResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;

public class GoogleHttpClientFunctions implements HttpClientFunctions {

    private static final String TAG ="te~st" ;
    private HttpRequestFactory requestFactory= new NetHttpTransport().createRequestFactory();
    private GsonFactory jsonFactory=new GsonFactory();





    /** These functions must run on another thread other than the UI thread */

    @Override
    public void fetchImagesFromRemoteApi(RequestResultListener requestResultListener) {
        try {
            HttpResponse response = preformGetRequest("https://api.pexels.com/v1/search?query=nature");
            if(response.isSuccessStatusCode()){
                ImageResponse videoResponse= response.parseAs(ImageResponse.class);
                requestResultListener.onSuccess(videoResponse);
            }else{
                requestResultListener.onRequestFailed(response.getStatusCode());
            }
        } catch (IOException e) {
            requestResultListener.onFailed(e);
        }
    }



    @Override
    public void fetchVideosFromRemoteApi(RequestResultListener requestResultListener) {
        try {
           HttpResponse response = preformGetRequest("https://api.pexels.com/videos/search?query=nature&per_page=15");
           if(response.isSuccessStatusCode()){
              VideoResponse videoResponse= response.parseAs(VideoResponse.class);
              requestResultListener.onSuccess(videoResponse);
           }else{
               requestResultListener.onRequestFailed(response.getStatusCode());
           }
        } catch (IOException e) {
           requestResultListener.onFailed(e);
        }
    }


    private HttpResponse preformGetRequest(String url) throws IOException {
        GenericUrl requestUrl=new GenericUrl(url);
        HttpRequest httpRequest=requestFactory.buildGetRequest(requestUrl);
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setAuthorization(App.appContext().getString(R.string.PEXELS_API_KEY));
        httpRequest.setHeaders(httpHeaders);
        httpRequest.setParser(new JsonObjectParser(jsonFactory));
        HttpHeaders headers=httpRequest.getHeaders();
        return httpRequest.execute();
    }


}
