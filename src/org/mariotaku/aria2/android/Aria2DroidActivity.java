package org.mariotaku.aria2.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class Aria2DroidActivity extends Activity implements Constants, ServiceConnection {

	private IAria2Service mService = null;
	private ServiceToken mToken = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		findViewById(R.id.output).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Object[] list = (Object[]) new Aria2CommandWrapper().getVersion().get(
						"enabledFeatures");

				for (Object feature : list) {
					Log.d(LOGTAG, "enabledFeatures = " + feature);
				}
			}

		});
	}

	@Override
	public void onStart() {
		super.onStart();
		mToken = ServiceUtils.bindToService(this, this);
	}

	@Override
	public void onStop() {
		ServiceUtils.unbindFromService(mToken);
		super.onStop();
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		mService = IAria2Service.Stub.asInterface(service);
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {

	}
}