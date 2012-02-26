package org.mariotaku.aria2.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Aria2DroidActivity extends Activity implements Constants, ServiceConnection,
		OnClickListener {

	private IAria2Service mService = null;
	private ServiceToken mToken = null;

	private Aria2API aria2;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		aria2 = new Aria2API();

		findViewById(R.id.version).setOnClickListener(this);
		findViewById(R.id.session_info).setOnClickListener(this);
		findViewById(R.id.global_stats).setOnClickListener(this);
		findViewById(R.id.run).setOnClickListener(this);

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
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.version:
				StringBuilder version = new StringBuilder();
				version.append("Version : " + aria2.getVersion().version + "\n");
				Object[] values = aria2.getVersion().enabledFeatures;
				StringBuilder features = new StringBuilder();
				for (Object value : values) {
					features.append(value + "\n");
				}
				features.delete(features.length() - 1, features.length());
				version.append("Enabled features : \n" + features.toString());
				
				((TextView) v).setText(version.toString());
				break;
			case R.id.session_info:
				StringBuilder session_info = new StringBuilder();
				session_info.append("Session ID : " + aria2.getSessionInfo().sessionId);
				((TextView) v).setText(session_info.toString());
				break;
			case R.id.global_stats:
				StringBuilder global_stats = new StringBuilder();
				global_stats.append("Download speed : " + aria2.getGlobalStat().downloadSpeed + "\n");
				global_stats.append("Upload speed : " + aria2.getGlobalStat().uploadSpeed + "\n");
				global_stats.append("Active : " + aria2.getGlobalStat().numActive + "\n");
				global_stats.append("Stopped : " + aria2.getGlobalStat().numStopped + "\n");
				global_stats.append("Waiting : " + aria2.getGlobalStat().numWaiting);
				((TextView) v).setText(global_stats.toString());
				break;
			case R.id.run:
				((TextView) v)
						.setText("Return value : "
								+ aria2.addUri(
										new DownloadUris(
												"http://android-x86.googlecode.com/files/android-x86-4.0-eeepc-20120101.iso"),
										new Options()));
				break;
		}

	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		mService = IAria2Service.Stub.asInterface(service);
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {

	}
}