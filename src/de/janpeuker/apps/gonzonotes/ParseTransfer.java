package de.janpeuker.apps.gonzonotes;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import de.janpeuker.apps.gonzonotes.mvp.GonzonotesViewModel;
import de.janpeuker.apps.gonzonotes.provider.NotesContract;

public class ParseTransfer {

	public static final String PARSE_CLASS = "GonzoNotes";
	private static final String PARSE_KEY_PREFIX = PARSE_CLASS + "_";
	private static final String NOTE_CONTENT = "noteContent";

	public static void storeObject(final GonzonotesViewModel model, final SaveCallback callback) {

		final int nextId = model.getLastNoteId() + 1;
		final String parseKey = PARSE_KEY_PREFIX + nextId;
		model.setLastNoteId(nextId);
		final String lastEnteredText = model.getLastEnteredText();

		Log.i(Constants.TAG, "Storing " + parseKey + " in Parse");

		final ParseObject parseObject = new ParseObject(PARSE_CLASS);
		parseObject.put(NotesContract.KEY_NOTE_ID, parseKey);
		parseObject.put(NOTE_CONTENT, getJsonObject(lastEnteredText.toString()));

		parseObject.saveInBackground(callback);

	}

	public static String[][] getAllObjects() {

		Log.i(Constants.TAG, "Starting Parse Query for all objects");

		final ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseTransfer.PARSE_CLASS);
		String[][] returnArray = null;
		try {
			// I/O Blocking Operation
			final List<ParseObject> objects = query.find();
			if ((objects != null) && (objects.size() > 0)) {
				returnArray = new String[objects.size()][2];
				int i = 0;
				for (final ParseObject object : objects) {
					returnArray[i++] = new String[] { object.getString(NotesContract.KEY_NOTE_ID), getContentFromJson(object) };
				}
			} else {
				Log.w(Constants.TAG, "0 Objects returned from Parse Query");
			}
		} catch (final ParseException e) {
			Log.e(Constants.TAG, "Parse could not retrieve data", e);
		}
		return returnArray;
	}

	private static String getContentFromJson(final ParseObject object) {
		final JSONObject json = object.getJSONObject(NOTE_CONTENT);
		String returnString = "";
		try {
			returnString = json.getString(NotesContract.KEY_NOTE_CONTENT_TEXT);
		} catch (final JSONException e) {
			Log.e(Constants.TAG, "JSON Parse Error for " + object, e);
		}
		return returnString;
	}

	private static JSONObject getJsonObject(final String lastEnteredText) {
		final JSONObject json = new JSONObject();
		try {
			json.put(NotesContract.KEY_NOTE_CONTENT_TEXT, lastEnteredText);
		} catch (final JSONException e) {
			Log.e(Constants.TAG, "JSON Create Error for " + lastEnteredText, e);
		}
		return json;
	}

}
