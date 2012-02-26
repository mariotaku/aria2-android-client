package org.mariotaku.aria2;

public class Files extends CommonItem {

	/**
	 * Index of file. Starting with 1. This is the same order with the files in
	 * multi-file torrent.
	 */
	public int index;

	/**
	 * File path.
	 */
	public String path;

	/**
	 * File size in bytes.
	 */
	public int length;

	/**
	 * Completed length of this file in bytes. Please note that it is possible
	 * that sum of completedLength is less than completedLength in
	 * {@link Aria2API#tellStatus(int, String...)} method. This is because completedLength in
	 * aria2.getFiles only calculates completed pieces. On the other hand,
	 * completedLength in {@link Aria2API#tellStatus(int, String...)} takes into account of partially
	 * completed piece.
	 */
	public int completedLength;

	/**
	 * "true" if this file is selected by --select-file option. If --select-file
	 * is not specified or this is single torrent or no torrent download, this
	 * value is always "true". Otherwise "false".
	 */
	public boolean selected;

	/**
	 * Returns the list of URI for this file. The element of list is the same
	 * struct used in aria2.getUris method.
	 */
	public String[] uris;

}
