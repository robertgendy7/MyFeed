package com.example.myfeed.remote;


public interface HttpClientFunctions {
    void fetchImagesFromRemoteApi(RequestResultListener requestResultListener);
    void fetchVideosFromRemoteApi(RequestResultListener requestResultListener);
}
