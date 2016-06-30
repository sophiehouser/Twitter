package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {
    TwitterClient client;
    User user;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        client = TwitterApplication.getRestClient();
        user = (User) getIntent().getSerializableExtra("user");
        if (user == null){
            client.getUserInfo(new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);
                    loadForUser(user, savedInstanceState);
                    //my current user account's info
                }
            });
        } else {
            loadForUser(user, savedInstanceState);
        }

    }

    private void loadForUser(User user, Bundle savedInstanceState) {
        getSupportActionBar().setTitle("@" + user.getScreenName());
        populateProfileHeader(user);
        if(savedInstanceState == null){
            //create usertimeline fragment
            String screenName = user.getScreenName();
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);
            //display user fragment within this activity (dynamically)
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainter, fragmentUserTimeline);
            ft.commit(); //changes the fragments
        }

    }
        //get screename from activity taht launches this


    private void populateProfileHeader(User user){
        TextView tvName = (TextView) findViewById(R.id.tvFullName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagLine);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        tvFollowers.setText(user.getFollowersCount() + "Followers");
        tvFollowing.setText(user.getFriendsCount() + "Following");
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /*
        if (id == R.id.action_settings){
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }
}
