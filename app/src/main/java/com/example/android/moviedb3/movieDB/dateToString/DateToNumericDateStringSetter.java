package com.example.android.moviedb3.movieDB.dateToString;

import java.util.Calendar;

/**
 * Created by nugroho on 26/07/17.
 */

public class DateToNumericDateStringSetter implements DateToStringSetter
{
    @Override
    public String getDateString(Calendar calendar) {
        String dateString = calendar.get(Calendar.YEAR) + "-";

        int monthInteger = calendar.get(Calendar.MONTH) + 1;

        if(monthInteger < 10)
        {
            dateString += "0" + monthInteger + "-";
        }

        else
        {
            dateString += monthInteger + "-";
        }

        int dayInteger = calendar.get(Calendar.DAY_OF_MONTH);

        if(dayInteger < 10)
        {
            dateString += "0" + dayInteger + "-";
        }

        else
        {
            dateString += "0" + dayInteger;
        }

        return dateString;
    }
}

