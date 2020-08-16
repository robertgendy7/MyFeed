package com.example.myfeed.remote;


public final class Remote {

   private HttpClientFunctions httpClientFunctions;

    public Remote(HttpClientFunctions httpClientFunctions) {
        this.httpClientFunctions = httpClientFunctions;
    }

   public void fetchImages(RequestResultListener requestResultListener){
        httpClientFunctions.fetchImagesFromRemoteApi(requestResultListener);
    }

   public void fetchVideos(RequestResultListener requestResultListener){
        httpClientFunctions.fetchVideosFromRemoteApi(requestResultListener);
    }


}

