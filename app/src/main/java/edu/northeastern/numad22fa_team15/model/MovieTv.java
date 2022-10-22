package edu.northeastern.numad22fa_team15.model;

import com.google.gson.annotations.SerializedName;

public class MovieTv {

    @SerializedName("Title")
    private String movieTvTitle;
    @SerializedName("Year")
    private String movieTvYear;
    @SerializedName("imdbID")
    private String movieTvImdbID;
    //    @SerializedName("Response")
//    private String response;
    @SerializedName("Type")
    private String type;
    @SerializedName("Poster")
    private String posterURL;

    public MovieTv(String movieTvTitle, String movieTvYear, String movieTvImdbID, String type, String posterURL) {
        this.movieTvTitle = movieTvTitle;
        this.movieTvYear = movieTvYear;
        this.movieTvImdbID = movieTvImdbID;
        this.type = type;
        this.posterURL = posterURL;
    }


//    public MovieTv(String movieTvTitle, String movieTvYear, String movieTvImdbID, String response, String type, String posterURL) {
//        this.movieTvTitle = movieTvTitle;
//        this.movieTvYear = movieTvYear;
//        this.movieTvImdbID = movieTvImdbID;
//        this.response = response;
//        this.type = type;
//        this.posterURL = posterURL;
//    }

    public String getMovieTvTitle() {
        return this.movieTvTitle;
    }

    public String getMovieTvYear() {
        return this.movieTvYear;
    }

    public String getMovieTvImdbID() {
        return this.movieTvImdbID;
    }

//    public String getResponse() {
//        return this.response;
//    }

    public String getType() {
        return this.type;
    }

    public String getPosterURL() {
        return this.posterURL;
    }

//    public void setMovieTvTitle(String movieTvTitle) {
//        this.movieTvTitle = movieTvTitle;
//    }
//
//    public void setMovieTvYear(String movieTvYear) {
//        this.movieTvYear = movieTvYear;
//    }
//
//    public void setMovieTvImdbID(String movieTvImdbID) {
//        this.movieTvImdbID = movieTvImdbID;
//    }

}