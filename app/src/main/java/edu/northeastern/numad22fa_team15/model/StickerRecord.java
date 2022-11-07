package edu.northeastern.numad22fa_team15.model;

import java.util.Calendar;

public class StickerRecord implements Comparable<StickerRecord>{

    private int stickerID;
    private int stickerResourceID;
    private String stickerName;
    private String sender;
    private String receiver;
    private long timestamp;
    private boolean processedByServer;

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
        this.processedByServer = false;
    }

    public StickerRecord(int stickerResourceID, String stickerName, String sender, String receiver, long timestamp) {
        this.stickerID = 0;
        this.stickerResourceID = stickerResourceID;
        this.stickerName = stickerName;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
        this.processedByServer = false;
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

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean getProcessedByServer() {
        return this.processedByServer;
    }

    public void setProcessedByServer(boolean processedByServer) {
        this.processedByServer = processedByServer;
    }

    public int compareTo(StickerRecord s) {
        if (this.getTimestamp() > s.getTimestamp()) {
            return -1;
        } else if (this.getTimestamp() < s.getTimestamp()) {
            return 1;
        } else {
            return 0;
        }
    }

}
