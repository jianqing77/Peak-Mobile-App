package edu.northeastern.numad22fa_team15.models.databaseModels;

/**
 * This class represents a transaction entry in sqlite database.
 * A transaction entry contains the following information: (1) transactionID, (2) expense, (3) category,
 * (4) description, (5) transactionDate, (6) receiptPhoto, (7) summaryID.
 */

import java.sql.Blob;

import edu.northeastern.numad22fa_team15.utils.Category;

public class TransactionModel {
    private int transactionID;
    private float expense;
    private Category category;
    private String description;
    private String transactionDate;
    private Blob receiptPhoto;
    private int summaryID;

    public TransactionModel(float expense, Category category, String description, String transactionDate) {
        this.expense = expense;
        this.category = category;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    public int getTransactionID() {
        return this.transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public float getExpense() {
        return this.expense;
    }

    public void setExpense() {
        this.expense = expense;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionDate() {
        return this.transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Blob getReceiptPhoto() {
        return this.receiptPhoto;
    }

    public void setReceiptPhoto(Blob receiptPhoto) {
        this.receiptPhoto = receiptPhoto;
    }

    public int getSummaryID() {
        return this.summaryID;
    }

    public void setSummaryID(int summaryID) {
        this.summaryID = summaryID;
    }
}
