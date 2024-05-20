package com.example.technology_app.activities.livestream;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.technology_app.R;
import com.example.technology_app.fragment.SpeakerFragment;
import com.example.technology_app.fragment.ViewerFragment;
import com.example.technology_app.retrofit.Api;
import com.example.technology_app.retrofit.RetrofitClient;
import com.example.technology_app.utils.GlobalVariable;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import live.videosdk.rtc.android.Meeting;
import live.videosdk.rtc.android.VideoSDK;
import live.videosdk.rtc.android.listeners.MeetingEventListener;

public class MeetingActivity extends AppCompatActivity {
    private Meeting meeting;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Api api;
    String userId, accessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meeting);
        api = RetrofitClient.getInstance(GlobalVariable.BASE_URL).create(Api.class);
        Paper.init(this);
        userId = Paper.book().read("userId");
        accessToken = Paper.book().read("accessToken");
        String meetingId = getIntent().getStringExtra("meetingId");
        String token = getIntent().getStringExtra("token");
        String mode = getIntent().getStringExtra("mode");
        Toast.makeText(this, meetingId, Toast.LENGTH_SHORT).show();
        String localParticipantName = "Technology App";
        assert mode != null;
        boolean streamEnable = mode.equals("CONFERENCE");

        Log.d("Meeting Id", meetingId);
        postDataToMeeting(meetingId, token);


        // initialize VideoSDK
        VideoSDK.initialize(getApplicationContext());

        // Configuration VideoSDK with Token
        VideoSDK.config(token);

        // Initialize VideoSDK Meeting
        meeting = VideoSDK.initMeeting(
                MeetingActivity.this, meetingId, localParticipantName,
                streamEnable, streamEnable, null, mode, false, null);

        // join Meeting
        assert meeting != null;
        meeting.join();

        // if mode is CONFERENCE than replace mainLayout with SpeakerFragment otherwise with ViewerFragment
        meeting.addEventListener(new MeetingEventListener() {
            @Override
            public void onMeetingJoined() {
                if (meeting != null) {
                    if (mode.equals("CONFERENCE")) {
                        //pin the local partcipant
                        meeting.getLocalParticipant().pin("SHARE_AND_CAM");
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.mainLayout, new SpeakerFragment(), "MainFragment")
                                .commit();
                    } else if (mode.equals("VIEWER")) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.mainLayout, new ViewerFragment(), "viewerFragment")
                                .commit();
                    }
                }
            }
        });
    }

        private void postDataToMeeting(String meetingId, String token) {
        compositeDisposable.add(api.createMeeting(userId, accessToken, meetingId, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        createMeeting -> {
                            if (createMeeting.getStatus() == 201) {
                                Toast.makeText(getApplicationContext(), "Create Meeting Success", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Log.d( "Log","123"+ throwable.getMessage());
                            Toast.makeText(getApplicationContext(), "Loi!!!" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    public Meeting getMeeting() {
        return meeting;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}