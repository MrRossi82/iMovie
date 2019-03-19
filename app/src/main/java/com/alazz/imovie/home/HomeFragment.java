package com.alazz.imovie.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alazz.imovie.R;
import com.alazz.imovie.base.PaginationScrollListener;
import com.alazz.imovie.network.models.Movie;
import com.alazz.imovie.network.models.Movies;
import com.github.ybq.android.spinkit.SpinKitView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment implements HomeContract.View,TopRatedMoviesAdapter.TopRatedMoviesAdapterListener{

    @BindView(R.id.recyclerView_list_top_rated_movies)
    RecyclerView mTopRatedMoviesRecyclerView;

    @BindView(R.id.view_empty_movie)
    ConstraintLayout mEmptyView;

    @BindView(R.id.view_loading_progress)
    SpinKitView mProgressBar;

    @BindView(R.id.swipeRefreshLayout_home)
    SwipeRefreshLayout mHomeSwipeRefreshLayout;

    private static final int PAGE_START = 1;
    private int mCurrentPage = PAGE_START;
    private boolean isLastPage = false;
    private int mTotalPage = 20;
    private boolean isLoading = false;

    private TopRatedMoviesAdapter mTopRatedMoviesAdapter;

    private HomeContract.Presenter mPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, mView);

        initView();

        return mView;
    }

    private void initView() {

        setUpTopRatedRecyclerView();


    }


    private void setUpTopRatedRecyclerView() {

        mHomeSwipeRefreshLayout.setOnRefreshListener(this::OnRefresh);

        LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(requireActivity());
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mTopRatedMoviesAdapter = new TopRatedMoviesAdapter(getActivity(), this);
        mTopRatedMoviesRecyclerView.setLayoutManager(mLayoutManager);
        mTopRatedMoviesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mTopRatedMoviesRecyclerView.setAdapter(mTopRatedMoviesAdapter);

        mTopRatedMoviesRecyclerView.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                mCurrentPage += 1;
                Handler handler = new Handler();
                handler.postDelayed(() -> mPresenter.getMoreTopRatedMovies(mCurrentPage), 1000); // for Better performance

            }

            @Override
            public int getTotalPageCount() {
                return mTotalPage;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });


    }


    @Override
    public void setTopRatedMoviesList(Movies topRatedMoviesList) {

        if (mCurrentPage <= mTotalPage) mTopRatedMoviesAdapter.addLoadingFooter();
        else isLastPage = true;

        mTopRatedMoviesAdapter.addAll(topRatedMoviesList.getMovies());


    }

    @Override
    public void setMoreTopRatedMoviesList(Movies moreTopRatedMoviesList) {

        mTopRatedMoviesAdapter.removeLoadingFooter();
        isLoading = false;

        mTopRatedMoviesAdapter.addAll(moreTopRatedMoviesList.getMovies());

        if (mCurrentPage != mTotalPage) {

            mTopRatedMoviesAdapter.addLoadingFooter();

        } else {

            isLastPage = true;

        }

    }


    @Override
    public void makeToast(@StringRes int stringId) {
        Toast.makeText(getActivity(), getString(stringId), Toast.LENGTH_LONG).show();
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }



    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.mPresenter = presenter;

    }

    @Override
    public void showProgressIndicator(boolean show) {

        if (show) {

            mProgressBar.setVisibility(View.VISIBLE);
            mTopRatedMoviesRecyclerView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);


        } else {

            mProgressBar.setVisibility(View.GONE);
            mTopRatedMoviesRecyclerView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);

        }

    }


    private void OnRefresh(){
        mPresenter.subscribe();
        mHomeSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setEmptyView() {

        mEmptyView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mTopRatedMoviesRecyclerView.setVisibility(View.GONE);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mPresenter == null) {
            mPresenter = new HomePresenter(this);
        }

        mPresenter.subscribe();

    }


    private HomeFragmentDirections.ActionHomeFragmentToMovieDetailsFragment setMovie(Movie movie) {

        HomeFragmentDirections.ActionHomeFragmentToMovieDetailsFragment mActionGettingStartedFragmentToSignUpFragment
                =HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(movie);

        mActionGettingStartedFragmentToSignUpFragment.setMovie(movie);

        return mActionGettingStartedFragmentToSignUpFragment;
    }

    @Override
    public void onMovieSelected(Movie movie) {

        NavHostFragment.findNavController(this).navigate(setMovie(movie)); // Navigate TO Movie Details Fragment

    }
}

