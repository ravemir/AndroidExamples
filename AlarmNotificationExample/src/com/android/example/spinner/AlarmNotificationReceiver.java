package com.android.example.spinner;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmNotificationReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent launchingIntent) {
		// Parse intent arguments (title, text body, icon, ...)
		Bundle extras = launchingIntent.getExtras();
		int id = extras.getInt("ALARM_ID");
		int icon = extras.getInt("ALARM_ICON");
		String spinnerText = extras.getString("ALARM_SPINNER");
		String title = extras.getString("ALARM_TITLE");
		String body = extras.getString("ALARM_BODY");
		long when = extras.getLong("ALARM_WHEN");

 		// Create PendingIntent to launch the passed intent
 		PendingIntent pi = PendingIntent.getActivity(context, id, launchingIntent, 0);
		
		// Get the NotificationManager and build the notification
		NotificationManager nm = (NotificationManager)
 				context.getSystemService(Context.NOTIFICATION_SERVICE);
 		Notification ntfn = new Notification(icon, spinnerText, when);
 		ntfn.setLatestEventInfo(context, title, body, pi);
 		ntfn.flags |= Notification.FLAG_AUTO_CANCEL; // Notification clears when clicked
		
		// Display notification
		nm.notify(id, ntfn);
	}

}
