package com.alazz.imovie.home;

import android.os.Bundle;
import android.view.WindowManager;

import com.alazz.imovie.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();


    }

    private void initView(){

        setSupportActionBar(mToolbar);

        mNavController = Navigation.findNavController(this, R.id.fragment);

        NavigationUI.setupActionBarWithNavController(this,mNavController);

    }

    @Override
    public boolean onSupportNavigateUp() {

        mNavController.navigate(R.id.action_movieDetailsFragment_to_homeFragment); /// Back To Home Fragment

        return false;
    }





}
