package com.alazz.imovie.movieDetails;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alazz.imovie.R;
import com.alazz.imovie.network.models.Movie;
import com.alazz.imovie.utils.GenreUtils;
import com.alazz.imovie.utils.ImageUtils;
import com.google.android.material.chip.Chip;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsFragment extends Fragment {

    @BindView(R.id.textView_movie_details_movie_title)
    TextView mMovieTitleTextView;

    @BindView(R.id.textView_movie_details_movie_overview)
    TextView mMovieOverviewTextView;

    @BindView(R.id.textView_movie_details_movie_vote_avrage)
    TextView mVoteAverageTextView;

    @BindView(R.id.ImageView_movie_details_movie_backdrop)
    ImageView mMovieBackDropImageView;

    @BindView(R.id.ImageView_movie_details_movie_poster)
    ImageView mMoviePosterImageView;

    @BindView(R.id.chip_movie_details_movie_genre_one)
    Chip mMovieGenreOneChip;

    @BindView(R.id.chip_movie_details_movie_genre_two)
    Chip mMovieGenreTwoChip;



    public MovieDetailsFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        ButterKnife.bind(this, mView);


        initView();

        return mView;
    }

    private void initView() {


        Movie mMovie;

        if (getArguments() != null) {

            mMovie =MovieDetailsFragmentArgs.fromBundle(getArguments()).getMovie();

            mMovieTitleTextView.setText(mMovie.getTitle());
            mMovieOverviewTextView.setText(mMovie.getOverview());
            mVoteAverageTextView.setText(getString(R.string.vote_average, String.valueOf(mMovie.getVoteAverage())));

            ImageUtils.onLoadImage(requireActivity(),mMovie.getBackdropPath(),mMovieBackDropImageView);
            ImageUtils.onLoadImage(requireActivity(),mMovie.getPosterPath(),mMoviePosterImageView);
            mMovieGenreOneChip.setText(GenreUtils.getGenre(requireActivity(),mMovie.getGenreIds().get(0)));
            mMovieGenreTwoChip.setText(GenreUtils.getGenre(requireActivity(),mMovie.getGenreIds().get(1)));

            Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(mMovie.getTitle());

        }

    }




}
