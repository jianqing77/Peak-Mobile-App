package edu.northeastern.numad22fa_team15.models.databaseModels;

/**
 * This class represents a saving entry in sqlite database.
 * A saving entry contains the following information: (1) savingID, (2) savingGoal, (3) goalDescription,
 * (4) savingSoFar, (5) savingStatus
 */

public class SavingModel {
    private int savingID;
    private float savingGoal;
    private String goalDescription;
    private float savingSoFar;
    private boolean savingStatus;

    public SavingModel(float savingGoal, String goalDescription, float savingSoFar, boolean savingStatus) {
        this.savingGoal = savingGoal;
        this.goalDescription = goalDescription;
        this.savingSoFar = savingSoFar;
        this.savingStatus = savingStatus;
    }

    public int getSavingID() {
        return this.savingID;
    }

    public void setSavingID(int savingID) {
        this.savingID = savingID;
    }

    public float getSavingGoal() {
        return this.savingGoal;
    }

    public void setSavingGoal(float savingGoal) {
        this.savingGoal = savingGoal;
    }

    public String getGoalDescription() {
        return this.goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    public float getSavingSoFar() {
        return this.savingSoFar;
    }

    public void setSavingSoFar(float savingSoFar) {
        this.savingSoFar = savingSoFar;
    }

    public boolean getSavingStatus() {
        return this.savingStatus;
    }

    public void setSavingStatus(boolean savingStatus) {
        this.savingStatus = savingStatus;
    }
}
