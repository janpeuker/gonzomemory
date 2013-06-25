package de.janpeuker.apps.gonzonotes;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Application;
import android.util.Log;

public class GonzonotesApplication extends Application {

	private static final String GONZONOTES_APPLICATION_ID = "LLGtuK815Yy6Svn6WVfcXJFgGdlgAX9GlxuATL9B";
	private static final String GONZONOTES_CLIENT_KEY = "ZCW2kAmoc1ZmlXtMbQ4RoQLgVuh6hK1bnkxFksIE";

	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
		Parse.initialize(this, GONZONOTES_APPLICATION_ID, GONZONOTES_CLIENT_KEY);

		ParseUser.logInInBackground("janpeuker", "CPP6LqSFUHswLAV", new LogInCallback() {

			@Override
			public void done(final ParseUser user, final ParseException e) {
				if (e != null) {
					Log.e(Constants.TAG, "Could not log in user", e);
				} else {
					Log.i(Constants.TAG, "Logged in user " + user.getUsername());
				}
			}
		});

		final ParseACL defaultACL = new ParseACL();

		ParseACL.setDefaultACL(defaultACL, true);

	}

	// ParseUser user = new ParseUser();
	// user.setUsername("janpeuker");
	// user.setPassword("CPP6LqSFUHswLAV");
	// user.setEmail("devel@janpeuker.de");
	//
	// user.signUpInBackground(new SignUpCallback() {
	// public void done(ParseException e) {
	// if (e == null) {
	// Log.i(Constants.TAG, "Created user janpeuker");
	// } else {
	// Log.e(Constants.TAG, "Could not create user", e);
	// }
	// }
	// });

}
