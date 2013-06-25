package de.janpeuker.apps.gonzonotes.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;
import de.janpeuker.apps.gonzonotes.Constants;
import de.janpeuker.apps.gonzonotes.ParseTransfer;

public class NotesProvider extends ContentProvider {

	@Override
	public int delete(final Uri arg0, final String arg1, final String[] arg2) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public String getType(final Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(final Uri arg0, final ContentValues arg1) {
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public boolean onCreate() {
		return true;
	}

	@Override
	public Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
		// TODO: Implement uri check and selection

		Log.d(Constants.TAG, "Provider queried " + uri);

		// I/O Blocking
		final String[][] objects = ParseTransfer.getAllObjects();
		MatrixCursor matrixCursor = null;
		if (objects != null) {
			final int capacity = objects.length;
			matrixCursor = new MatrixCursor(new String[] { NotesContract.KEY_ID, NotesContract.KEY_NOTE_ID, NotesContract.KEY_NOTE_CONTENT_TEXT },
					capacity);
			int i = 0;
			for (final String[] object : objects) {
				final String[] newObject = new String[3];
				// _id is 0, mandatory for Adapter
				newObject[0] = Integer.toString(i++);
				newObject[1] = object[0];
				newObject[2] = object[1];
				matrixCursor.addRow(newObject);
			}
		} else {
			// empty cursor
			matrixCursor = new MatrixCursor(new String[] { NotesContract.KEY_ID, NotesContract.KEY_NOTE_ID, NotesContract.KEY_NOTE_CONTENT_TEXT }, 1);
		}
		return matrixCursor;
	}

	@Override
	public int update(final Uri uri, final ContentValues values, final String selection, final String[] selectionArgs) {
		throw new UnsupportedOperationException("not implemented");
	}

}
