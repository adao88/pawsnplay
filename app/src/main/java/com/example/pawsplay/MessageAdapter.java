package com.example.pawsplay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private ArrayList<Message> mMessageList = new ArrayList<>();
    private OnMessageListener mOnMessageListener;

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        MessageViewHolder  mvh = new MessageViewHolder(v, mOnMessageListener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        Message currentMessage = mMessageList.get(position);
        int contentLen = currentMessage.getContent().length();
        String contentStr = currentMessage.getContent();
        if(contentLen > 30){
            contentStr = contentStr.substring(0, 27) + "...";
        }

        holder.mContentTextView.setText(contentStr);
        holder.mAuthorTextView.setText(currentMessage.getAuthorName());
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public MessageAdapter(ArrayList<Message> messageList, OnMessageListener onMessageListener){
        this.mOnMessageListener = onMessageListener;
        this.mMessageList = messageList;
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mContentTextView;
        public TextView mAuthorTextView;


        OnMessageListener onMessageListener;

        public MessageViewHolder(@NonNull View itemView, OnMessageListener onMessageListener) {
            super(itemView);
            mContentTextView = itemView.findViewById(R.id.messageItemContent);
            mAuthorTextView = itemView.findViewById(R.id.messageItemAuthor);


            this.onMessageListener = onMessageListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onMessageListener.onMessageClick(getAdapterPosition());
        }
    }

    public interface OnMessageListener{
        void onMessageClick(int position);
    }
}
