package com.example.android.moviedb3.jsonParsing;

import com.example.android.moviedb3.movieDB.PeopleData;
import com.example.android.moviedb3.movieDB.stringToDate.NumericDateStringToDateSetter;
import com.example.android.moviedb3.movieDB.stringToDate.StringToDateSetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by nugroho on 06/09/17.
 */

public class PeopleDetailJSONParser implements JSONParser<PeopleData>
{
    PeopleData peopleData;

    public PeopleDetailJSONParser(PeopleData peopleData) {
        this.peopleData = peopleData;
    }

    public PeopleDetailJSONParser() {
    }

    @Override
    public PeopleData Parse(JSONObject jsonObject) {

        if(jsonObject == null)
        {
            return null;
        }

        if(peopleData != null)
        {
            try
            {
                String birthdayString = jsonObject.getString("birthday");
                Calendar birthday;
                if(birthdayString == null || birthdayString.isEmpty() || birthdayString.equals("null"))
                {
                    birthday = new GregorianCalendar(2000, 1, 1);
                }
                else
                {
                    StringToDateSetter stringToDateSetter = new NumericDateStringToDateSetter();
                    birthday = stringToDateSetter.getDateTime(birthdayString);
                }
                peopleData.setBirthtdayDate(birthday);

                String name = jsonObject.getString("name");
                if(name == null || name.equals("null"))
                {
                    name = "";
                }
                peopleData.setName(name);

                String also_known_as = "";
                JSONArray also_known_as_array = jsonObject.getJSONArray("also_known_as");
                if(also_known_as_array.length() > 0)
                {
                    for(int a = 0; a < 1; a++)
                    {
                        also_known_as = also_known_as_array.getString(a);
                    }
                }
                peopleData.setOthersName(also_known_as);

                String biography = jsonObject.getString("biography");
                if(biography == null || biography.equals("null"))
                {
                    biography = "";
                }
                peopleData.setBiography(biography);

                String place_of_birth = jsonObject.getString("place_of_birth");
                if(place_of_birth == null || place_of_birth.equals("place_of_birth"))
                {
                    place_of_birth = "";
                }
                peopleData.setPlaceOfBirth(place_of_birth);

                String profile_path = jsonObject.getString("profile_path");
                if(profile_path == null || profile_path.equals("null"))
                {
                    profile_path = "";
                }
                peopleData.setProfileImage(profile_path);

                return peopleData;

            } catch (JSONException e)
            {
                e.printStackTrace();
                return null;
            }
        }

        else {
            try {

                String birthdayString = jsonObject.getString("birthday");
                Calendar birthday;
                if(birthdayString == null || birthdayString.isEmpty() || birthdayString.equals("null"))
                {
                    birthday = new GregorianCalendar(2000, 1, 1);
                }
                else
                {
                    StringToDateSetter stringToDateSetter = new NumericDateStringToDateSetter();
                    birthday = stringToDateSetter.getDateTime(birthdayString);
                }

                String id = jsonObject.getString("id");

                String name = jsonObject.getString("name");
                if (name == null || name.equals("null")) {
                    name = "";
                }

                String also_known_as = jsonObject.getString("also_known_as");
                if (also_known_as == null || also_known_as.equals("null")) {
                    also_known_as = "";
                }

                String biography = jsonObject.getString("biography");
                if (biography == null || biography.equals("null")) {
                    biography = "";
                }

                String place_of_birth = jsonObject.getString("place_of_birth");
                if (place_of_birth == null || place_of_birth.equals("place_of_birth")) {
                    place_of_birth = "";
                }

                String profile_path = jsonObject.getString("profile_path");
                if (profile_path == null || profile_path.equals("null")) {
                    profile_path = "";
                }

                return new PeopleData(id, name, place_of_birth, birthday, biography, profile_path, also_known_as);

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
