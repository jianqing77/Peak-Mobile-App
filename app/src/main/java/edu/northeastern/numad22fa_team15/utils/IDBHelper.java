package edu.northeastern.numad22fa_team15.utils;

import edu.northeastern.numad22fa_team15.models.databaseModels.UserModel;

public interface IDBHelper {

    // Methods that interact with the user table
    boolean addUserTableUser(String username, String firstName, String lastName, String passcode);
    boolean confirmUserTableUser(String usernameInput, String passcodeInput);
    boolean updateUserPasswordTableUser(String usernameInput, String passcodeInput);
    boolean updateUserInfoTableUser(String username, String firstName, String lastName);
    boolean updateUserProfilePictureTableUser(byte[] profilePictureBlob);
    UserModel retrieveUserInfoTableUser();


}
