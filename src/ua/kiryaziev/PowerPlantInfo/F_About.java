package ua.kiryaziev.PowerPlantInfo;

import android.app.Activity;
import android.os.Bundle;

public class F_About extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f_about);
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
	
}
