package org.mariotaku.aria2.android;

import java.io.IOException;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;

@SuppressWarnings("unchecked")
public class Aria2API {

	private XMLRPCClient mClient;

	public Aria2API() {
		init("localhost", 6800);
	}

	public Aria2API(String host) {
		init(host, 6800);
	}

	/**
	 * This method adds new HTTP(S)/FTP/BitTorrent Magnet URI. uris is of type
	 * array and its element is URI which is of type string. For BitTorrent
	 * Magnet URI, uris must have only one element and it should be BitTorrent
	 * Magnet URI. URIs in uris must point to the same file. If you mix other
	 * URIs which point to another file, aria2 does not complain but download
	 * may fail.
	 * 
	 * @deprecated Using default options compiled in aria2 is not recommend,
	 *             please use {@link #addUri(DownloadUuris, Options)} instead.
	 * 
	 * @see DownloadUris
	 * 
	 * @return GID of registered download.
	 */
	public int addUri(DownloadUris uris) {

		Object result = callMethod("aria2.addUri", new Object[] { uris.getUris() });

		if (result == null) return -1;

		return Integer.parseInt((String) result);
	}

	/**
	 * This method adds new HTTP(S)/FTP/BitTorrent Magnet URI. uris is of type
	 * array and its element is URI which is of type string. For BitTorrent
	 * Magnet URI, uris must have only one element and it should be BitTorrent
	 * Magnet URI. URIs in uris must point to the same file. If you mix other
	 * URIs which point to another file, aria2 does not complain but download
	 * may fail.
	 * 
	 * @see Options
	 * 
	 * @see DownloadUris
	 * 
	 * @return GID of registered download.
	 */
	public int addUri(DownloadUris uris, Options options) {

		Object result = callMethod("aria2.addUri", new Object[] { uris.getUris(), options.get() });

		if (result == null) return -1;

		return Integer.parseInt((String) result);
	}

	/**
	 * This method adds new HTTP(S)/FTP/BitTorrent Magnet URI. uris is of type
	 * array and its element is URI which is of type string. For BitTorrent
	 * Magnet URI, uris must have only one element and it should be BitTorrent
	 * Magnet URI. URIs in uris must point to the same file. If you mix other
	 * URIs which point to another file, aria2 does not complain but download
	 * may fail. If position is given as an integer starting from 0, the new
	 * download is inserted at position in the waiting queue. If position is
	 * larger than the size of the queue, it is appended at the end of the
	 * queue.
	 * 
	 * @see Options
	 * 
	 * @see DownloadUris
	 * 
	 * @return GID of registered download.
	 */
	public int addUri(DownloadUris uris, Options options, int position) {
		if (position < 0)
			throw new IllegalArgumentException("position can't be a negative value!");
		Object result = callMethod("aria2.addUri", new Object[] { uris.getUris(), options.get(),
				position });

		if (result == null) return -1;

		return Integer.parseInt((String) result);
	}

	/**
	 * This method shutdowns aria2. This method behaves like aria2.shutdown
	 * except that any actions which takes time such as contacting BitTorrent
	 * tracker are skipped.
	 * 
	 * @return "OK" if succeed.
	 */
	public String forceShutdown() {
		return (String) callMethod("aria2.forceShutdown");
	}

	/**
	 * @return global statistics such as overall download and upload speed.
	 */
	public GlobalStat getGlobalStat() {
		return new GlobalStat((HashMap<String, Object>) callMethod("aria2.getGlobalStat"));
	}

	/**
	 * @return session information.
	 */
	public SessionInfo getSessionInfo() {

		return new SessionInfo((HashMap<String, Object>) callMethod("aria2.getSessionInfo"));
	}

	/**
	 * @return version of the program and the list of enabled features.
	 */
	public Version getVersion() {

		return new Version((HashMap<String, Object>) callMethod("aria2.getVersion"));
	}

	/**
	 * Removes completed/error/removed download denoted by gid from memory.
	 * 
	 * @return "OK" if succeed.
	 */
	public String removeDownloadResult(int gid) {
		return (String) callMethod("aria2.removeDownloadResult", gid);
	}

	/**
	 * Shutdowns aria2.
	 * 
	 * @return "OK" if succeed.
	 */
	public String shutdown() {

		return (String) callMethod("aria2.shutdown");
	}

	private Object callMethod(String method, Object... args) {
		try {
			return mClient.call(method, args);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (XMLRPCException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void init(String host, int port) {
		mClient = new XMLRPCClient("http://" + host + ":" + port + "/rpc");

	}
}
