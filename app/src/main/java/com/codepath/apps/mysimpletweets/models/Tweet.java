package com.codepath.apps.mysimpletweets.models;

import com.codepath.apps.mysimpletweets.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sophiehouser on 6/27/16.
 */
public class Tweet implements Serializable {

    private String body;
    private long uid;
    private String createdAt;
    private User user;
    private String date;

    public String getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        //return createdAt;
        String formattedTime = TimeFormatter.getTimeDifference(createdAt);
        System.out.println("created at" + createdAt);
        System.out.println("formatted time" + formattedTime);
        return formattedTime;
    }

    public static Tweet fromJSON(JSONObject jsonObject){
        Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            //tweet.date = jsonObject.getString()
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    public static ArrayList<Tweet>  fromJSONArray(JSONArray jsonArray){
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        for (int i =0; i< jsonArray.length(); i++){
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if (tweet != null){
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweets;
    }
}
