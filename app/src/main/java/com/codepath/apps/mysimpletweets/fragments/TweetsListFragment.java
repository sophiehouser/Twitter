package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sophiehouser on 6/27/16.
 */


public class TweetsListFragment extends Fragment {

    private TweetsArrayAdapter aTweets;
    private ArrayList<Tweet> tweets;
    private ListView lvTweets;
    SwipeRefreshLayout swipeContainer;
    ProgressBar progressBarFooter;




    //inflation logic
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                populateTimeline();
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        View footer = inflater.inflate(R.layout.footer_progress, null);
        // Find the progressbar within footer
        progressBarFooter = (ProgressBar) footer.findViewById(R.id.pbFooterLoading);
        // Add footer to ListView before setting adapter
        lvTweets.addFooterView(footer);
        lvTweets.setAdapter(aTweets);

        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        return v;
    }

    public void showProgressBar() {
        if (progressBarFooter != null) {
            progressBarFooter.setVisibility(View.VISIBLE);
        }
    }

    // Hide progress
    public void hideProgressBar() {
        if (progressBarFooter != null) {
            progressBarFooter.setVisibility(View.GONE);
        }
    }



    //creation lifecycle event
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
        //setupListWithFooter();
    }

    /*
    public void setupListWithFooter() {
        // Inflate the footer
        View footer = getLayoutInflater().inflate(R.layout.footer_progress, null);
        // Find the progressbar within footer
        progressBarFooter = (ProgressBar) footer.findViewById(R.id.pbFooterLoading);
        // Add footer to ListView before setting adapter
        lvTweets.addFooterView(footer);
        // Set the adapter AFTER adding footer
        //lv.setAdapter(myAdapter);
    }
    */

    public void appendTweet(Tweet tweet){
        tweets.add(0, tweet);
        aTweets.notifyDataSetChanged();
        lvTweets.setSelection(0);
    }

    public void addAll(List<Tweet> tweets){
        aTweets.addAll(tweets);
    }

    public void populateTimeline(){

    }
}
