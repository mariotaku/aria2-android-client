package org.mariotaku.aria2.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Aria2Service extends Service implements Constants {

	private IBinder mBinder = new ServiceStub(this);
	private Aria2API mAria2 = new Aria2API();

	@Override
	public void onCreate() {

		if (!getFilesDir().exists()) {
			getFilesDir().mkdirs();
		} else {
			startAria2();
		}
	}

	@Override
	public void onDestroy() {
		stopAria2();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public void startAria2() {

		new Thread() {

			@Override
			public void run() {
				if (!isAria2Running()) {
					exec("aria2c --enable-rpc --log=/sdcard/aria2.log --log-level=info");
				}
			}
		}.start();

	}

	public void stopAria2() {
		if (isAria2Running()) {
			mAria2.shutdown();
		}
	}

	public boolean isAria2Running() {
		return exec("/system/bin/ps aria2c").contains("aria2c");
	}

	private String exec(String command) {
		try {
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			int read;
			char[] buffer = new char[4096];
			StringBuffer output = new StringBuffer();
			while ((read = reader.read(buffer)) > 0) {
				output.append(buffer, 0, read);
			}
			reader.close();
			process.waitFor();
			return output.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * By making this a static class with a WeakReference to the Service, we
	 * ensure that the Service can be GCd even when the system process still has
	 * a remote reference to the stub.
	 */
	static class ServiceStub extends IAria2Service.Stub {

		WeakReference<Aria2Service> mService;

		ServiceStub(Aria2Service service) {

			mService = new WeakReference<Aria2Service>(service);
		}

		@Override
		public void startAria2() {
			mService.get().startAria2();
		}

		@Override
		public void stopAria2() {
			mService.get().stopAria2();
		}

	}
}
