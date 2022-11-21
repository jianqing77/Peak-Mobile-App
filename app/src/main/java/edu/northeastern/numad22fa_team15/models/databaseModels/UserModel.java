package edu.northeastern.numad22fa_team15.models.databaseModels;

/**
 * This class represents a user entry in the SQLite database. A user entry contains the following
 * information: (1) userId, (2) firstName, (3) lastName, (4) username, and (5) passcode (which
 * should be a 4 digit number).
 */
public class UserModel {

    private int userId;
    private String firstName;
    private String lastName;
    private String username;
    private String passcode;

    public UserModel(String firstName, String lastName, String username, String passcode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.passcode = passcode;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasscode() {
        return this.passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

}

