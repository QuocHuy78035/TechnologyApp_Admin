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
            "Authorization: key=AAAAjzELO2o:APA91bHYQWyku45Px9fUC0WDpn5A9md1PN6nlNcsLLpmFo1F_IDPLUCSKVzJ8okYK2oTJC0_XdsVl-H1JdC7e-S15k-sipGjPRS_2kwOicnJnmkt6b5Mc9aTCMPXVf7OQA3GirE-xj6g"
    })
    @POST("fcm/send")
    Observable<NotiResponse> sendNotification(@Body NotiSendData data);
}
