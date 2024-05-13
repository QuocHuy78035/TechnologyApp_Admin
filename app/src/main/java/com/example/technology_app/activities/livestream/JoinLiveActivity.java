package com.example.technology_app.activities.livestream;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.technology_app.R;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinLiveActivity extends AppCompatActivity {
    private String sampleToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcGlrZXkiOiI5ZTgxMmExNS0xY2UyLTQ3NjgtOTI5Ni01MWQwNjYwOTQ3MGYiLCJwZXJtaXNzaW9ucyI6WyJhbGxvd19qb2luIl0sImlhdCI6MTcxNTYxNTIzNiwiZXhwIjoxNzE4MjA3MjM2fQ.Bj_soEZUhAKpzXAotmEcGmigpsB66kG2nWfsSldQYVs";
    private static final int PERMISSION_REQ_ID = 22;

    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };

    private void checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_join_live);
        Button btnCreate = findViewById(R.id.btnCreateMeeting);
        Button btnJoinHost = findViewById(R.id.btnJoinHostMeeting);
        Button btnJoinViewer = findViewById(R.id.btnJoinViewerMeeting);

        checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID);
        checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID);

        EditText etMeetingId = findViewById(R.id.etMeetingId);


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createMeeting(sampleToken);
            }
        });

        // Join as Host
        btnJoinHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinLiveActivity.this, MeetingActivity.class);
                intent.putExtra("token", sampleToken);
                intent.putExtra("meetingId", etMeetingId.getText().toString().trim());
                intent.putExtra("mode", "CONFERENCE");
                startActivity(intent);
            }
        });

        // Join as Viewer
        btnJoinViewer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinLiveActivity.this, MeetingActivity.class);
                intent.putExtra("token", sampleToken);
                intent.putExtra("meetingId", etMeetingId.getText().toString().trim());
                intent.putExtra("mode", "VIEWER");
                startActivity(intent);
            }
        });
    }

    private void createMeeting(String token) {
        AndroidNetworking.post("https://api.videosdk.live/v2/rooms")
                .addHeaders("Authorization", token) //we will pass the token in the Headers
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            // response will contain `roomId`

                            final String meetingId = response.getString("roomId");

                            // starting the MeetingActivity with received roomId and our sampleToken
                            Intent intent = new Intent(JoinLiveActivity.this, MeetingActivity.class);
                            intent.putExtra("token", sampleToken);
                            intent.putExtra("meetingId", meetingId);
                            intent.putExtra("mode", "CONFERENCE");
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                        Toast.makeText(JoinLiveActivity.this, anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }

            });
        }
}