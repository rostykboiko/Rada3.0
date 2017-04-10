package com.springcamp.rostykboiko.rada3.shared.utlils.firebaseMessaging;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.answer.view.SurveyDialogActivity;

import java.util.List;

public class FCMMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            String messageID = remoteMessage.getData().get("surveyId");
            String title = remoteMessage.getData().get("surveyTitle");

            if (Helper.isAppRunning(getApplicationContext(), "com.springcamp.rostykboiko.rada3")) {
                System.out.println("AppChecker: app is running");
                Intent intent = new Intent(this, SurveyDialogActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("surveyID", messageID);
                startActivity(intent);
            } else {
                sendNotification(title , messageID);
                System.out.println("AppChecker: app isn't running");
            }

            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
    }

    public void sendNotification(String messageTitle, String messageBody) {
        Intent intent = new Intent(this, SurveyDialogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        System.out.println("Message body, sendNoti " + messageBody);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */,
                intent.putExtra("surveyID", messageBody),
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private static class Helper {

        private static boolean isAppRunning(final Context context, final String packageName) {
            final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
            if (procInfos != null)
            {
                for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                    if (processInfo.processName.equals(packageName)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}