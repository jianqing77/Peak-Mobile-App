package edu.northeastern.numad22fa_team15.network;

import java.util.List;

import edu.northeastern.numad22fa_team15.model.MovieTv;
import edu.northeastern.numad22fa_team15.model.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieSearchService {

    @GET("?")
    Call<MovieTv> getMovieTvByTitle(@Query("t") String movieTvTitle,
                                    @Query("apikey") String apiKey);

    @GET("?")
    Call<Result> getMovieTvByCriteria(@Query("s") String movieTvTitle,
                                      @Query("y") String movieTvYear,
                                      @Query("type") String movieTvType,
                                      @Query("apikey") String apiKey);
}
