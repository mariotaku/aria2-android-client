package org.mariotaku.aria2.android;

import java.util.HashMap;
import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;

@SuppressWarnings("unchecked")
public class Aria2CommandWrapper {

	XMLRPCClient mClient = new XMLRPCClient("http://localhost:6800/rpc");

	public void addUri(String[] uris, String[] options, int position) {

	}

	/**
	 * This method returns global statistics such as overall download and upload
	 * speed. The response is of type struct and contains following keys. The
	 * value type is string.
	 * 
	 * @return a {@link HashMap} contains following data: <br>
	 * <br>
	 *         <b>downloadSpeed</b>: Overall download speed (byte/sec). <br>
	 *         <b>uploadSpeed</b>: Overall upload speed(byte/sec). <br>
	 *         <b>numActive</b>: The number of active downloads. <br>
	 *         <b>numWaiting</b>: The number of waiting downloads. <br>
	 *         <b>numStopped</b>: The number of stopped downloads.
	 */
	public HashMap<String, Object> getGlobalStat() {
		try {
			return (HashMap<String, Object>) mClient.call("aria2.getGlobalStat", "");
		} catch (XMLRPCException e) {
			e.printStackTrace();
		}
		return new HashMap<String, Object>();
	}

	/**
	 * This method shutdowns aria2. This method returns "OK".
	 * 
	 * @return "OK" if succeed.
	 */
	public String shutdown() {

		try {
			return (String) mClient.call("aria2.shutdown", "");
		} catch (XMLRPCException e) {
			e.printStackTrace();
		}
		return "FAILED";
	}

	/**
	 * This method shutdowns aria2. This method behaves like aria2.shutdown
	 * except that any actions which takes time such as contacting BitTorrent
	 * tracker are skipped. This method returns "OK".
	 * 
	 * @return "OK" if succeed.
	 */
	public String forceShutdown() {
		try {
			return (String) mClient.call("aria2.forceShutdown", "");
		} catch (XMLRPCException e) {
			e.printStackTrace();
		}
		return "FAILED";
	}

	/**
	 * This method returns session information. The response is of type struct
	 * and contains following key.
	 * 
	 * @return a {@link HashMap} contains following data: <br>
	 * <br>
	 *         <b>version</b> : Version number of the program in string
	 *         (String).<br>
	 * 
	 *         <b>enabledFeatures</b> : List of enabled features. Each feature
	 *         name is of type string (Object[]).
	 */
	public HashMap<String, Object> getVersion() {
		try {
			return (HashMap<String, Object>) mClient.call("aria2.getVersion", "");
		} catch (XMLRPCException e) {
			e.printStackTrace();
		}
		return new HashMap<String, Object>();
	}

	/**
	 * This method returns session information. The response is of type struct
	 * and contains following key.
	 * 
	 * @return a {@link HashMap} contains following data: <br>
	 * <br>
	 *         <b>sessionId</b> : Session ID, which is generated each time when
	 *         aria2 is invoked.
	 */
	public HashMap<String, String> getSessionInfo() {
		try {
			return (HashMap<String, String>) mClient.call("aria2.getSessionInfo", "");
		} catch (XMLRPCException e) {
			e.printStackTrace();
		}
		return new HashMap<String, String>();
	}

}
