package com.beatcorona.game;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.beatcorona.game.BeatCorona;

public class AndroidLauncher extends AndroidApplication implements Services {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new BeatCorona(this), config);
	}

	@Override
	public void share() {

		final Intent sharingIntent = new Intent();
		sharingIntent.setAction(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Beat Corona");

		String sAux = "\nTry this great game dedicated to fight Corona Virus.\nDownload it here: \n\n";
		sAux += AppInfo.PLAYSTORE_LINK + getPackageName() + " \n\n";

		sharingIntent.putExtra(Intent.EXTRA_TEXT, sAux);
		Log.i("mystring",sAux);
		startActivity(Intent.createChooser(sharingIntent, "Share via:"));

	}

	@Override
	public void share(int highscore) {
		final Intent sharingIntent = new Intent();
		sharingIntent.setAction(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Beat Corona");
		String sAux = "\nI scored " + String.valueOf(highscore) + " points in #BeatCorona.\nDo you think you can score more?\nAccept the challenge!\n\n";

		sAux += AppInfo.PLAYSTORE_LINK + getPackageName() + " \n\n";

		sharingIntent.putExtra(Intent.EXTRA_TEXT, sAux);

		startActivity(Intent.createChooser(sharingIntent, "Send challenge via:"));
	}

	@Override
	public void rate() {
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(AppInfo.PLAYSTORE_LINK + getPackageName())));
	}

}
