package com.example.android.moviedb3.movieDB.stringToDate;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by nugroho on 26/07/17.
 */

public class NumericDateStringToDateSetter implements StringToDateSetter
{
    @Override
    public Calendar getDateTime(String dateString) {

        Calendar date = new GregorianCalendar();
        String[] releaseDateSubString = dateString.split("-");

        if(releaseDateSubString[1].contains("0"))
        {
            releaseDateSubString[1] = releaseDateSubString[1].replace("0", "");
        }

        if(releaseDateSubString[2].contains("0"))
        {
            releaseDateSubString[2] = releaseDateSubString[2].replace("0", "");
        }

        date.set(Integer.parseInt(releaseDateSubString[0]), Integer.parseInt(releaseDateSubString[1]) - 1, Integer.parseInt(releaseDateSubString[2]));

        return date;
    }
}
