package edu.northeastern.numad22fa_team15.network;

import java.util.List;

import edu.northeastern.numad22fa_team15.model.MovieTv;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieSearchService {

    @GET("?")
    Call<MovieTv> getMovieTvByTitle(@Query("t") String movieTitle,
                                          @Query("apikey") String apiKey);
}
