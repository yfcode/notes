package org.lxndr.notes;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MainService extends Service {
	
	Storage m_Storage;
	
	
	@Override
	public void onCreate() {
		m_Storage = new Storage(this);
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_STICKY;
	}
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
}
