package ua.kiryaziev.PowerPlantInfo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class F_Settings extends Activity {

	EditText editHost, editUserName, edituserPass;
	SeekBar seekDelay;
	TextView textDelay, textUserName, textuserPass;
	int delay;
	String userPass = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f_settings);

		editHost = (EditText) findViewById(R.id.editHost);
		seekDelay = (SeekBar) findViewById(R.id.seekDelay);
		textDelay = (TextView) findViewById(R.id.textDelay);
		textUserName = (TextView) findViewById(R.id.textUserName);	// label Name
		textuserPass = (TextView) findViewById(R.id.textuserPass);	// label Key
		editUserName = (EditText) findViewById(R.id.editUserName);	// text Name
		edituserPass = (EditText) findViewById(R.id.edituserPass);	// text Key

		seekDelay.setOnSeekBarChangeListener(seekDelay_Change);

		// читаем настройки
		SharedPreferences pref = getSharedPreferences("settings", 0);
		editHost.setText(pref.getString("host", "http://"));
		seekDelay.setProgress(pref.getInt("delay", 0));

		delay = seekDelay.getProgress() + 5;
		textDelay.setText(delay + " с");

		// читаем имя
		editUserName.setText(pref.getString("userName", ""));
		
		// читаем ключ
		userPass = pref.getString("userPass", "");

		if (!userPass.isEmpty()) {
			textuserPass.setTextColor(Color.rgb(0, 255, 0));
			textuserPass.setText("сохранен");
		} else {
			textuserPass.setTextColor(Color.rgb(255, 0, 0));
			textuserPass.setText("отсутствует");
		}

	}

	public OnSeekBarChangeListener seekDelay_Change = new OnSeekBarChangeListener() {
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			delay = progress + 5;
			textDelay.setText(delay + " с");
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		public void onStopTrackingTouch(SeekBar seekBar) {
		}
	};

	public void buttonSave_Click(View v) {
		SharedPreferences pref = getSharedPreferences("settings", 0);
		SharedPreferences.Editor editor = pref.edit();
		
		editor.putString("host", editHost.getText().toString());
		editor.putInt("delay", seekDelay.getProgress());
		
		// пишем имя
		editor.putString("userName", editUserName.getText().toString());
		
		// пишем ключ
		if (!edituserPass.getText().toString().isEmpty()) {
			editor.putString("userPass", MyCrypt.getMD5(edituserPass.getText().toString()));
		}
		
		editor.commit();
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

}
