/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.example.spinner;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Displays an Android spinner widget backed by data in an array. The
 * array is loaded from the strings.xml resources file.
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        /**
         * derived classes that use onCreate() overrides must always call the super constructor
         */
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        // Gather data
        int id = 12345;
        int icon = android.R.drawable.ic_popup_sync;
        String spinner = "This is a test";
        String title = "Test title";
        String body = "Test body";
        long when = System.currentTimeMillis();
        int alarmFreq = 5*1000;
        
        // Schedule AlarmNotification triggering
        setRecurringNotificationAlarm(this, id, icon, spinner, title, body, when, alarmFreq);
        setRecurringNotificationAlarm(this, id+1, icon, "Second, more annoying alarm", title, body, when, alarmFreq/5);
        
        // Cancels a given alarm
        //cancelAlarmNotification(this, id);
        //cancelAlarmNotification(this, id+1);
    }

	/**
	 * Cancels a notification with the given id, from within the 
	 * given context.
	 * 
	 * @param c		The context from which the notification was created.
	 * @param id	The identification number of the scheduled alarm
	 */
	public static void cancelAlarmNotification(Context c, int id) {
		// Get the AlarmManager
		AlarmManager am = (AlarmManager) c.getSystemService(ALARM_SERVICE);
		
		// Get the Intent for the 'AlarmNotificationReceiver'
		Intent i = new Intent(c, AlarmNotificationReceiver.class);
		
		// Create 'PendinIntent' to access the 'AlarmNotificationReceiver' 
        PendingIntent pi = PendingIntent.getBroadcast(c, id, i, PendingIntent.FLAG_UPDATE_CURRENT);
        
        // Cancel the alarm (recurring or otherwise)
        am.cancel(pi);
	}

	/**
	 * Schedule a notification to occur repeatedly from time to time,
	 * built with the passed data (icon, title, body and spinner text).
	 * 
	 * @param c			The context from which the notification was created.
	 * @param id		The intended identification number for the scheduled alarm.
	 * @param icon		The intended icon for the notification.
	 * @param spinner	The text to be displayed on the status bar, spinner style.
	 * @param title		The title of the notification to be displayed.
	 * @param body		The body of the notification to be displayed.
	 * @param when		When the notification will be displayed (in miliseconds).
	 * @param alarmFreq	How often will the notification be displayed (<=0 will display it only once).
	 */
	public static void setRecurringNotificationAlarm(Context c, int id, int icon, String spinner,
			String title, String body, long when, int alarmFreq) {
		// Get the AlarmManager 
		AlarmManager am = (AlarmManager) c.getSystemService(ALARM_SERVICE);
		
		// Fill an Intent with the necessary data
        Intent i = new Intent(c, AlarmNotificationReceiver.class);
		i.putExtra("ALARM_ID", id);
		i.putExtra("ALARM_ICON", icon);
		i.putExtra("ALARM_SPINNER", spinner);
		i.putExtra("ALARM_TITLE", title);
		i.putExtra("ALARM_BODY", body);
		i.putExtra("ALARM_WHEN", when);
		
		// Create 'PendinIntent' to launch the 'AlarmNotificationReceiver'
        PendingIntent pi = PendingIntent.getBroadcast(c, id, i, PendingIntent.FLAG_UPDATE_CURRENT);
        
        // If a non-nil, non-positive number is used as frequency...
        if(alarmFreq <= 0){
        	// Schedule one notification
        	am.set(AlarmManager.RTC_WAKEUP, when, pi);
        } else{
        	//...if not, schedule recurring notifications
        	am.setRepeating(AlarmManager.RTC_WAKEUP, when, alarmFreq, pi);
        }
	}
	
	/**
	 * Schedule a notification to occur on the given time, built with the
	 * passed data (icon, title, body and spinner text).
	 * 
	 * @param c			The context from which the notification was created.
	 * @param id		The intended identification number for the scheduled alarm.
	 * @param icon		The intended icon for the notification.
	 * @param spinner	The text to be displayed on the status bar, spinner style.
	 * @param title		The title of the notification to be displayed.
	 * @param body		The body of the notification to be displayed.
	 * @param when		When the notification will be displayed (in miliseconds).
	 * @param alarmFreq	How often will the notification be displayed (<=0 will display it only once).
	 */
	public static void setSingleNotificationAlarm(Context c, int id, int icon, String spinner,
			String title, String body, long when, int alarmFreq) {
		setRecurringNotificationAlarm(c, id, icon, spinner, title, body, when, 0);
	}

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
	public void onDestroy() {
		// Auto-generated method stub
		super.onDestroy();
		
        // Cancel alarm notifications, in case of crash
        cancelAlarmNotification(this, 12345);
        cancelAlarmNotification(this, 12346);
	}

}
