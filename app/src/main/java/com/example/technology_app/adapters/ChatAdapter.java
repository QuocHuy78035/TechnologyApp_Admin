package com.example.technology_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technology_app.R;
import com.example.technology_app.models.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<ChatMessage> chatMessageList;
    private  String sendId;
    private static final int TYPE_SEND = 1;

    public ChatAdapter(Context context, List<ChatMessage> chatMessageList, String sendId) {
        this.context = context;
        this.chatMessageList = chatMessageList;
        this.sendId = sendId;
    }

    private static final int TYPE_RECEIVED = 2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_SEND){
            view = LayoutInflater.from(context).inflate(R.layout.item_send_message, parent, false);
            return new SendMessViewHolder(view);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.item_received_message, parent, false);
            return new ReceivedMessViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_SEND){
            ((SendMessViewHolder) holder).txtSendMessage.setText(chatMessageList.get(position).mess);
            ((SendMessViewHolder) holder).txtTimeSendMessage.setText(chatMessageList.get(position).dateTime);
        }else{
            ((ReceivedMessViewHolder) holder).txtReceivedMessage.setText(chatMessageList.get(position).mess);
            ((ReceivedMessViewHolder) holder).txtTimeReceivedMessage.setText(chatMessageList.get(position).dateTime);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(chatMessageList.get(position).sendId.equals(sendId)){
            return TYPE_SEND;
        }else{
            return TYPE_RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }


    static class SendMessViewHolder extends RecyclerView.ViewHolder{
        TextView txtSendMessage, txtTimeSendMessage;
        public SendMessViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSendMessage = itemView.findViewById(R.id.txtMessSend);
            txtTimeSendMessage = itemView.findViewById(R.id.txtTimeMessageSend);
        }
    }

    static class ReceivedMessViewHolder extends RecyclerView.ViewHolder{
        TextView txtReceivedMessage, txtTimeReceivedMessage;
        public ReceivedMessViewHolder(@NonNull View itemView) {
            super(itemView);
            txtReceivedMessage = itemView.findViewById(R.id.txtMessReceived);
            txtTimeReceivedMessage = itemView.findViewById(R.id.txtTimeMessageReceived);
        }
    }
}
