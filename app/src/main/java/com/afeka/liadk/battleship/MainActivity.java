package com.afeka.liadk.battleship;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MainStartFragment.onButtonTouchedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnimationDrawable animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.animation_list, null);
        findViewById(R.id.mainActivity).setBackground(animationDrawable);
        animationDrawable.start();
        MainStartFragment newFragment = new MainStartFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.selectButtons, newFragment).commit();
    }

    @Override
    public void onButtonTouchedPlay() {
        Fragment newFragment = new ChooseLevelFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.selectButtons, newFragment).addToBackStack(null).commit();
    }

    @Override
    public void onButtonTouchedHighScore() {
        HighscoreFragment newFragment = new HighscoreFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity, newFragment).addToBackStack(null).commit();
    }
}