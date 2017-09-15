package com.example.android.moviedb3.movieDB.dateToString;

import java.util.Calendar;

/**
 * Created by nugroho on 26/07/17.
 */

public class DateToNormalDateStringSetter implements  DateToStringSetter
{
    @Override
    public String getDateString(Calendar calendar)
    {
        if(String.valueOf(calendar.get(Calendar.YEAR)).equals("1000"))
        {
            return "-";
        }

        else
        {
            return MonthNameIndexer.GetMonthName(calendar.get(Calendar.MONTH)) + " " +
                    calendar.get(Calendar.YEAR);
        }
    }
}
