package com.randomcookie.randomcookie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    String key = "174bc9609498bf7741ad3d018faf6c93";
    private static RequestQueue requestQueue;
    private static final String TAG = "Lab12:Main";
    private String toShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_main);
    }

    public void getRecipe(View view) {
        try{
            String url = "https://www.food2fork.com/api/search?key="+ key + "&q=cookie";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try{
                                toShow = response.toString();
                                gotResult();
                            } catch (Exception e) {
                                Log.w(TAG, e.toString());
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG, error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void gotResult() {
        setContentView(R.layout.activity_show_recipe);
        TextView result = findViewById(R.id.recipe);

        JsonParser parser = new JsonParser();
        JsonObject rootObj = parser.parse(toShow).getAsJsonObject();
        JsonArray formObj = rootObj.getAsJsonArray("recipes");

        Random random = new Random();
        int recipeNum = random.nextInt(30);

        JsonElement res = formObj.get(recipeNum);
        JsonObject final_result = res.getAsJsonObject();
        result.setText(final_result.get("title").getAsString());

        }
}
