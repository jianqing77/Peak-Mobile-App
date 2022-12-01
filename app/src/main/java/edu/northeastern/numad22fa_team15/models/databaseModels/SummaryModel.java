package edu.northeastern.numad22fa_team15.models.databaseModels;

/**
 * This class represents a summary entry in the sqlite database.
 * A summary entry has the following information: (1) summaryID, (2) startDate, (3) endDate,
 * (4) totalBudget, (5) currentExpense, (6) currentBalance.
 */

public class SummaryModel {
    private int summaryID;
    private Integer year;
    private Integer month;
    private float totalBudget;
    private float currentExpense;
    private float currentBalance;

    private float diningBudget;
    private float groceriesBudget;
    private float shoppingBudget;
    private float livingBudget;
    private float entertainmentBudget;
    private float educationBudget;
    private float beautyBudget;
    private float transportationBudget;
    private float healthBudget;
    private float travelBudget;
    private float petBudget;
    private float otherBudget;

    private float diningExpense;
    private float groceriesExpense;
    private float shoppingExpense;
    private float livingExpense;
    private float entertainmentExpense;
    private float educationExpense;
    private float beautyExpense;
    private float transportationExpense;
    private float healthExpense;
    private float travelExpense;
    private float petExpense;
    private float otherExpense;


    public SummaryModel(Integer year, Integer month, float totalBudget, float currentExpense,
                        float diningBudget, float diningExpense,
                        float groceriesBudget, float groceriesExpense,
                        float shoppingBudget, float shoppingExpense,
                        float livingBudget, float livingExpense,
                        float entertainmentBudget, float entertainmentExpense,
                        float educationBudget, float educationExpense,
                        float beautyBudget, float beautyExpense,
                        float transportationBudget, float transportationExpense,
                        float healthBudget, float healthExpense,
                        float travelBudget, float travelExpense,
                        float petBudget, float petExpense,
                        float otherBudget, float otherExpense) {

        this.year = year;
        this.month = month;
        this.totalBudget = totalBudget;
        this.currentExpense = currentExpense;

        this.diningBudget = diningBudget;
        this.diningExpense = diningExpense;
        this.groceriesBudget = groceriesBudget;
        this.groceriesExpense = groceriesExpense;
        this.shoppingBudget = shoppingBudget;
        this.shoppingExpense = shoppingExpense;
        this.livingBudget = livingBudget;
        this.livingExpense = livingExpense;
        this.entertainmentBudget = entertainmentBudget;
        this.entertainmentExpense = entertainmentExpense;
        this.educationBudget = educationBudget;
        this.educationExpense = educationExpense;
        this.beautyBudget = beautyBudget;
        this.beautyExpense = beautyExpense;
        this.transportationBudget = transportationBudget;
        this.transportationExpense = transportationExpense;
        this.healthBudget = healthBudget;
        this.healthExpense = healthExpense;
        this.travelBudget = travelBudget;
        this.travelExpense = travelExpense;
        this.petBudget = petBudget;
        this.petExpense = petExpense;
        this.otherBudget = otherBudget;
        this.otherExpense = otherExpense;
    }

    public int getSummaryID() {
        return this.summaryID;
    }

