package com.example.android.moviedb3;

import com.example.android.moviedb3.movieDB.dateToString.DateToNormalDateStringSetter;
import com.example.android.moviedb3.movieDB.dateToString.DateToStringSetter;
import com.example.android.moviedb3.movieDB.stringToDate.NumericDateStringToDateSetter;
import com.example.android.moviedb3.movieDB.stringToDate.StringToDateSetter;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception
    {
        String releaseDateString = "2017-09-05";

        StringToDateSetter stringToDateSetter = new NumericDateStringToDateSetter();
        Calendar releaseDate = stringToDateSetter.getDateTime(releaseDateString);

        DateToStringSetter dateToStringSetter = new DateToNormalDateStringSetter();


        assertEquals(4, 2 + 2);
    }
}