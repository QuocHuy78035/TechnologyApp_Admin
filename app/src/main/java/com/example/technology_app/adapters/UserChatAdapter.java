package com.example.technology_app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technology_app.R;
import com.example.technology_app.activities.chat.ChatActivity;
import com.example.technology_app.interfaces.IItemClickListener;
import com.example.technology_app.models.UserModel;

import java.util.List;

public class UserChatAdapter extends RecyclerView.Adapter<UserChatAdapter.MyViewHolder> {
    Context context;
    List<UserModel.User> userList;

    public UserChatAdapter(Context context, List<UserModel.User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_chat, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserChatAdapter.MyViewHolder holder, int position) {
        UserModel.User user = userList.get(position);
        holder.txtId.setText(userList.get(position).get_id() );
        holder.txtEmail.setText(userList.get(position).getEmail());
        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClicked) {
                if(!isLongClicked){
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("id", user.get_id());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtId, txtEmail;
        IItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.id_chat_user);
            txtEmail = itemView.findViewById(R.id.email_chat_user);
            itemView.setOnClickListener(this);
        }
        public TextView getTxtId() {
            return txtId;
        }

        public void setTxtId(TextView txtId) {
            this.txtId = txtId;
        }

        public TextView getTxtEmail() {
            return txtEmail;
        }

        public void setTxtEmail(TextView txtEmail) {
            this.txtEmail = txtEmail;
        }

        public IItemClickListener getItemClickListener() {
            return itemClickListener;
        }

        public void setItemClickListener(IItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }
}