    public void setSummaryID(int summaryID) {
        this.summaryID = summaryID;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return this.month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public float getTotalBudget() {
        return this.totalBudget;
    }

    public void setTotalBudget(float totalBudget) {
        this.totalBudget = totalBudget;
    }

    public float getCurrentExpense() {
        return this.currentExpense;
    }

    public void setCurrentExpense(float currentExpense) {
        this.currentExpense = currentExpense;
    }

    public float getCurrentBalance() {
        return this.currentBalance;
    }

    public void setCurrentBalance(float currentBalance) {
        this.currentBalance = currentBalance;
    }

    public float getDiningBudget() {
        return this.diningBudget;
    }

    public void setDiningBudget(float diningBudget) {
        this.diningBudget = diningBudget;
    }

    public float getDiningExpense() {
        return this.diningExpense;
    }

    public void setDiningExpense(float diningExpense) {
        this.diningExpense = diningExpense;
    }

    public float getGroceriesBudget() {
        return this.groceriesBudget;
    }

    public void setGroceriesBudget(float groceriesBudget) {
        this.groceriesBudget = groceriesBudget;
    }

    public float getGroceriesExpense() {
        return this.groceriesExpense;
    }

    public void setGroceriesExpense(float groceriesExpense) {
        this.groceriesExpense = groceriesExpense;
    }

    public float getShoppingBudget() {
        return this.shoppingBudget;
    }

    public void setShoppingBudget(float shoppingBudget) {
        this.shoppingBudget = shoppingBudget;
    }

    public float getShoppingExpense() {
        return this.shoppingExpense;
    }

    public void setShoppingExpense(float shoppingExpense) {
        this.shoppingExpense = shoppingExpense;
    }

    public float getLivingBudget() {
        return this.livingBudget;
    }

    public void setLivingBudget(float livingBudget) {
        this.livingBudget = livingBudget;
    }

    public float getLivingExpense() {
        return this.livingExpense;
    }

    public void setLivingExpense(float livingExpense) {
        this.livingExpense = livingExpense;
    }

    public float getEntertainmentBudget() {
        return this.entertainmentBudget;
    }

    public void setEntertainmentBudget(float entertainmentBudget) {
        this.entertainmentBudget = entertainmentBudget;
    }

    public float getEntertainmentExpense() {
        return this.entertainmentExpense;
    }

    public void setEntertainmentExpense (float entertainmentExpense) {
        this.entertainmentExpense = entertainmentExpense;
    }

    public float getEducationBudget() {
        return this.educationBudget;
    }

    public void setEducationBudget() {
        this.educationBudget = educationBudget;
    }

    public float getEducationExpense() {
        return this.educationExpense;
    }

    public void setEducationExpense (float educationExpense) {
        this.educationExpense = educationExpense;
    }

    public float getBeautyBudget() {
        return this.beautyBudget;
    }

    public void setBeautyBudget(float beautyBudget) {
        this.beautyBudget = beautyBudget;
    }

    public float getBeautyExpense() {
        return this.beautyExpense;
    }

    public void setBeautyExpense(float beautyExpense) {
        this.beautyExpense = beautyExpense;
    }

    public float getTransportationBudget() {
        return this.transportationBudget;
    }

    public void setTransportationBudget(float transportationBudget) {
        this.transportationBudget = transportationBudget;
    }

    public float getTransportationExpense() {
        return this.transportationExpense;
    }

    public void setTransportationExpense(float transportationExpense) {
        this.transportationExpense = transportationExpense;
    }

    public float getHealthBudget() {
        return this.healthBudget;
    }

    public void setHealthBudget(float healthBudget) {
        this.healthBudget = healthBudget;
    }

    public float getHealthExpense() {
        return this.healthExpense;
    }

    public void setHealthExpense(float healthExpense) {
        this.healthExpense = healthExpense;
    }

    public float getTravelBudget() {
        return this.travelBudget;
    }

    public void setTravelBudget(float travelBudget) {
        this.travelBudget = travelBudget;
    }

    public float getTravelExpense() {
        return this.travelExpense;
    }

    public void setTravelExpense(float travelExpense) {
        this.travelExpense = travelExpense;
    }

    public float getPetBudget() {
        return this.petBudget;
    }

    public void setPetBudget(float petBudget) {
        this.petBudget = petBudget;
    }

    public float getPetExpense() {
        return this.petExpense;
    }

    public void setPetExpense(float petExpense) {
        this.petExpense = petExpense;
    }

    public float getOtherBudget() {
        return this.otherBudget;
    }

    public void setOtherBudget(float otherBudget) {
        this.otherBudget = otherBudget;
    }

    public float getOtherExpense() {
        return this.otherExpense;
    }

    public void setOtherExpense(float otherExpense) {
        this.otherExpense = otherExpense;
    }

}
