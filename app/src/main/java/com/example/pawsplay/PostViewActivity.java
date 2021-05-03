package com.example.pawsplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PostViewActivity extends AppCompatActivity {

    private TextView contentTextView, dogAgeTextView, dogSizeTextView, dogActivityLevelTextView;
    private String authorUserId, authorUserName, dogAgeStr, dogSizeStr, dogActivityLevelStr;
    private String TAG = "PVA";
    private Button sendMessageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);

        Post post = getIntent().getParcelableExtra("post");
        authorUserId = getIntent().getStringExtra("authorId");
        authorUserName = getIntent().getStringExtra("authorUserName");
        dogAgeStr = getIntent().getStringExtra("dogAge");
        dogSizeStr = getIntent().getStringExtra("dogSize");
        dogActivityLevelStr = getIntent().getStringExtra("dogActivityLevel");

        contentTextView = (TextView)findViewById(R.id.postViewContent);
        dogAgeTextView = (TextView)findViewById(R.id.dogAgePost);
        dogSizeTextView = (TextView)findViewById(R.id.dogSizePost);
        dogActivityLevelTextView = (TextView)findViewById(R.id.dogActivityLevelPost);

        contentTextView.setText(post.getContent());
        dogAgeTextView.setText(dogAgeStr);
        dogSizeTextView.setText(dogSizeStr);
        dogActivityLevelTextView.setText(dogActivityLevelStr);

        sendMessageButton = (Button)findViewById(R.id.sendMessageButton);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostViewActivity.this, CreateMessageActivity.class);
                intent.putExtra("recipientUserId", authorUserId);
                intent.putExtra("recipientUserName", authorUserName);
                startActivity(intent);
            }
        });

        Log.d(TAG, "UserID: " + authorUserId);

    }
}