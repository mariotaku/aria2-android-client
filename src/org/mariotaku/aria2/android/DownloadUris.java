package org.mariotaku.aria2.android;

import java.util.ArrayList;

public class DownloadUris {

	private ArrayList<String> uris = new ArrayList<String>();

	public DownloadUris(String uri) {
		init(uri);
	}

	public DownloadUris(String uri, String... mirrors) {
		init(uri, mirrors);

	}

	public void addMirror(String mirror) {
		uris.add(mirror);
	}

	public String[] getUris() {
		return uris.toArray(new String[uris.size()]);
	}

	public boolean isBitTorrent() {

		for (String uri : uris) {
			if ((uri.endsWith(".torrent")) || (uri.endsWith(".meta4"))
					|| (uri.endsWith(".metalink")) || (uri.startsWith("magnet:"))) {
				return true;
			}
		}

		return false;
	}

	private void init(String uri, String... mirrors) {
		if (uri == null) throw new IllegalArgumentException("URI can't be null!");
		uris.clear();
		uris.add(uri);
		if (mirrors != null) {
			for (String mirror : mirrors) {
				uris.add(mirror);
			}
		}
	}
}