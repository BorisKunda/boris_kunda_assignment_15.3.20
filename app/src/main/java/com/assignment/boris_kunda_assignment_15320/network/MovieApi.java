package com.assignment.boris_kunda_assignment_15320.network;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

public class MovieApi {


    private RequestQueue mRequestQueue;
    private final static String GET_MOVIES_URL = "https://api.androidhive.info/json/movies.json";
    private static MovieApi mMovieApi = null;

    private MovieApi (Application application) {
        mRequestQueue = Volley.newRequestQueue(application);
    }

    public static MovieApi getMovieApi (Application iApplication) {

        if (mMovieApi == null) {
            mMovieApi = new MovieApi(iApplication);
        }

        return mMovieApi;
    }

    public void requestMovieList (OnServerResponseListener onServerResponseListener) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, GET_MOVIES_URL,
                null,
                response -> onServerResponseListener.OnServerResponse(true, response),

                error -> onServerResponseListener.OnServerResponse(false, null)

        );

        mRequestQueue.add(jsonArrayRequest);

    }


}
