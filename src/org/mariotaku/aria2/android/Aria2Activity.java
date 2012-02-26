package org.mariotaku.aria2.android;

import java.util.Timer;
import java.util.TimerTask;

import org.mariotaku.actionbarcompat.app.ActionBarActivity;
import org.mariotaku.aria2.Aria2API;
import org.mariotaku.aria2.DownloadUris;
import org.mariotaku.aria2.GlobalStat;
import org.mariotaku.aria2.Options;
import org.mariotaku.aria2.android.utils.CommonUtils;
import org.mariotaku.aria2.android.utils.ServiceToken;
import org.mariotaku.aria2.android.utils.ServiceUtils;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Aria2Activity extends ActionBarActivity implements Constants, ServiceConnection,
		OnClickListener {

	private final static int GLOBAL_STAT_REFRESHED = 0;

	private IAria2Service mService = null;
	private ServiceToken mToken = null;
	private Timer mGlobalStatRefreshTimer;

	private Aria2API aria2;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		getActionBarCompat().setIcon(R.drawable.ic_launcher);

		aria2 = new Aria2API();

		findViewById(R.id.version).setOnClickListener(this);
		findViewById(R.id.session_info).setOnClickListener(this);
		findViewById(R.id.status).setOnClickListener(this);
		findViewById(R.id.run).setOnClickListener(this);

	}

	@Override
	public void onStart() {
		super.onStart();
		mGlobalStatRefreshTimer = new Timer();
		mGlobalStatRefreshTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				Message globalstat_msg = new Message();
				globalstat_msg.what = GLOBAL_STAT_REFRESHED;
				globalstat_msg.obj = aria2.getGlobalStat();
				mStatusRefreshHandler.sendMessage(globalstat_msg);

			}

		}, 0, 1000);

		mToken = ServiceUtils.bindToService(this, this);
	}

	@Override
	public void onStop() {
		mGlobalStatRefreshTimer.cancel();
		mGlobalStatRefreshTimer = null;
		ServiceUtils.unbindFromService(mToken);
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_download, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.new_download:
				break;
		}
		return super.onOptionsItemSelected(item);
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
			case R.id.status:
				aria2.tellStatus(7, "gid");
				//((TextView) v).setText(String.valueOf(aria2.tellStatus(7, "gid").gid));
				break;
			case R.id.run:
				((TextView) v)
						.setText("Return value : "
								+ aria2.addUri(
										new DownloadUris(
												"http://releases.ubuntu.com/11.10/ubuntu-11.10-desktop-i386.iso.torrent"),
										new Options()));
				break;
		}

	}

	private Handler mStatusRefreshHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case GLOBAL_STAT_REFRESHED:
					GlobalStat stat = (GlobalStat) msg.obj;
					if (msg.obj == null) return;
					String subtitle = getString(R.string.global_speed_format,
							CommonUtils.formatSpeedString(stat.downloadSpeed),
							CommonUtils.formatSpeedString(stat.uploadSpeed));
					getActionBarCompat().setSubtitle(subtitle);
					break;
			}
		}
	};

	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		mService = IAria2Service.Stub.asInterface(service);
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {

	}
}