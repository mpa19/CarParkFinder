package com.example.marc.carparkfinder.ui.CloudMessage;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import com.example.marc.carparkfinder.R;
import com.example.marc.carparkfinder.ui.Reserva.ReservarActivity;
import com.example.marc.carparkfinder.ui.Reserva.TimerHelper.TimePickerFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private Intent resultIntent;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("campus"));
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

        }
    }

    private void showNotification(String title, final String campus) {
        final NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title+campus);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        db.collection("Reserva")
                .document(user.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (!documentSnapshot.exists()) {

                            resultIntent = new Intent(getApplication(), ReservarActivity.class);
                            if (campus.equals(getResources().getString(R.string.cap))) {
                                resultIntent.putExtra("Campus", 1);
                            } else resultIntent.putExtra("Campus", 2);


                            PendingIntent resultPendingIntent =
                                    PendingIntent.getActivity(
                                            getApplicationContext(),
                                            0,
                                            resultIntent,
                                            PendingIntent.FLAG_UPDATE_CURRENT
                                    );

                            mBuilder.setContentIntent(resultPendingIntent);
                            int mNotificationId = (int) System.currentTimeMillis();
                            NotificationManager mNotifyMgr =
                                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                int importance = NotificationManager.IMPORTANCE_HIGH;
                                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);

                                mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                                mNotifyMgr.createNotificationChannel(notificationChannel);
                            }
                            mNotifyMgr.notify(mNotificationId, mBuilder.build());
                        }
                    }
                });
    }
}

