package edu.northeastern.numad22fa_team15.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Result {

    @SerializedName("Search")
    private List<MovieTv> movieTvList;
    @SerializedName("totalResults")
    private String totalResults;
    @SerializedName("Response")
    private String response;

    public Result(List<MovieTv> movieTvList, String totalResults, String response) {
        this.movieTvList = movieTvList;
        this.totalResults = totalResults;
        this.response = response;
    }

    public List<MovieTv> getMovieTvList() {
        List<MovieTv> listCopy = new ArrayList<MovieTv>();
        for (MovieTv movieTv : this.movieTvList) {
            listCopy.add(movieTv);
        }
        return listCopy;
    }

    public String getTotalResults() {
        return this.totalResults;
    }

    public String getResponse() {
        return this.response;
    }

}