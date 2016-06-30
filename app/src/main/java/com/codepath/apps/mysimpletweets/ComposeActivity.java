package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sophiehouser on 6/29/16.
 */
public class ComposeActivity extends AppCompatActivity{
    private TwitterClient client;
    Button btnSend;
    private EditText etTweet;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        etTweet = (EditText) findViewById(R.id.etTweet);
        client = TwitterApplication.getRestClient();
        btnSend = (Button) findViewById(R.id.btnSend);
        final TextView etCharacterLabel = (TextView) findViewById(R.id.tvCharacterLabel);
        etTweet.addTextChangedListener(new TextWatcher() {
            int characterRemainder;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tweet = etTweet.getText().toString();
                characterRemainder = 140 - tweet.length();
                etCharacterLabel.setText("Characters remaining: " + characterRemainder);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                    final String status = etTweet.getText().toString();
                client.createNewEndpoint(status, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Tweet newTweet = Tweet.fromJSON(response);
                        Intent i = new Intent(ComposeActivity.this, TimelineActivity.class);
                        i.putExtra("tweet", newTweet);
                        setResult(RESULT_OK, i);
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }

                });
            }
        });

    }


}
