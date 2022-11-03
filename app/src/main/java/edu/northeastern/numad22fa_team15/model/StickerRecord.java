package edu.northeastern.numad22fa_team15.model;

public class StickerRecord {

    private int stickerID;
    private String stickerResourceID;
    private String senderUsername;
    private String timeStamp;

    /**
     * An empty constructor is required when using Firebase Realtime Database.
     */
    public StickerRecord() {}

    public StickerRecord(String stickerResourceID, String senderUsername, String timeStamp) {
        this.stickerID = 0;
        this.stickerResourceID = stickerResourceID;
        this.senderUsername = senderUsername;
        this.timeStamp = timeStamp;
    }

    public StickerRecord(int stickerID, String stickerResourceID, String senderUsername, String timeStamp) {
        this.stickerID = stickerID;
        this.stickerResourceID = stickerResourceID;
        this.senderUsername = senderUsername;
        this.timeStamp = timeStamp;
    }

    public int getStickerID() {
        return this.stickerID;
    }

    public String getStickerResourceID() {
        return this.stickerResourceID;
    }

    public String getSenderUsername() {
        return this.senderUsername;
    }

    public String getTimeStamp() {
        return this.timeStamp;
    }

}
