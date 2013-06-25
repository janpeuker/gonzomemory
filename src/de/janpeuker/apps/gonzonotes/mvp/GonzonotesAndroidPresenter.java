package de.janpeuker.apps.gonzonotes.mvp;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.SaveCallback;

import de.janpeuker.apps.gonzonotes.Constants;
import de.janpeuker.apps.gonzonotes.GonzonotesActivity;
import de.janpeuker.apps.gonzonotes.ParseTransfer;
import de.janpeuker.apps.gonzonotes.R;
import de.janpeuker.apps.gonzonotes.provider.NotesContract;

@SuppressLint("NewApi")
public class GonzonotesAndroidPresenter extends SaveCallback implements GonzonotesPresenter, LoaderManager.LoaderCallbacks<Cursor> {

	private static final String SHOW_READABLE_KEY = "ShowReadable";

	private final Context context;
	private final GonzonotesActivity activity;
	private SimpleCursorAdapter adapter;

	private final GonzonotesViewModel model;

	private static GonzonotesAndroidPresenter instance;

	public static GonzonotesAndroidPresenter getInstance(final GonzonotesActivity activity) {
		instance = new GonzonotesAndroidPresenter(activity);
		return instance;
	}

	private GonzonotesAndroidPresenter(final GonzonotesActivity activity) {
		this.activity = activity;
		context = activity.getBaseContext();
		model = new SharedPreferencesBasedGonzonotesViewModel(context);
	}

	@Override
	public void createView(final Bundle savedInstanceState) {
		final Button button = (Button) activity.findViewById(R.id.buttonStart);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View view) {

				startTransfer();

			}

		});

		boolean checked = false;

		if ((savedInstanceState != null) && (savedInstanceState.containsKey(SHOW_READABLE_KEY))) {
			checked = savedInstanceState.getBoolean(SHOW_READABLE_KEY);
		}

		((CheckBox) activity.findViewById(R.id.checkBoxReadable)).setChecked(checked);

		createLoaderAdapter();
	}

	@Override
	public void resumeView() {

		Log.d(Constants.TAG, "Presenter in resumeView");

		model.loadState(context);

		final TextView it = (TextView) activity.findViewById(R.id.textInput);
		it.setText(model.getLastEnteredText());

		activity.getLoaderManager().restartLoader(0, null, this);
	}

	@Override
	public void pauseView() {
		model.persistState(context);
	}

	@Override
	public void startTransfer() {

		final CharSequence lastEnteredText = ((TextView) activity.findViewById(R.id.textInput)).getText();

		model.setLastEnteredText(lastEnteredText.toString());

		final ProgressBar pb = (ProgressBar) activity.findViewById(R.id.progressBarTransfer);
		pb.setIndeterminate(true);
		pb.setVisibility(View.VISIBLE);

		ParseTransfer.storeObject(model, this);

	}

	@Override
	public void storeInstanceState(final Bundle storeBundle) {
		model.persistState(context);
		// Store state the model does not persist itself
		final CheckBox checkBox = (CheckBox) activity.findViewById(R.id.checkBoxReadable);
		storeBundle.putBoolean(SHOW_READABLE_KEY, checkBox.isChecked());
	}

	@Override
	public void done(final ParseException e) {
		final ProgressBar pb = (ProgressBar) activity.findViewById(R.id.progressBarTransfer);

		pb.setVisibility(View.INVISIBLE);

		final TextView ot = (TextView) activity.findViewById(R.id.textOutput);

		if (e == null) {
			ot.setText("Finish");
			// Reload List from Server
			activity.getLoaderManager().restartLoader(0, null, this);

		} else {
			if (((CheckBox) activity.findViewById(R.id.checkBoxReadable)).isChecked()) {
				ot.setText("Error");
			} else {
				ot.setText(e.getMessage());
			}
			Log.w(Constants.TAG, "Error in Parse done", e);
		}

	}

	public void createLoaderAdapter() {

		activity.getLoaderManager().initLoader(0, null, this);

		adapter = new SimpleCursorAdapter(context, R.layout.noteslistitem, null, new String[] { NotesContract.KEY_NOTE_ID,
				NotesContract.KEY_NOTE_CONTENT_TEXT }, new int[] { R.id.textNoteKey, R.id.textNoteContent },
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

		// Bind to our new adapter.
		final ListView lv = (ListView) activity.findViewById(R.id.listOfNotes);
		lv.setAdapter(adapter);

	}

	// Creates a new loader after the initLoader () call
	@Override
	public Loader<Cursor> onCreateLoader(final int id, final Bundle args) {
		final String[] projection = { NotesContract.KEY_ID, NotesContract.KEY_NOTE_ID, NotesContract.KEY_NOTE_CONTENT_TEXT };
		final CursorLoader cursorLoader = new CursorLoader(context, NotesContract.AUTHORITY_URI, projection, null, null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(final Loader<Cursor> loader, final Cursor data) {
		adapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(final Loader<Cursor> loader) {
		// data is not available anymore, delete reference
		adapter.swapCursor(null);
	}

}
