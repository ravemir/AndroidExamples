package com.android.example.spinner;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReschedulerReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent launchingIntent) {
		// TODO Access application data
		
		// TODO Get all scheduled alarms
		ArrayList<Object> schedAlarms = new ArrayList<Object>();
		
		// TODO Reschedule them
		for(Object sched : schedAlarms){
			// TODO Do shtuff
		}
	}

}
