package com.example.myfeed.data;

import com.google.api.client.util.Key;

import javax.annotation.ParametersAreNullableByDefault;

@ParametersAreNullableByDefault
public class Image {

   public Image(){

    }


    public Image(Integer id, Integer width, Integer height, String url, String photographer, String photographer_url, Integer photographer_id, Src src) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.url = url;
        this.photographer = photographer;
        this.photographer_url = photographer_url;
        this.photographer_id = photographer_id;
        this.src = src;
    }

    @Key
    Integer id;
    @Key
    Integer width;
    @Key
    Integer height;
    @Key
    String url;
    @Key
    String photographer;
    @Key
    String photographer_url;
    @Key
    Integer photographer_id;
    @Key
    Src src;

    public static class Src {

        public Src(String original, String large2x, String large, String medium, String small, String portrait, String landscape, String tiny) {
            this.original = original;
            this.large2x = large2x;
            this.large = large;
            this.medium = medium;
            this.small = small;
            this.portrait = portrait;
            this.landscape = landscape;
            this.tiny = tiny;
        }

        public Src() {
        }

        @Key
        String original;
        @Key
        String large2x;
        @Key
        String large;
        @Key
        String medium;
        @Key
        String small;
        @Key
        String portrait;
        @Key
        String landscape;
        @Key
        String tiny;


        public String getOriginal() {
            return original;
        }

        public String getLarge2x() {
            return large2x;
        }

        public String getLarge() {
            return large;
        }

        public String getMedium() {
            return medium;
        }

        public String getSmall() {
            return small;
        }

        public String getPortrait() {
            return portrait;
        }

        public String getLandscape() {
            return landscape;
        }

        public String getTiny() {
            return tiny;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getPhotographer_url() {
        return photographer_url;
    }

    public void setPhotographer_url(String photographer_url) {
        this.photographer_url = photographer_url;
    }

    public Integer getPhotographer_id() {
        return photographer_id;
    }

    public void setPhotographer_id(Integer photographer_id) {
        this.photographer_id = photographer_id;
    }

    public Src getSrc() {
        return src;
    }

    public void setSrc(Src src) {
        this.src = src;
    }


}
