package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwitch = null;

        try
        {
            JSONObject sandwichData = new JSONObject(json);
            JSONObject name = sandwichData.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<String>();
            for (int i = 0; i < alsoKnownAsArray.length(); i++)
            {
                alsoKnownAs.add(alsoKnownAsArray.getString(i));
            }
            String placeOfOrigin = sandwichData.getString("placeOfOrigin");
            String description = sandwichData.getString("description");
            String image = sandwichData.getString("image");
            JSONArray ingredientsArray = sandwichData.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<String>();
            for (int i = 0; i < ingredientsArray.length(); i++)
            {
                ingredients.add(ingredientsArray.getString(i));
            }

            sandwitch = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        }
        catch (Exception e) // try catch more then just JSONException, like null ref exception
        {
            e.printStackTrace();
        }

        return sandwitch;
    }
}
