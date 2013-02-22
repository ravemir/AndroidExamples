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
        setNotificationAlarm(id, icon, spinner, title, body, when, alarmFreq);
        setNotificationAlarm(id+1, icon, "HAHA", title, body, when, alarmFreq/5);
        
        // Cancels a given alarm
//        cancelAlarmNotification(id);
//        cancelAlarmNotification(id+1);
    }

	/**
	 * @param id
	 */
	private void cancelAlarmNotification(int id) {
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent i = new Intent(this, AlarmNotificationReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, id, i, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(pi);
	}

	/**
	 * @param id
	 * @param icon
	 * @param spinner
	 * @param title
	 * @param body
	 * @param when
	 * @param alarmFreq
	 */
	private void setNotificationAlarm(int id, int icon, String spinner,
			String title, String body, long when, int alarmFreq) {
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this, AlarmNotificationReceiver.class);
		i.putExtra("ALARM_ID", id);
		i.putExtra("ALARM_ICON", icon);
		i.putExtra("ALARM_SPINNER", spinner);
		i.putExtra("ALARM_TITLE", title);
		i.putExtra("ALARM_BODY", body);
		i.putExtra("ALARM_WHEN", when);
        PendingIntent pi = PendingIntent.getBroadcast(this, id, i, PendingIntent.FLAG_UPDATE_CURRENT);
		am.setRepeating(AlarmManager.RTC_WAKEUP, when, alarmFreq, pi);
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
        cancelAlarmNotification(12345);
        cancelAlarmNotification(12346);
	}

}
