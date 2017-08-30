package com.example.android.moviedb3.movieDB.dateToString;

/**
 * Created by nugroho on 03/07/17.
 */

class AM_PMIndexer
{
    public static String GetAM_PM(int index)
    {
        switch (index)
        {
            case 0 : return "AM";
            case 1 : return "PM";
            default: return "";
        }
    }
}
