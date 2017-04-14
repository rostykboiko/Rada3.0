package com.springcamp.rostykboiko.rada3.shared.utlils.firebaseMessaging;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.answer.view.AnswerDialogActivity;
import com.springcamp.rostykboiko.rada3.receiver.QuestionReceiver;

import java.util.List;

public class FCMMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @NonNull
    private QuestionReceiver questionReceiver;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            String surveyTitle = remoteMessage.getData().get("surveyTitle");
            String surveyData = remoteMessage.getData().get("survey");

            System.out.println("extraDATA " + remoteMessage.getData().get("survey"));

            if (Helper.isAppRunning(this, "com.springcamp.rostykboiko.rada3")) {
                Intent intent = new Intent(QuestionReceiver.QUESTION_RECEIVED_FILTER);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("surveyID", surveyData);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            } else {
                sendNotification(surveyData, surveyTitle);
                System.out.println("AppChecker: app isn't running");
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            }
        }
    }

    public void sendNotification(String surveyData, String messageTitle) {
        Intent intent = new Intent(this, AnswerDialogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */,
                intent.putExtra("survey", surveyData),
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageTitle)
                .setContentText("")
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
            if (procInfos != null) {
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