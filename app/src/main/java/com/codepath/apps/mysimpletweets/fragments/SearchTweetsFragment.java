package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sophiehouser on 6/30/16.
 */
public class SearchTweetsFragment extends TweetsListFragment {

    private TwitterClient client;
    private String query;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //query = getIntent()
    }


    public void populateTimeline(String query) {
        client = TwitterApplication.getRestClient();
        //HARD CODED QUERY UNTIL I FIGURE OUT HOW TO PASS IN CORRECT THING
        client.getSearchTweets(query, new JsonHttpResponseHandler() {
            //SUCCESS

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                //super.onSuccess(statusCode, headers, response);
                Log.d("DEBUG", json.toString());
                //JSON HERE
                //DESERIALIZE JSON
                //CREATE MODELS AND ADD THEM TO THE ADAPTER
                //LOAD THE MODEL DATA INTO LIST VIEW

                try {
                    addAll(Tweet.fromJSONArray(json.getJSONArray("statuses")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            //FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }
}
