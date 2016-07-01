package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.mysimpletweets.fragments.SearchTweetsFragment;

/**
 * Created by sophiehouser on 6/30/16.
 */
public class SearchActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String query = getIntent().getStringExtra("query");
        if (savedInstanceState == null) {
            SearchTweetsFragment fragmentDemo = new SearchTweetsFragment();
            // Let's first dynamically add a fragment into a frame container
            FragmentTransaction ft =  getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flFragmentHolder, fragmentDemo, null);
            ft.commit();
            // Now later we can lookup the fragment by tag
            fragmentDemo.populateTimeline(query);

        }
    }
}
