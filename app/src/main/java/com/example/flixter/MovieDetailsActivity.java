package com.example.flixter;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.flixter.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class MovieDetailsActivity extends AppCompatActivity {
    // the movie to display
    Movie movie;
    // the view objects
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivPoster;
    Context context;
    @NonNull
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // resolve the view objects
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        ivPoster=(ImageView) findViewById(R.id.ivPoster);

        String imageUrl;


        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));


        // set the title and overview
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());


        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);
        int w=0, h=0;

        //if poster is in landscape
        if (MovieDetailsActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //image url = backdrop image
            imageUrl = movie.getPosterPath();
            w=300;
            h=200;
        } else {
            //else image url=poster image
            imageUrl = movie.getBackdropPath();
            w=1000;
            h=500;
        }
        //Glide.with(MovieDetailsActivity.this).load(imageUrl).into(ivPoster);
        Glide.with(MovieDetailsActivity.this)
                .load(imageUrl).override(w, h)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.flicks_movie_placeholder).transform(new RoundedCornersTransformation(30, 10))
                .into(ivPoster);

    }

}