package edu.northeastern.numad22fa_team15.model;

import androidx.annotation.NonNull;

import java.util.Calendar;

public class StickerRecord {

    private int stickerID;
    private int stickerResourceID;
    private String stickerName;
    private String sender;
    private String receiver;
    private long timestamp;

    /**
     * An empty constructor is required when using Firebase Realtime Database.
     */
    public StickerRecord() {}

    public StickerRecord(int stickerResourceID, String stickerName, String sender, String receiver) {
        this.stickerID = 0;
        this.stickerResourceID = stickerResourceID;
        this.stickerName = stickerName;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = Calendar.getInstance().getTime().getTime();
    }

    public StickerRecord(int stickerResourceID, String stickerName, String sender, String receiver, long timestamp) {
        this.stickerID = 0;
        this.stickerResourceID = stickerResourceID;
        this.stickerName = stickerName;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
    }

    public int getStickerID() {
        return this.stickerID;
    }

    public void setStickerID(int stickerID) {
        this.stickerID = stickerID;
    }

    public int getStickerResourceID() {
        return this.stickerResourceID;
    }

    public String getStickerName() {
        return this.stickerName;
    }

    public String getSender() {
        return this.sender;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(@NonNull long timestamp) {
        this.timestamp = timestamp;
    }

}
