package com.example.pawsplay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MessageViewActivity extends AppCompatActivity {

    private TextView contentTextView, authorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_view);

        String content = getIntent().getStringExtra("content");
        String author = getIntent().getStringExtra("author");

        contentTextView = (TextView) findViewById(R.id.messageContentTextView);
        authorTextView = (TextView) findViewById(R.id.messageAuthorTextView);

        contentTextView.setText(content);
        authorTextView.setText(author);
    }
}