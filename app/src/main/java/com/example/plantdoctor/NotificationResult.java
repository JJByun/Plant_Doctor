package com.example.plantdoctor;

import android.app.NotificationManager;
import android.view.View;

import androidx.core.app.NotificationCompat;

public class NotificationResult {
    private static final int NOTIFICATION_ID = 11;
    static final String channel_id = "channel";
    //String chennel_Name = "channel_name"
    private static final int importance = NotificationManager.IMPORTANCE_LOW;

    public static void sendNotification(View view){
        //빌더로 알림 설정
        //NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channel_id);
    }
}
