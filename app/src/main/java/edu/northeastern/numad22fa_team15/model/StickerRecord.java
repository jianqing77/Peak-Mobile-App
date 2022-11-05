package edu.northeastern.numad22fa_team15.model;

import androidx.annotation.NonNull;

public class StickerRecord {

    private int stickerID;
    private int stickerResourceID;
    private String stickerName;
    private String senderUsername;
    private String receiverUsername;
    private String timestamp;

    /**
     * An empty constructor is required when using Firebase Realtime Database.
     */
    public StickerRecord() {}

    public StickerRecord(int stickerResourceID, String stickerName, String senderUsername, String receiverUsername) {
        this.stickerID = 0;
        this.stickerResourceID = stickerResourceID;
        this.stickerName = stickerName;
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.timestamp = null;
    }

    public StickerRecord(int stickerResourceID, String stickerName, String senderUsername, String receiverUsername, String timestamp) {
        this.stickerID = 0;
        this.stickerResourceID = stickerResourceID;
        this.stickerName = stickerName;
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.timestamp = timestamp;
    }

    public StickerRecord(int stickerID, int stickerResourceID, String stickerName, String senderUsername, String receiverUsername) {
        this.stickerID = stickerID;
        this.stickerResourceID = stickerResourceID;
        this.stickerName = stickerName;
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.timestamp = null;
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

    public String getSenderUsername() {
        return this.senderUsername;
    }

    public String getReceiverUsername() {
        return this.receiverUsername;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(@NonNull String timestamp) {
        this.timestamp = timestamp;
    }

}
