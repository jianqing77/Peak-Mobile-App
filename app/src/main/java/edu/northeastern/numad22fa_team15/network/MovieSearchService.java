package edu.northeastern.numad22fa_team15.network;

import java.util.List;

import edu.northeastern.numad22fa_team15.model.MovieTv;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieSearchService {

    /*
    This is the one Xinyu used to have
    @GET("?")
    Call<MovieTv> getMovieTvByTitle(@Query("t") String movieTitle,
                                          @Query("apikey") String apiKey);

     */

    // search by only title
    @GET("?")
    Call<List<MovieTv>> getMovieTvByTitle(@Query("s") String movieTitle,
                                          @Query("apikey") String apiKey);

    // search by title, year, and type
    @GET("?")
    Call<List<MovieTv>> getMovieTvByFilter(@Query("s") String movieTitle,
                                           @Query("y") String movieYear,
                                           @Query("type") String movieType,
                                           @Query("apikey") String apiKey);

    // search by title and year
    @GET("?")
    Call<List<MovieTv>> getMovieTvByYear(@Query("s") String movieTitle,
                                         @Query("y") String movieYear,
                                         @Query("apikey") String apiKey);

    // search by title and type
    @GET("?")
    Call<List<MovieTv>> getMovieTvByType(@Query("s") String movieTitle,
                                         @Query("type") String movieType,
                                         @Query("apikey") String apiKey);

}
