package de.janpeuker.apps.gonzonotes.mvp;

import android.os.Bundle;

public interface GonzonotesPresenter {

	public void createView(Bundle savedInstanceState);

	public void resumeView();

	public void pauseView();

	public void startTransfer();

	public void storeInstanceState(Bundle storeBundle);

}
