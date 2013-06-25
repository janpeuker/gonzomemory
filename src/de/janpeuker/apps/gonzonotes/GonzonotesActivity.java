package de.janpeuker.apps.gonzonotes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import de.janpeuker.apps.gonzonotes.mvp.GonzonotesAndroidPresenter;
import de.janpeuker.apps.gonzonotes.mvp.GonzonotesPresenter;

@SuppressLint("NewApi")
public class GonzonotesActivity extends Activity {

	private GonzonotesPresenter presenter;
	private SimpleCursorAdapter adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		presenter = GonzonotesAndroidPresenter.getInstance(this);
		presenter.createView(savedInstanceState);

	}

	@Override
	public void onPause() {
		super.onPause();
		presenter.pauseView();
	}

	@Override
	public void onResume() {
		super.onResume();
		presenter.resumeView();
	}

	@Override
	protected void onSaveInstanceState(final Bundle storeBundle) {
		super.onSaveInstanceState(storeBundle);
		presenter.storeInstanceState(storeBundle);
	}

}
