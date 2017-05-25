package com.springcamp.rostykboiko.rada3.shared.utlils.firebaseMessaging;

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
import com.google.gson.Gson;
import com.springcamp.rostykboiko.rada3.R;
import com.springcamp.rostykboiko.rada3.Rada3;
import com.springcamp.rostykboiko.rada3.answer.view.AnswerDialogActivity;
import com.springcamp.rostykboiko.rada3.receiver.QuestionReceiver;
import com.springcamp.rostykboiko.rada3.shared.utlils.FireBaseDB.Survey;
import com.springcamp.rostykboiko.rada3.shared.utlils.Utils;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class FCMMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private static final String SURVEY_KEY = "SURVEY_KEY";

    @NonNull
    private QuestionReceiver questionReceiver;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            String surveyTitle = remoteMessage.getData().get("surveyTitle");
            String surveyJson = remoteMessage.getData().get(SURVEY_KEY);

            if (Rada3.isActivityVisible()) {
                Intent intent = new Intent(QuestionReceiver.QUESTION_RECEIVED_FILTER);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(SURVEY_KEY, surveyJson);

                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            } else {
                sendNotification(surveyJson, surveyTitle);
                Survey survey = new Gson().fromJson(surveyJson, Survey.class);
                notificationTimer(survey);
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            }
        }
    }

    public void sendNotification(String surveyJson, String messageTitle) {
        Intent intent = new Intent(this, AnswerDialogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */,
                intent.putExtra(SURVEY_KEY, surveyJson),
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_material_messege)
                .setContentTitle(messageTitle)
                .setContentText(getString(R.string.push_content))
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    private void notificationTimer(Survey survey){
        Timer mTimer = new Timer();
        CloseNotificationTask mTask = new CloseNotificationTask();
        System.out.println("Duration push " + Utils.durationStringToSec(survey.getDurationValue()) * 1000);

        mTimer.schedule(mTask, Utils.durationStringToSec(survey.getDurationValue()) * 1000);
    }

    private class CloseNotificationTask extends TimerTask{
        @Override
        public void run() {
            NotificationManager notificationManager = (NotificationManager)
                    getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
        }
    }
}