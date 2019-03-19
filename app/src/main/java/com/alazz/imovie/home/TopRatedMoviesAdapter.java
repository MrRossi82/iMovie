package com.alazz.imovie.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alazz.imovie.R;
import com.alazz.imovie.network.models.Movie;
import com.alazz.imovie.utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TopRatedMoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<Movie> mMovieList;
    private Context context;
    private final TopRatedMoviesAdapterListener mTopRatedMoviesAdapterListener;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;


    private String errorMsg;

    TopRatedMoviesAdapter(Context context, TopRatedMoviesAdapterListener mTopRatedMoviesAdapterListener) {
        this.context = context;
        this.mTopRatedMoviesAdapterListener = mTopRatedMoviesAdapterListener;
        mMovieList = new ArrayList<>();
    }

    public List<Movie> getMovies() {
        return mMovieList;
    }

    public void setMoviesList(List<Movie> moviesList) {
        mMovieList = moviesList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.item_top_rated_movies, parent, false);
                viewHolder = new MovieVH(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
        }
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie mMovie = mMovieList.get(position);

        switch (getItemViewType(position)) {

            case ITEM:

                final MovieVH movieVH = (MovieVH) holder;
                movieVH.mMovieTitleTextView.setText(mMovie.getTitle());
                movieVH.mMovieVoteAverageTextView.setText(context.getResources().getString(R.string.vote_average, String.valueOf(mMovie.getVoteAverage())));
                movieVH.mMovieReleaseDate.setText(mMovie.getReleaseDate());
                movieVH.mMovieVoteDecTextView.setText(mMovie.getOverview());
                ImageUtils.onLoadImage(context,mMovie.getPosterPath(),movieVH.mPosterImageView);

                break;

            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;

                loadingVH.mProgressBar.setVisibility(View.VISIBLE);

                break;
        }


    }

    @Override
    public int getItemCount() {
        return mMovieList == null ? 0 : mMovieList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mMovieList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;

    }


    public void add(Movie r) {
        mMovieList.add(r);
        notifyItemInserted(mMovieList.size() - 1);
    }

    void addAll(List<Movie> moveMovies) {
        for (Movie Movie : moveMovies) {
            add(Movie);
        }
    }

    private void remove(Movie r) {
        int position = mMovieList.indexOf(r);
        if (position > -1) {
            mMovieList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    void addLoadingFooter() {
        isLoadingAdded = true;
    }

    void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mMovieList.size() - 1;
        Movie Movie = getItem(position);

        if (Movie != null) {
            mMovieList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private Movie getItem(int position) {
        return mMovieList.get(position);
    }



    class MovieVH extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_item_movie_title)
        TextView mMovieTitleTextView;

        @BindView(R.id.textView_item_movie_vote_average)
        TextView mMovieVoteAverageTextView;

        @BindView(R.id.textView_item_movie_dec)
        TextView mMovieVoteDecTextView;

        @BindView(R.id.imageView_item_movie_poster)
        ImageView mPosterImageView;

        @BindView(R.id.textView_item_movie_release_date)
        TextView mMovieReleaseDate;


        MovieVH(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(view1 -> mTopRatedMoviesAdapterListener.onMovieSelected(mMovieList.get(getAdapterPosition())));


        }
    }


    class LoadingVH extends RecyclerView.ViewHolder {


        @BindView(R.id.load_more_progress)
        ProgressBar mProgressBar;

        LoadingVH(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }


    }


    public interface TopRatedMoviesAdapterListener {
        void onMovieSelected(Movie movie);
    }


}