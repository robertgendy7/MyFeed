package com.example.myfeed.data;

/**
 *   class that represents data for the UI elements this class
 *   can be change or eliminated in production according to the app needs
 *   not a class tht carries a core functionality .
 *
 **/

public class UserFeed {


    /** at first the feedID will be the video or image id
     * on download the it will be the download id given
     * from the download manager and it can be used to query download state
     * which is best handled according to production need. */

   private String title;
   private String thumbnail;
   private boolean isVideo;
   private String link;
   private int duration;
   private String filePath;
   private String feedID;




    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public UserFeed(UserFeed userFeed) {
        this.title=userFeed.title;
        this.thumbnail=userFeed.thumbnail;
        this.isVideo=userFeed.isVideo;
        this.link=userFeed.link;
        this.duration=userFeed.duration;
        this.filePath=userFeed.filePath;
        this.feedID=userFeed.feedID;
    }

    public UserFeed(String title, String thumbnail, boolean isVideo, String link, String feedID) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.isVideo = isVideo;
        this.link = link;
        this.feedID=feedID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public String getFeedID() {
        return feedID;
    }

    public void setFeedID(String feedID) {
        this.feedID = feedID;
    }
}


