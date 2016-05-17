package ua.kiryaziev.PowerPlantInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class F_Splashscr extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f_splashscr);

		Thread th = new Thread() {
			@Override
			public void run() {
				try {
					sleep(2000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					Intent i = new Intent(getApplicationContext(), F_Main.class);
					startActivity(i);
				}
			}
		};
		th.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}
