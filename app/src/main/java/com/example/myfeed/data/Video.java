package com.example.myfeed.data;

import com.google.api.client.util.Key;

import javax.annotation.ParametersAreNullableByDefault;

@ParametersAreNullableByDefault
public class Video  {

    public Video(int id, int width, int height, String url, String image, int duration, User user, VideoFiles[] video_files, VideoPicture[] video_pictures) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.url = url;
        this.image = image;
        this.duration = duration;
        this.user = user;
        this.video_files = video_files;
        this.video_pictures = video_pictures;
    }


    public Video() {

    }

    @Key
    int id;
    @Key

    Integer width;
    @Key
    Integer height;
    @Key
    String url;
    @Key
    String image;
    @Key
    Integer duration;
    @Key
    User user;
    @Key
    VideoFiles[] video_files;
    @Key
    VideoPicture[] video_pictures;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VideoFiles[] getVideo_files() {
        return video_files;
    }

    public void setVideo_files(VideoFiles[] video_files) {
        this.video_files = video_files;
    }

    public VideoPicture[] getVideo_pictures() {
        return video_pictures;
    }

    public void setVideo_pictures(VideoPicture[] video_pictures) {
        this.video_pictures = video_pictures;
    }


    public static class User{
        @Key
        int id;
        @Key
        String name;
        @Key
        String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class VideoFiles{
        @Key
        Integer id;
        @Key
        String quality;
        @Key
        String file_type;
        @Key
        Integer width;
        @Key
        Integer height;
        @Key
        String link;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getFile_type() {
            return file_type;
        }

        public void setFile_type(String file_type) {
            this.file_type = file_type;
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

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

    public static class VideoPicture{
        @Key
        Integer id;
        @Key
        String picture;
        @Key
        Integer nr;
    }



}
