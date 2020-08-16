package com.example.myfeed.remote;

import com.example.myfeed.data.response.ResponseBase;

public interface RequestResultListener {
        void onSuccess(ResponseBase response);
        void onRequestFailed(int httpResponseCode);
        void onFailed(Exception e);
}
