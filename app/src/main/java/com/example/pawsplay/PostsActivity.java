package com.example.pawsplay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostsActivity extends AppCompatActivity implements PostAdapter.OnPostListener {

    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Post> postList = new ArrayList<>();
    private ArrayList<Post> postListFull = new ArrayList<>();

    private DatabaseReference reference;

    private String TAG = "Posts Activity";

    private Button goToAddPost;

    private TextView filterSizeButton, filterAgeButton, filterActivityLevelButton, filterResetButton;

    private Spinner dogSizeSpinner, dogAgeSpinner, dogActivityLevelSpinner;

    private String filterSizeStr, filterAgeStr, filterActivityLevelStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        dogSizeSpinner = (Spinner) findViewById(R.id.dog_size_filter_spinner);
        ArrayAdapter<CharSequence> dogSizeAdapter = ArrayAdapter.createFromResource(this, R.array.dogSize_array, android.R.layout.simple_spinner_dropdown_item);
        dogSizeSpinner.setAdapter(dogSizeAdapter);

        dogAgeSpinner = (Spinner) findViewById(R.id.dog_age_filter_spinner);
        ArrayAdapter<CharSequence> dogAgeAdapter = ArrayAdapter.createFromResource(this, R.array.dogAge_array, android.R.layout.simple_spinner_dropdown_item);
        dogAgeSpinner.setAdapter(dogAgeAdapter);

        dogActivityLevelSpinner = (Spinner) findViewById(R.id.dog_activity_level_filter_spinner);
        ArrayAdapter<CharSequence> dogActivityLevelAdapter = ArrayAdapter.createFromResource(this, R.array.dogActivityLevel_array, android.R.layout.simple_spinner_dropdown_item);
        dogActivityLevelSpinner.setAdapter(dogActivityLevelAdapter);


        filterSizeButton = (TextView) findViewById(R.id.filterSizeButton);
        filterSizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterSizeStr = dogSizeSpinner.getSelectedItem().toString();
                mAdapter.getFilter().filter(filterSizeStr);
            }
        });

        filterAgeButton = (TextView) findViewById(R.id.filterAgeButton);
        filterAgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterAgeStr = dogAgeSpinner.getSelectedItem().toString();
                mAdapter.getFilter().filter(filterAgeStr);
            }
        });

        filterActivityLevelButton = (TextView) findViewById(R.id.filterActivityLevelButton);
        filterActivityLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterActivityLevelStr = dogActivityLevelSpinner.getSelectedItem().toString();
                mAdapter.getFilter().filter(filterActivityLevelStr);
            }
        });


        filterResetButton = (TextView) findViewById(R.id.filterResetButton);
        filterResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.getFilter().filter("Reset");
            }
        });

        goToAddPost = (Button)findViewById(R.id.createPostButton);
        goToAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostsActivity.this, CreatePostActivity.class));
            }
        });

        mRecyclerView = findViewById(R.id.postsRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();

                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    Post post = postSnapshot.getValue(Post.class);
                    postList.add(post);
                    Log.d(TAG, "Got Post: " + post.getContent());

                }
                if (!postList.isEmpty()){
                    Log.d(TAG, "Non Empty List");
                    mAdapter = new PostAdapter(postList, PostsActivity.this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Unable to get Posts");
                Log.d(TAG, "Empty List");
                mRecyclerView.setLayoutManager(mLayoutManager);
            }
        });
        /*
        if (!this.postList.isEmpty()){
            Log.d(TAG, "Non Empty List");
            mAdapter = new PostAdapter(this.postList);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }
        */

        //Log.d(TAG, "Empty List");
        //mRecyclerView.setLayoutManager(mLayoutManager);
        //mRecyclerView.setAdapter();
    }


    @Override
    public void onPostClick(int position) {
        Post post = postList.get(position);
        Intent intent = new Intent(this, PostViewActivity.class);
        intent.putExtra("content", post.getContent());
        intent.putExtra("authorUserName", post.getUserName());
        intent.putExtra("authorId", post.getUserId());
        intent.putExtra("zipcode", post.getZipcode());
        intent.putExtra("dogAge", post.getDogAge());
        intent.putExtra("dogSize", post.getDogSize());
        intent.putExtra("dogActivityLevel", post.getDogActivityLevel());
        intent.putExtra("post", post);
        startActivity(intent);
    }
}