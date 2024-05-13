package com.example.technology_app.retrofit;


import com.example.technology_app.models.NotiResponse;
import com.example.technology_app.models.NotiSendData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiPushNotification {
    @Headers({
            //"Accept: application/json",
            "Authorization: key=AAAAjzELO2o:APA91bHaEDJs2-G7b0o1bdMzZ2s73ckVfjl3PmukNH0I4aLxWTUPumyXLZtASxoXqXcfKO7jyjhMoI_GDAybfdpUeIwKVSXFNckAAiC8KGmrj32YzIL8cuZeVp-SPjUj2aZYhHbhcW7R"
    })
    @POST("fcm/send")
    Observable<NotiResponse> sendNotification(@Body NotiSendData data);
}
