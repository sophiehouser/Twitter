package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sophiehouser on 6/27/16.
 */

//Taking the tweet objects and turning them into views that will be displayed in the list
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    Tweet tweet;
    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get tweet
        tweet = getItem(position);
        //find or inflate the template
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        //find the subviews to fill with data from the template
        final ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        //ivProfileImage.setTag(tweet.getUser());
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("user", tweet.getUser());
                //i.putSerializable("user", ivProfileImage.getTag());
                getContext().startActivity(i);
            }
        });
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        //populate data into the subviews
        tvUserName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvTime.setText(tweet.getCreatedAt());
        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
        //return the view to be inserted into list
        return convertView;
    }

}
