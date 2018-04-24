package com.example.android.popularmoviesstage1;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private final String MOVIE_POSTER;
    private final String TITLE;
    private final String RELEASE_DATE;
    private final String PLOT_SYNOPSIS;
    private final double VOTE_AVERAGE;

    public Movie(String movie_poster, String title, String release_date, String plot_synopsis, double vote_average) {
        this.MOVIE_POSTER = movie_poster;
        this.TITLE = title;
        this.RELEASE_DATE = release_date;
        this.PLOT_SYNOPSIS = plot_synopsis;
        this.VOTE_AVERAGE = vote_average;

    }

    public String get_movie_poster() {
        if(this.MOVIE_POSTER != null) {
            return this.MOVIE_POSTER;
        }
        return null;
    }

    public String get_title() {
        if(this.TITLE != null) {
            return TITLE;
        }
        return null;
    }

    public String get_release_date() {
        if(this.RELEASE_DATE != null) {
            return RELEASE_DATE;
        }
        return null;
    }

    public String get_plot_synopsis() {
        if(this.PLOT_SYNOPSIS != null) {
            return PLOT_SYNOPSIS;
        }
        return null;
    }

    public double get_vote_average() {
        return VOTE_AVERAGE;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(MOVIE_POSTER);
        parcel.writeString(TITLE);
        parcel.writeString(RELEASE_DATE);
        parcel.writeString(PLOT_SYNOPSIS);
        parcel.writeDouble(VOTE_AVERAGE);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel in) {
        MOVIE_POSTER = in.readString();
        TITLE = in.readString();
        RELEASE_DATE = in.readString();
        PLOT_SYNOPSIS = in.readString();
        VOTE_AVERAGE = in.readDouble();

    }

}
