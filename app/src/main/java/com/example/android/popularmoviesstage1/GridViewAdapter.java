package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.GridViewHolder> {

    private static final String TAG = GridViewAdapter.class.getSimpleName();

    private Movie[] movieList;

    final private GridItemClickListener mOnClickListener;

    public interface GridItemClickListener {
        void onItemClickListener(int clickedItemIndex);
    }

    public GridViewAdapter(GridItemClickListener gridItemClickListener) {
        this.mOnClickListener = gridItemClickListener;

    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForGridItem = R.layout.grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForGridItem, parent, false);
        return new GridViewHolder(view);

    }

    @Override
    public int getItemCount() {
        if (movieList == null) {
            return 0;
        } else {
            return movieList.length;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        Movie movie = movieList[position];
        holder.bind(movie.get_movie_poster());
    }

    public void setMovieData(Movie[] movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public Movie[] getMovieData() {
        return movieList;
    }

    public Movie getClickedMovie(int index) {
        if (movieList != null) {
            return movieList[index];
        }
        return null;
    }

    public class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView IMAGEVIEW_THUMBNAIL_POSTER;
        private final static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
        private final static String IMAGE_SIZE = "w185/";

        GridViewHolder(View itemView) {
            super(itemView);

            IMAGEVIEW_THUMBNAIL_POSTER = itemView.findViewById(R.id.imgView_moviePoster);
            itemView.setOnClickListener(this);
        }

        void bind(String thumbnail_path) {
            Picasso.get().load(IMAGE_BASE_URL + IMAGE_SIZE + thumbnail_path).into(IMAGEVIEW_THUMBNAIL_POSTER);

        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onItemClickListener(clickedPosition);
        }
    }
}
