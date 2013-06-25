package de.janpeuker.apps.gonzonotes.mvp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesBasedGonzonotesViewModel implements GonzonotesViewModel {

	private static final String KEY_LAST_ENTERED_TEXT = "lastEnteredText";
	private static final String KEY_LAST_ID = "lastId";
	private final Context context;
	private final String SHARED_PREFERENCES_NAME = "de.janpeuker.apps.gonzonotes";

	public SharedPreferencesBasedGonzonotesViewModel(final Context context) {
		this.context = context;
	}

	private SharedPreferences getSharedPreferences() {
		final SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

		return prefs;
	}

	private void storeInt(final String key, final int value) {
		getSharedPreferences().edit().putInt(key, value).apply();
	}

	private void storeString(final String key, final String value) {
		getSharedPreferences().edit().putString(key, value).apply();
	}

	private int readInt(final String key) {
		return getSharedPreferences().getInt(key, 0);
	}

	private String readString(final String key) {
		return getSharedPreferences().getString(key, "");
	}

	@Override
	public String getLastEnteredText() {
		return readString(KEY_LAST_ENTERED_TEXT);
	}

	@Override
	public void setLastEnteredText(final String lastEnteredText) {
		storeString(KEY_LAST_ENTERED_TEXT, lastEnteredText);
	}

	@Override
	public void persistState(final Context context) {
		// Auto-Persisted
	}

	@Override
	public int getLastNoteId() {
		return readInt(KEY_LAST_ID);
	}

	@Override
	public void setLastNoteId(final int id) {
		storeInt(KEY_LAST_ID, id);
	}

	@Override
	public void loadState(final Context context) {
		// Auto-Loaded
	}

}
