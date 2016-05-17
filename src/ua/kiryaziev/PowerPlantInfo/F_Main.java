package ua.kiryaziev.PowerPlantInfo;

import java.util.Timer;
import java.util.TimerTask;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class F_Main extends Activity implements OnClickListener {

	TextView text_bl4, text_bl5, text_bl6, text_bl7, text_bl8, text_bl9,
			text_bl10, text_bl11, text_bl12, text_bl13;

	Button button_bl4, button_bl5, button_bl6, button_bl7, button_bl8,
			button_bl9, button_bl10, button_bl11, button_bl12, button_bl13;

	XMLParser xmlP;
	Timer t1;
	Handler h;
	
	//AES
	AESModule asem;
	
	SharedPreferences pref;
	
	String host;
	int delay;
	boolean running = false;
	String userName = "";
	String userPass = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f_main);
		
		text_bl4 = (TextView) findViewById(R.id.text_bl4);
		text_bl5 = (TextView) findViewById(R.id.text_bl5);
		text_bl6 = (TextView) findViewById(R.id.text_bl6);
		text_bl7 = (TextView) findViewById(R.id.text_bl7);
		text_bl8 = (TextView) findViewById(R.id.text_bl8);
		text_bl9 = (TextView) findViewById(R.id.text_bl9);
		text_bl10 = (TextView) findViewById(R.id.text_bl10);
		text_bl11 = (TextView) findViewById(R.id.text_bl11);
		text_bl12 = (TextView) findViewById(R.id.text_bl12);
		text_bl13 = (TextView) findViewById(R.id.text_bl13);

		button_bl4 = (Button) findViewById(R.id.button_bl4);
		button_bl5 = (Button) findViewById(R.id.button_bl5);
		button_bl6 = (Button) findViewById(R.id.button_bl6);
		button_bl7 = (Button) findViewById(R.id.button_bl7);
		button_bl8 = (Button) findViewById(R.id.button_bl8);
		button_bl9 = (Button) findViewById(R.id.button_bl9);
		button_bl10 = (Button) findViewById(R.id.button_bl10);
		button_bl11 = (Button) findViewById(R.id.button_bl11);
		button_bl12 = (Button) findViewById(R.id.button_bl12);
		button_bl13 = (Button) findViewById(R.id.button_bl13);

		button_bl4.setOnClickListener(this);
		button_bl5.setOnClickListener(this);
		button_bl6.setOnClickListener(this);
		button_bl7.setOnClickListener(this);
		button_bl8.setOnClickListener(this);
		button_bl9.setOnClickListener(this);
		button_bl10.setOnClickListener(this);
		button_bl11.setOnClickListener(this);
		button_bl12.setOnClickListener(this);
		button_bl13.setOnClickListener(this);

		xmlP = new XMLParser();
		//ASE
		asem = new AESModule("aeskey.key", "0123456789abcdef");
		pref = getSharedPreferences("settings", 0);

		/*
		 * if (android.os.Build.VERSION.SDK_INT > 9) { StrictMode.ThreadPolicy
		 * policy = new StrictMode.ThreadPolicy.Builder() .permitAll().build();
		 * StrictMode.setThreadPolicy(policy); }
		 */

		// startGettingData();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (t1 != null) {
			t1.cancel();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
				
		host = pref.getString("host", "");
		delay = (pref.getInt("delay", 0) + 5) * 1000;
		
		// читаем имя
		userName = pref.getString("userName", "");
		// читаем пароль
		userPass = pref.getString("userPass", "");
		
		if (running == true) {
			startGettingData();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
	}

	private void startGettingData() {
		t1 = new Timer();
		h = new Handler();
		t1.schedule(new TimerTask() {
			@Override
			public void run() {
				h.post(t1_Tick);
			}
		}, 0, delay);		
	}

  //===================================
  // ф-ция таймера.обращение к php странице, парсинг ответа, вывод данных на экран
  //===================================
	private Runnable t1_Tick = new Runnable() {
		public void run() {
			// Log.v("Timer", "tick");
			String res = xmlP.getXmlFromUrl(host + "/read_w_act.php", userName, userPass);
			Document doc = xmlP.getDomElement(asem.AESDecryptB64(res));
			doc.getDocumentElement().normalize();
			NodeList nl = doc.getElementsByTagName("w_act");

			for (int i = 0; i < nl.getLength(); ++i) {
				Element e = (Element) nl.item(i);
				text_bl4.setText(xmlP.getValue(e, "bl4"));
				text_bl5.setText(xmlP.getValue(e, "bl5"));
				text_bl6.setText(xmlP.getValue(e, "bl6"));
				text_bl7.setText(xmlP.getValue(e, "bl7"));
				text_bl8.setText(xmlP.getValue(e, "bl8"));
				text_bl9.setText(xmlP.getValue(e, "bl9"));
				text_bl10.setText(xmlP.getValue(e, "bl10"));
				text_bl11.setText(xmlP.getValue(e, "bl11"));
				text_bl12.setText(xmlP.getValue(e, "bl12"));
				text_bl13.setText(xmlP.getValue(e, "bl13"));
			}
		}
	};

	// ======================
	// buttons
	// ======================
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_bl4:
			openDetails(4);
			break;
		case R.id.button_bl5:
			openDetails(5);
			break;
		case R.id.button_bl6:
			openDetails(6);
			break;
		case R.id.button_bl7:
			openDetails(7);
			break;
		case R.id.button_bl8:
			openDetails(8);
			break;
		case R.id.button_bl9:
			openDetails(9);
			break;
		case R.id.button_bl10:
			openDetails(10);
			break;
		case R.id.button_bl11:
			openDetails(11);
			break;
		case R.id.button_bl12:
			openDetails(12);
			break;
		case R.id.button_bl13:
			openDetails(13);
			break;

		default:
			break;
		}
	}

	private void openDetails(int blok) {
		Intent i = new Intent(getApplicationContext(), F_Details.class);
		Bundle b = new Bundle();
		b.putInt("blok", blok);
		b.putString("host", host);
		b.putInt("delay", delay);
		b.putBoolean("running", running);
		b.putString("userName", userName);
		b.putString("userPass", userPass);
		i.putExtras(b);
		startActivity(i);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_start:
			if (running == false) {				
				item.setTitle("Остановить");
				startGettingData();
				running = true;
			} else {
				item.setTitle("Запустить");				
				if (t1 != null) {
					t1.cancel();
				}
				running = false;
			}
			return true;
		case R.id.menu_settings:
			Intent i = new Intent(getApplicationContext(), F_Settings.class);
			startActivity(i);
			return true;			
		case R.id.menu_about:
			Intent i_about = new Intent(getApplicationContext(), F_About.class);
			startActivity(i_about);
			return true;
		case R.id.menu_exit:
			finish();
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.f_main, menu);
		return true;
	}
}
