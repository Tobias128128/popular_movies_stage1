package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    // I decided to leave the variables here for later manipulations.
    @BindView(R.id.img_view_movie_poster) ImageView img_view_movie_poster;
    @BindView(R.id.txt_view_title) TextView txt_view_title;
    @BindView(R.id.txt_view_release_date) TextView txt_view_release_date;
    @BindView(R.id.txt_view_vote_average) TextView txt_view_vote_average;
    @BindView(R.id.txt_view_plot_synopsis) TextView txt_view_plot_synopsis;

    private final static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    private final static String IMAGE_SIZE = "w185/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent former_intent = getIntent();
        if (former_intent != null) {
            if (former_intent.hasExtra(MainActivity.PARCELABLE_MOVIE_SELECTED_ITEM)) {
                Movie selectedMovie = former_intent.getExtras().getParcelable(MainActivity.PARCELABLE_MOVIE_SELECTED_ITEM);
                if ((selectedMovie.get_movie_poster() != null) && (selectedMovie.get_title() != null) && (selectedMovie.get_release_date() != null)  && (selectedMovie.get_plot_synopsis() != null)) {


                    Picasso.get().load(IMAGE_BASE_URL + IMAGE_SIZE + selectedMovie.get_movie_poster()).into(img_view_movie_poster);
                    txt_view_title.setText(selectedMovie.get_title());
                    txt_view_release_date.setText(selectedMovie.get_release_date());
                    txt_view_vote_average.setText(String.valueOf(selectedMovie.get_vote_average()));
                    txt_view_plot_synopsis.setText(selectedMovie.get_plot_synopsis());
                }

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
