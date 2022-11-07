package edu.northeastern.numad22fa_team15.activities.movieApiActivities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.numad22fa_team15.R;
import edu.northeastern.numad22fa_team15.model.MovieTv;
import edu.northeastern.numad22fa_team15.model.Result;
import edu.northeastern.numad22fa_team15.movieTvRecyclerUtil.MovieTvAdapter;
import edu.northeastern.numad22fa_team15.network.MovieSearchService;
import edu.northeastern.numad22fa_team15.network.RetrofitMovieClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieSearchActivityWithRecyclerView extends AppCompatActivity {

    private static final String TAG = "MovieSearchActivity___";

    private String API_KEY;
    private MovieSearchThread movieSearchThread;
    private Thread movieThread;
    private List<MovieTv> matchResults;
    private ProgressDialog progressDialog;
    private int resetButtonCounter;

    EditText titleInput;
    TextView yearInput;
    CheckBox yearCheckBox;
    RadioGroup typeRadioGroup;
    RadioButton typeRadioButton;
    RecyclerView movieTvRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search_w_recycler_view);

        // Retrieve OMDB API Key
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
            API_KEY = applicationInfo.metaData.getString("API_KEY");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to load metadata: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to load metadata: " + e.getMessage());
        }

        resetButtonCounter = 0;

        matchResults = new ArrayList<>();
        movieTvRecyclerView = findViewById(R.id.movie_tv_recycler_view);

        // Define the way in which the RecyclerView is oriented
        movieTvRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Associates the adapter with the RecyclerView
        movieTvRecyclerView.setAdapter(new MovieTvAdapter(matchResults, this));

        // Input: title, year, type
        titleInput = (EditText) findViewById(R.id.movie_tv_title_edit_text);
        yearInput = (TextView) findViewById(R.id.customized_year_text_view);
        yearCheckBox = (CheckBox) findViewById(R.id.customized_year_check_box);
        yearCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    yearInput.setText(R.string.customized_year);
                } else {
                    yearPickerDialog();
                }
            }
        });
        typeRadioGroup = (RadioGroup) findViewById(R.id.type_radio_group);
        typeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
                typeRadioButton = findViewById(checkedID);
            }
        });
        typeRadioButton = findViewById(R.id.no_preference_radio_button);

        progressDialog = new ProgressDialog(MovieSearchActivityWithRecyclerView.this);
        progressDialog.setMessage("Loading...");

        movieSearchThread = new MovieSearchThread();
    }

    /**
     * This method gets called when the BACK button is pressed. It confirms a user's action to
     * dismiss the activity.
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog
                .Builder(MovieSearchActivityWithRecyclerView.this);
        alertDialog.setTitle("Confirm Exit");
        if (!matchResults.isEmpty()) {
            alertDialog.setMessage("Are you sure to ignore the search results and close the activity?");
        } else {
            alertDialog.setMessage("Are you sure to close the activity?");
        }
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    /**
     * Generate a year picker dialog that allows users to specify which year they want to search the
     * movie/tv in.
     */
    private void yearPickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MovieSearchActivityWithRecyclerView.this);
        builder.setTitle("Pick a specific year");

        // Set view to be a number picker (1888 - 2022)
        NumberPicker yearPicker = new NumberPicker(MovieSearchActivityWithRecyclerView.this);
        yearPicker.setMinValue(1888);
        yearPicker.setMaxValue(2022);
        yearPicker.setValue(2008);
        builder.setView(yearPicker);

        // Choosing "Confirm" will close the year picker dialog
        builder.setPositiveButton("Confirm", (DialogInterface.OnClickListener) (dialog, which) -> {
            String yearString = String.valueOf(yearPicker.getValue());
            yearInput.setText(yearString);
            dialog.cancel();
        });
        // Choosing "Cancel" will close the year picker dialog
        builder.setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
            yearCheckBox.setChecked(false);
            dialog.cancel();
        });
        // Nothing happens if the user clicks anywhere outside the dialog
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        // Display the Alert Dialog to the user
        alertDialog.show();
    }

    /**
     * Generate a required title input dialog that asks users to enter a movie/series title.
     */
    private void requiredTitleInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MovieSearchActivityWithRecyclerView.this);
        builder.setTitle("Warning");

        // Choosing "Ok" will close the required title input dialog
        builder.setPositiveButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        builder.setMessage("Title input is required.");

        // Nothing happens if the user clicks anywhere outside the dialog
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        // Display the Alert Dialog to the user
        alertDialog.show();
    }

    /**
     * Generate a reset dialog that asks users to confirm their reset action.
     */
    private void resetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MovieSearchActivityWithRecyclerView.this);
        builder.setTitle("Alert");

        builder.setMessage("Are you sure to clear all the selections and search results?");

        // Choosing "Confirm" will reset the selection and the results
        builder.setPositiveButton("Confirm", (DialogInterface.OnClickListener) (dialog, which) -> {
            clearSelection();
            clearResults();
            dialog.cancel();
        });
        // Choosing "Cancel" will close the reset dialog
        builder.setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        // Nothing happens if the user clicks anywhere outside the dialog
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        // Display the Alert Dialog to the user
        alertDialog.show();
    }

    /**
     * Clear user selection.
     */
    private void clearSelection() {
        // Empty title input
        titleInput.setText("");
        // Clear year input
        yearCheckBox.setChecked(false);
        yearInput.setText(getString(R.string.customized_year));
        // Set type preference to "no preference"
        typeRadioGroup.check(R.id.no_preference_radio_button);
    }

    /**
     * Clear results.
     */
    private void clearResults() {
        matchResults.clear();
        movieTvRecyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     * This method gets called when the RESET button is pressed.
     * @param view view
     */
    public void resetSelectionAndResults(View view) {
        // If it is the first time the reset button is pressed, show a reset dialog.
        if (resetButtonCounter == 0) {
            resetDialog();
            resetButtonCounter++;
        } else {
            clearSelection();
            clearResults();
        }
    }

    /**
     * This method gets called when the SEARCH button is pressed.
     * @param view view
     */
    public void searchMovieUsingApi(View view) {
        // Close Keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);

        // Check if title input has anything
        String titleInputString = titleInput.getText().toString();
        if (titleInputString == null || titleInputString.isEmpty()) {
            requiredTitleInputDialog();
            return;
        }

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
            String yearInputString = yearInput.getText().toString();
            if (yearInputString.equals(getString(R.string.customized_year))) {
                yearInputString = "";
            }
            String typeInputString = typeRadioButton.getText().toString();
            if (typeInputString.equals(getString(R.string.no_preference))) {
                typeInputString = "";
            }

            StringBuilder searchMessageStringBuilder = new StringBuilder();
            searchMessageStringBuilder.append("Searching for title: ");
            searchMessageStringBuilder.append(titleInputString);
            searchMessageStringBuilder.append(". Year: ");
            searchMessageStringBuilder.append(yearInputString);
            searchMessageStringBuilder.append(". Type: ");
            searchMessageStringBuilder.append(typeInputString);
            Log.v(TAG, searchMessageStringBuilder.toString());

            MovieSearchService service = RetrofitMovieClientInstance.getRetrofitInstance().create(MovieSearchService.class);

            callServiceGetMovieTvByCriteria(service, titleInputString, yearInputString, typeInputString, API_KEY);
        }

        private void callServiceGetMovieTvByCriteria(MovieSearchService service, String titleInputString,
                                                     String yearInputString, String typeInputString,
                                                     String apiKey) {
            Call<Result> call = service.getMovieTvByCriteria(titleInputString, yearInputString, typeInputString, apiKey);
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Log.v(TAG, "onResponse() " + titleInputString);
                    progressDialog.dismiss();

                    Result movieTvResponses = response.body();
                    Log.v(TAG, "Total results: " + movieTvResponses.getTotalResults() +
                            ". Response: " + movieTvResponses.getResponse());

                    // Clear previous result
                    matchResults.clear();
                    movieTvRecyclerView.getAdapter().notifyDataSetChanged();

                    // Check if the response field is False
                    if (!Boolean.parseBoolean(movieTvResponses.getResponse())) {
                        String searchFailureMessage= "No matches found.";
                        Snackbar.make(findViewById(android.R.id.content), searchFailureMessage, Snackbar.LENGTH_SHORT).show();
                        return;
                    }

                    for (MovieTv movieTvResponse : movieTvResponses.getMovieTvList()) {
                        Log.v(TAG, "Response: " + movieTvResponse.getMovieTvTitle());
                        addResultToList(movieTvResponse);
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Log.v(TAG, "onFailure() " + titleInputString);
                    progressDialog.dismiss();

                    String searchFailureMessage= "Something went wrong. Please try again later!";
                    Snackbar.make(findViewById(android.R.id.content), searchFailureMessage, Snackbar.LENGTH_SHORT).show();
                }
            });
        }

        /**
         * Add a movie/tv match result to the match results list and notify RecyclerView about the
         * change.
         * @param movieTvResponse a movie/tv match result
         */
        private void addResultToList(MovieTv movieTvResponse) {
            matchResults.add(movieTvResponse);
            movieTvRecyclerView.getAdapter().notifyDataSetChanged();
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
