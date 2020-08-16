package com.example.myfeed.data.response;


import com.example.myfeed.data.Image;
import com.google.api.client.util.Key;

import java.util.ArrayList;

public class ImageResponse extends ResponseBase{
    @Key
    ArrayList<Image> photos;

    public ArrayList<Image> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Image> photos) {
        this.photos = photos;
    }
}
