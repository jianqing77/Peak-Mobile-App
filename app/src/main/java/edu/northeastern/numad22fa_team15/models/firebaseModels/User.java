package edu.northeastern.numad22fa_team15.models.firebaseModels;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private List<String> friends;
    private int numOfStickersSent;
    // This will be used for the stickerID in the StickerRecord object to ensure
    // every sticker history has an unique stickerID;
    private int numOfStickersReceived;
    private List<StickerRecord> stickerRecords;

    /**
     * An empty constructor is required when using Firebase Realtime Database.
     */
    public User() {}

    public User(String username) {
        this.username = username;
        this.friends = new ArrayList<String>();
        this.numOfStickersSent = 0;
        this.numOfStickersReceived = 0;
        this.stickerRecords = new ArrayList<StickerRecord>();
    }

    public String getUsername() {
        return this.username;
    }

    public List<String> getFriends() {
        List<String> listCopy = new ArrayList<String>();
        for (String friend : this.friends) {
            listCopy.add(friend);
        }
        return listCopy;
    }

    public int getNumOfStickersSent() {
        return this.numOfStickersSent;
    }

    public int getNumOfStickersReceived() {
        return this.numOfStickersReceived;
    }

    public List<StickerRecord> getStickerRecords() {
        List<StickerRecord> recordsCopy = new ArrayList<StickerRecord>();
        for (StickerRecord stickerRecord : this.stickerRecords) {
            recordsCopy.add(stickerRecord);
        }
        return recordsCopy;
    }

}
