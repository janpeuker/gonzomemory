package de.janpeuker.apps.gonzonotes.mvp;

import android.content.Context;

public interface GonzonotesViewModel {

	public String getLastEnteredText();

	public void setLastEnteredText(final String lastEnteredText);

	public int getLastNoteId();

	public void setLastNoteId(int id);

	public void persistState(final Context context);

	public void loadState(final Context context);

}
