package com.example.myfeed.data.response;

import com.google.api.client.util.Key;

public abstract class ResponseBase {
    @Key
    int total_results;
    @Key
    int page;
    @Key
    int per_page;


    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }
}
