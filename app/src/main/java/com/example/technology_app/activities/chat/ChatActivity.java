package com.example.technology_app.activities.chat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.technology_app.R;
import com.example.technology_app.adapters.ChatAdapter;
import com.example.technology_app.models.ChatMessage;
import com.example.technology_app.utils.GlobalVariable;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.paperdb.Paper;

public class ChatActivity extends AppCompatActivity {

    EditText edtInputChat;
    ImageView imgChat;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    ChatAdapter chatAdapter;
    List<ChatMessage> list;
    String idReceive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        initView();
        initControl();
        listenMess();
        insertUser();
    }

    private void insertUser() {
        if(Paper.book().read("userId") != null && Paper.book().read("email") != null){
            HashMap<String, Object> user = new HashMap<>();
            user.put("id", Paper.book().read("userId"));
            user.put("userName", Paper.book().read("email"));
            //db.collection("users").document(Objects.requireNonNull(Paper.book().read("userId")));
            db.collection("users").document(Objects.requireNonNull(Paper.book().read("userId"))).set(user);
        }else{
            Log.d("Insert User faild", "no have userId or email");
        }

    }

    private void initControl() {
        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessToFireStore();
            }
        });
    }

    private void sendMessToFireStore() {
        String strMess = edtInputChat.getText().toString().trim();
        if(TextUtils.isEmpty(strMess)){

        }else{
            HashMap<String, Object> message = new HashMap<>();
            if(Paper.book().read("userId") != null){
                message.put(GlobalVariable.SEND_ID, Paper.book().read("userId"));
                //sai
                message.put(GlobalVariable.RECEIVED_ID, idReceive);
                message.put(GlobalVariable.MESS, strMess);
                message.put(GlobalVariable.DATE_TIME, new Date());
                db.collection(GlobalVariable.MESS).add(message);
                edtInputChat.setText("");
            }else{
                Log.d("No have userId Chat Screen", "userId null");
            }
        }
    }

    private void listenMess(){
        db.collection(GlobalVariable.CHAT_PATH)
                .whereEqualTo(GlobalVariable.SEND_ID, Paper.book().read("userId"))
                .whereEqualTo(GlobalVariable.RECEIVED_ID, idReceive)
                .addSnapshotListener(eventListener);

        db.collection(GlobalVariable.CHAT_PATH)
                .whereEqualTo(GlobalVariable.SEND_ID, idReceive)
                .whereEqualTo(GlobalVariable.RECEIVED_ID, Paper.book().read("userId"))
                .addSnapshotListener(eventListener);
    }

    @SuppressLint("NotifyDataSetChanged")
    private final EventListener<QuerySnapshot> eventListener = (value, error) ->{
        if(error != null){
            return;
        }
        if(value != null){
            int count = list.size();
            Log.d("FirestoreData", "Data: " + value.getDocumentChanges());

            for(DocumentChange documentChange : value.getDocumentChanges()){
                Log.d("check", "true");
                if(documentChange.getType() == DocumentChange.Type.ADDED){
                    ChatMessage chatMessage = new ChatMessage();
                    Log.d("FirestoreData1", "Data: " + documentChange.getDocument().getString(GlobalVariable.SEND_ID).toString());

                    chatMessage.sendId = documentChange.getDocument().getString(GlobalVariable.SEND_ID);
                    chatMessage.receivedId = documentChange.getDocument().getString(GlobalVariable.RECEIVED_ID);
                    chatMessage.mess  = documentChange.getDocument().getString(GlobalVariable.MESS);
                    chatMessage.dateObj = documentChange.getDocument().getDate(GlobalVariable.DATE_TIME);
                    chatMessage.dateTime = formatDate(documentChange.getDocument().getDate(GlobalVariable.DATE_TIME));
                    list.add(chatMessage);
                    Log.d("AddedMessage", "Message added: " + chatMessage.mess);
                }
            }
            list.sort(Comparator.comparing(obj -> obj.dateObj));
            if(count == 0){
                chatAdapter.notifyDataSetChanged();
            }else{
                chatAdapter.notifyItemRangeInserted(list.size(), list.size());
                recyclerView.smoothScrollToPosition(list.size() - 1);
                chatAdapter.notifyDataSetChanged();
            }
        }
    };

    private String formatDate(Date date){
        return new SimpleDateFormat("MMM dd, yyy- hh:mm a", Locale.getDefault()).format(date);
    }

    private void initView() {
        idReceive = getIntent().getStringExtra("id");
        list = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        edtInputChat = findViewById(R.id.edtContentChat);
        imgChat = findViewById(R.id.imgChat);
        recyclerView = findViewById(R.id.recycleviewChatScreen);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        chatAdapter = new ChatAdapter(getApplicationContext(), list, Paper.book().read("userId"));
        recyclerView.setAdapter(chatAdapter);
    }
}