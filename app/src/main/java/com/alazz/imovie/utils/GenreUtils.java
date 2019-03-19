package com.alazz.imovie.utils;

import android.content.Context;

import com.alazz.imovie.R;

public class GenreUtils {


    public static String getGenre(Context context, int id){

        String mGenre = "-";

        switch (id){

            case 28:
                mGenre= context.getString(R.string.genre_action);
                break;

            case 16:
                mGenre= context.getString(R.string.genre_animated);
                break;

            case 99:
                mGenre= context.getString(R.string.genre_documentary);
                break;

            case 18:
                mGenre= context.getString(R.string.genre_drama);
                break;

            case 10751:
                mGenre= context.getString(R.string.genre_family);
                break;

            case 14:
                mGenre= context.getString(R.string.genre_fantasy);
                break;

            case 36:
                mGenre= context.getString(R.string.genre_history);
                break;

            case 35:
                mGenre= context.getString(R.string.genre_comedy);
                break;

            case 10402:
                mGenre= context.getString(R.string.genre_music);
                break;

            case 80:
                mGenre= context.getString(R.string.genre_crime);
                break;

            case 10749:
                mGenre= context.getString(R.string.genre_romance);
                break;

            case 878:
                mGenre= context.getString(R.string.genre_mystery);
                break;

            case 27:
                mGenre= context.getString(R.string.genre_horror);
                break;

            case 10770:
                mGenre= context.getString(R.string.genre_sci_fi);
                break;

            case 53:
                mGenre= context.getString(R.string.genre_thriller);
                break;

            case 37:
                mGenre= context.getString(R.string.genre_western);
                break;

            case 12:
                mGenre= context.getString(R.string.genre_adventure);
                break;

            case 10752:
                mGenre= context.getString(R.string.genre_war);
                break;

        }

       return mGenre;
    }
}
