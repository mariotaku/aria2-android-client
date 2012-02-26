/*
 * Copyright (C) 2012 SP-time. All right reserved.
 *
 */

package org.mariotaku.aria2.android.utils;

import java.util.HashMap;

import org.mariotaku.aria2.android.Aria2Service;
import org.mariotaku.aria2.android.IAria2Service;
import org.mariotaku.aria2.android.IAria2Service.Stub;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;

public class ServiceUtils {

	public static IAria2Service mService = null;
	private static HashMap<Context, ServiceBinder> mConnectionMap = new HashMap<Context, ServiceBinder>();

	private static class ServiceBinder implements ServiceConnection {

		ServiceConnection mCallback;

		ServiceBinder(ServiceConnection callback) {

			mCallback = callback;
		}

		@Override
		public void onServiceConnected(ComponentName className, android.os.IBinder service) {

			mService = IAria2Service.Stub.asInterface(service);
			if (mCallback != null) {
				mCallback.onServiceConnected(className, service);
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName className) {

			if (mCallback != null) {
				mCallback.onServiceDisconnected(className);
			}
			mService = null;
		}
	}

	public static ServiceToken bindToService(Context context, ServiceConnection callback) {

		ContextWrapper cw = new ContextWrapper(context);
		cw.startService(new Intent(cw, Aria2Service.class));
		ServiceBinder sb = new ServiceBinder(callback);
		if (cw.bindService((new Intent()).setClass(cw, Aria2Service.class), sb, 0)) {
			mConnectionMap.put(cw, sb);
			return new ServiceToken(cw);
		}
		Log.e("Music", "Failed to bind to service");
		return null;
	}

	public static void unbindFromService(ServiceToken token) {

		if (token == null) {
			Log.e("ServiceUtils", "Trying to unbind with null token");
			return;
		}
		ContextWrapper wrapper = token.mWrappedContext;
		ServiceBinder binder = mConnectionMap.remove(wrapper);
		if (binder == null) {
			Log.e("ServiceUtils", "Trying to unbind for unknown Context");
			return;
		}
		wrapper.unbindService(binder);
		if (mConnectionMap.isEmpty()) {
			mService = null;
		}
	}
}
