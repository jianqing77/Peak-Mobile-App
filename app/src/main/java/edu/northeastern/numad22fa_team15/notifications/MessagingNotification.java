package edu.northeastern.numad22fa_team15.notifications;

import android.app.Application;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import edu.northeastern.numad22fa_team15.R;

public class MessagingNotification extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Someone just sent you a sticker!")
                .setContentText("Your friend just sent you a sticker. Tap this notification" +
                        "to reply.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
        }
    }
}