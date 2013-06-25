package de.janpeuker.apps.gonzonotes.provider;

import android.net.Uri;

public class NotesContract {

	public static final String MIME_ALLNOTES = "vnd.android.cursor.dir/de.janpeuker.apps.gonzonotes.provider.notes";
	public static final String MIME_NOTE = "vnd.android.cursor.item/de.janpeuker.apps.gonzonotes.provider.notes";

	public static final String KEY_ID = "_id";
	public static final String KEY_NOTE_ID = "nodeId";
	public static final String KEY_NOTE_CONTENT_TEXT = "nodeContentText";

	public static final Uri AUTHORITY_URI = Uri.parse("content://de.janpeuker.apps.gonzonotes.provider/notes");

}
