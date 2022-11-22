package edu.northeastern.numad22fa_team15.models.databaseModels;

/**
 * This class represents a savings entry in sqlite database.
 * A saving entry contains the following information: (1) savingID, (2) savingGoal, (3) goalDescription,
 * (4) savingNum, (5) savingStatus
 */

public class SavingModel {
    private int savingID;
    private String savingGoal;
    private String goalDescription;
    private float savingNum;
    private boolean savingStatus;

    public SavingModel(String savingGoal, String goalDescription, float savingNum) {
        this.savingGoal = savingGoal;
        this.goalDescription = goalDescription;
        this.savingNum = savingNum;
    }

    public int getSavingID() {
        return this.savingID;
    }

    public void setSavingID(int savingID) {
        this.savingID = savingID;
    }

    public String getSavingGoal() {
        return this.savingGoal;
    }

    public void setSavingGoal(String savingGoal) {
        this.savingGoal = savingGoal;
    }

    public String getGoalDescription() {
        return this.goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    public float getSavingNum() {
        return this.savingNum;
    }

    public void setSavingNum(float savingNum) {
        this.savingNum = savingNum;
    }

    public boolean getSavingStatus() {
        return this.savingStatus;
    }

    public void setSavingStatus(Boolean savingStatus) {
        this.savingStatus = savingStatus;
    }
}
