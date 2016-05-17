package ua.kiryaziev.PowerPlantInfo;

import java.util.Timer;
import java.util.TimerTask;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class F_Details extends Activity {

	TextView textBlok, textDateTime, textW_ACT, textW_REACT, textP_PARA_TG,
			textVAKUM, textF_PIT_VODA, textF_PARA_1, textF_PARA_2, textF_VPRISK,
			textF_GAZA, textT_PIT_VODA, textT_PARA_1, textT_PARA_2, textT_VAKUM;

	DateParser dp;
	XMLParser xmlP;
	Timer t1;
	Handler h;
	// AES
	AESModule asem;

	private int blok, delay;
	private String host, addr;
	private boolean running = false;
	private String userName = "";
	private String userPass = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f_details);

		Bundle b = getIntent().getExtras();

		textBlok = (TextView) findViewById(R.id.textBlok);
		textDateTime = (TextView) findViewById(R.id.textDateTime);
		textW_ACT = (TextView) findViewById(R.id.textW_ACT);
		textW_REACT = (TextView) findViewById(R.id.textW_REACT);
		textP_PARA_TG = (TextView) findViewById(R.id.textP_PARA_TG);
		textVAKUM = (TextView) findViewById(R.id.textVAKUM);

		textF_PIT_VODA = (TextView) findViewById(R.id.textF_PIT_VODA);
		textF_PARA_1 = (TextView) findViewById(R.id.textF_PARA_1);
		textF_PARA_2 = (TextView) findViewById(R.id.textF_PARA_2);
		textF_VPRISK = (TextView) findViewById(R.id.textF_VPRISK);
		textF_GAZA = (TextView) findViewById(R.id.textF_GAZA);

		textT_PIT_VODA = (TextView) findViewById(R.id.textT_PIT_VODA);
		textT_PARA_1 = (TextView) findViewById(R.id.textT_PARA_1);
		textT_PARA_2 = (TextView) findViewById(R.id.textT_PARA_2);
		textT_VAKUM = (TextView) findViewById(R.id.textT_VAKUM);

		dp = new DateParser();
		xmlP = new XMLParser();

		asem = new AESModule("aeskey.key", "0123456789abcdef");

		blok = b.getInt("blok");
		host = b.getString("host");
		delay = b.getInt("delay");
		running = b.getBoolean("running");
		userName = b.getString("userName");
		userPass = b.getString("userPass");

		addr = host + "/read_details_bl" + blok + ".php";
		textBlok.setText("Блок №" + blok);

		if (running == true) {
			startGettingData();
		}
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

	private Runnable t1_Tick = new Runnable() {
		public void run() {
			// Log.v("Timer", "tick");
			String res = xmlP.getXmlFromUrl(addr, userName, userPass);
			Document doc = xmlP.getDomElement(asem.AESDecryptB64(res));
			doc.getDocumentElement().normalize();
			NodeList nl = doc.getElementsByTagName("details");

			for (int i = 0; i < nl.getLength(); ++i) {
				Element e = (Element) nl.item(i);
				textDateTime.setText(dp.parseDate(xmlP.getValue(e, "datetime"),	"yyyy-MM-dd HH:mm:ss"));
				textW_ACT.setText(xmlP.getValue(e, "W_ACT"));
				textW_REACT.setText(xmlP.getValue(e, "W_REACT"));
				textP_PARA_TG.setText(xmlP.getValue(e, "P_PARA_TG"));
				textVAKUM.setText(xmlP.getValue(e, "VAKUM"));

				textF_PIT_VODA.setText(xmlP.getValue(e, "F_PIT_VODA"));
				textF_PARA_1.setText(xmlP.getValue(e, "F_PARA_1"));
				textF_PARA_2.setText(xmlP.getValue(e, "F_PARA_2"));
				textF_VPRISK.setText(xmlP.getValue(e, "F_VPRISK"));
				textF_GAZA.setText(xmlP.getValue(e, "F_GAZA"));

				textT_PIT_VODA.setText(xmlP.getValue(e, "T_PIT_VODA"));
				textT_PARA_1.setText(xmlP.getValue(e, "T_PARA_1"));
				textT_PARA_2.setText(xmlP.getValue(e, "T_PARA_2"));
				textT_VAKUM.setText(xmlP.getValue(e, "T_VAKUM"));
			}
		}
	};

	@Override
	protected void onPause() {
		super.onPause();
		if (t1 != null) {
			t1.cancel();
		}
		finish();
	}
}
