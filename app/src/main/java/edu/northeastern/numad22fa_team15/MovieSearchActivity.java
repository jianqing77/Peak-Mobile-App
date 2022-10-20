package edu.northeastern.numad22fa_team15;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import edu.northeastern.numad22fa_team15.model.MovieTv;
import edu.northeastern.numad22fa_team15.network.MovieSearchService;
import edu.northeastern.numad22fa_team15.network.RetrofitMovieClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieSearchActivity extends AppCompatActivity {

    private static final String TAG = "MovieSearchActivity___";
    private static final String API_KEY = "ecafb541";

    private Handler textHandler = new Handler();
    private MovieSearchThread movieSearchThread;
    private Thread movieThread;

    private ProgressDialog progressDialog;

    EditText titleInput;
    TextView type;
    TextView title;
    TextView year;
    TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        titleInput = (EditText) findViewById(R.id.movie_tv_title_edit_text);
        type = (TextView) findViewById(R.id.movie_tv_type_text_view);
        title = (TextView) findViewById(R.id.movie_tv_title_text_view);
        year = (TextView) findViewById(R.id.movie_tv_year_text_view);
        id = (TextView) findViewById(R.id.imdb_id_text_view);

        progressDialog = new ProgressDialog(MovieSearchActivity.this);
        progressDialog.setMessage("Loading...");

        movieSearchThread = new MovieSearchThread();
    }

    public void searchMovieUsingApi(View view) {
        progressDialog.show();

        if (movieThread != null && movieThread.isAlive()) {
            String runningSearchMessage = "A search is currently running";
            Snackbar.make(view, runningSearchMessage, Snackbar.LENGTH_SHORT).show();
            return;
        }
        movieThread = new Thread(movieSearchThread);
        movieThread.start();
    }

    class MovieSearchThread implements Runnable {

        @Override
        public void run() {
            String titleInputString = titleInput.getText().toString();
            Log.v(TAG, "Searching for " + titleInputString);

            MovieSearchService service = RetrofitMovieClientInstance.getRetrofitInstance().create(MovieSearchService.class);
            Call<MovieTv> call = service.getMovieTvByTitle(titleInputString, API_KEY);
            call.enqueue(new Callback<MovieTv>() {
                @Override
                public void onResponse(Call<MovieTv> call, Response<MovieTv> response) {
                    Log.v(TAG, "onResponse() " + titleInputString);
                    progressDialog.dismiss();

                    MovieTv movieResponse = response.body();

                    boolean searchResult = Boolean.parseBoolean(movieResponse.getResponse());
                    Log.v(TAG, "Search result: " + searchResult);
                    if (searchResult) {
                        type.setText(movieResponse.getType());
                        title.setText(movieResponse.getMovieTvTitle());
                        year.setText(movieResponse.getMovieTvYear());
                        id.setText(movieResponse.getMovieTvImdbID());
                    } else {
                        type.setText("not found");
                    }
                }

                @Override
                public void onFailure(Call<MovieTv> call, Throwable t) {
                    Log.v(TAG, "onFailure() " + titleInputString);
                    progressDialog.dismiss();

                    String searchFailureMessage= "Something went wrong. Please try again later!";
                    Snackbar.make(findViewById(android.R.id.content), searchFailureMessage, Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onPause() {
        Log.v(TAG, "onPause()");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG, "onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        Log.v(TAG, "onResume()");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.v(TAG, "onRestart()");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.v(TAG, "onStart()");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.v(TAG, "onStop()");
        super.onStop();
    }

}
