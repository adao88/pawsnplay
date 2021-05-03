package com.example.pawsplay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> implements Filterable {

    private ArrayList<Post> mPostList = new ArrayList<>();
    private ArrayList<Post> mPostListFull = new ArrayList<>();
    private OnPostListener mOnPostListener;

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        PostViewHolder  pvh = new PostViewHolder(v, mOnPostListener);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        Post currentPost = mPostList.get(position);
        int contentSubStrEnd = 0;
        int contentLen = currentPost.getContent().length();
        String contentStr = currentPost.getContent();
        if(contentLen > 30){
            contentStr = contentStr.substring(0, 27) + "...";
        }

        holder.mContentTextView.setText(contentStr);
        holder.mAuthorTextView.setText(currentPost.getUserName());
        holder.mDogAgeTextView.setText("Age: " + currentPost.getDogAge());
        holder.mDogSizeTextView.setText("Size: " + currentPost.getDogSize());
        holder.mZipcodeTextView.setText(currentPost.getZipcode());
        holder.mDogActivityLevelTextView.setText("Act. Level: " + currentPost.getDogActivityLevel());
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    public PostAdapter(ArrayList<Post> postList, OnPostListener onPostListener){
        this.mOnPostListener = onPostListener;
        this.mPostList = postList;
        this.mPostListFull = new ArrayList<>(postList);
    }

    @Override
    public Filter getFilter() {
        return postFilter;
    }

    private Filter postFilter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Post> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(mPostListFull);
            } else {
                String filterPattern = constraint.toString();
                if(filterPattern.equals("Reset")){
                    filteredList.addAll(mPostListFull);
                } else if(filterPattern.equals("Small") || filterPattern.equals("Med") || filterPattern.equals("Large")){
                    for(Post post: mPostListFull){
                        if(post.getDogSize().equals(filterPattern)){
                            filteredList.add(post);
                        }
                    }
                } else if(filterPattern.equals("Young") || filterPattern.equals("Adult") || filterPattern.equals("Senior")){
                    for(Post post: mPostListFull){
                        int dogAge = Integer.parseInt(post.getDogAge());

                        if(filterPattern.equals("Young")){
                            if(dogAge < 3){
                                filteredList.add(post);
                            }
                        } else if(filterPattern.equals("Adult")){
                            if(dogAge >= 3 && dogAge < 8){
                                filteredList.add(post);
                            }
                        } else if(filterPattern.equals("Senior")){
                            if(dogAge >= 8){
                                filteredList.add(post);
                            }
                        }
                    }
                } else if(filterPattern.equals("Low") || filterPattern.equals("Medium") || filterPattern.equals("High")){
                    for(Post post: mPostListFull){
                        if(post.getDogActivityLevel().equals(filterPattern)){
                            filteredList.add(post);
                        }
                    }
                } else {
                    filteredList.addAll(mPostListFull);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mPostList.clear();
            mPostList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mContentTextView;
        public TextView mAuthorTextView;
        public TextView mZipcodeTextView;
        public TextView mDogSizeTextView;
        public TextView mDogAgeTextView;
        public TextView mDogActivityLevelTextView;

        OnPostListener onPostListener;

        public PostViewHolder(@NonNull View itemView, OnPostListener onPostListener) {
            super(itemView);
            mContentTextView = itemView.findViewById(R.id.postContent);
            mAuthorTextView = itemView.findViewById(R.id.postAuthor);
            mZipcodeTextView = itemView.findViewById(R.id.postZipcode);
            mDogSizeTextView = itemView.findViewById(R.id.postDogSize);
            mDogAgeTextView = itemView.findViewById(R.id.postDogAge);
            mDogActivityLevelTextView = itemView.findViewById(R.id.postDogActivityLevel);

            this.onPostListener = onPostListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onPostListener.onPostClick(getAdapterPosition());
        }
    }

    public interface OnPostListener{
        void onPostClick(int position);
    }
}
