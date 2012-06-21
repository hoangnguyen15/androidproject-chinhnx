package chau.nguyen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import chau.nguyen.calendar.VietCalendar;
import chau.nguyen.calendar.util.CryptoUtils;

public class DayInfoActivity extends Activity {
	private static String ENCRYPTED_SEED = "1B6C5A8B1BBDB1A0C1C6B4FA2D838B0D";
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private WebView webView;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		webView = new WebView(this);
		setContentView(webView);
		
		long time = this.getIntent().getLongExtra("Date", new Date().getTime());
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;		
		int year = calendar.get(Calendar.YEAR);
					
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head><style>");
		sb.append(readText("datedata/style.css", null));
		sb.append("</style><head><body>");
		sb.append("<h2>Ngày " + dayOfMonth
				+ " tháng " + month
				+ " năm " + year
				+ "</h2>");
		
		
		int[] lunars = VietCalendar.convertSolar2LunarInVietnam(calendar.getTime());
		String[] vnmCalendarTexts = VietCalendar.getCanChiInfo(lunars[VietCalendar.DAY], lunars[VietCalendar.MONTH], lunars[VietCalendar.YEAR], dayOfMonth, month, year);
		sb.append("<em class=\"AL\">(" + lunars[VietCalendar.DAY] + " tháng " + lunars[VietCalendar.MONTH] + " năm " + vnmCalendarTexts[VietCalendar.YEAR] + ")</em>");
		
		String fileName = "datedata/" + year + "/" + simpleDateFormat.format(calendar.getTime()) + ".html";
		sb.append(decryptFile(fileName, getResources().getString(R.string.no_date_info)));
		sb.append("</body></html>");			
		String dayInfo = sb.toString();
		webView.loadDataWithBaseURL("http://dummy", dayInfo, "text/html", "UTF-8", null);			
	}
	
	private String readText(String fileName, String defaultText) {
		StringBuffer sb = new StringBuffer();
		try {
			InputStream is = this.getAssets().open(fileName);		
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {				
				sb.append(line).append("\n");
			}
			is.close();
		} catch (IOException e) {
			sb.append(defaultText);
			e.printStackTrace();
		}		
		return sb.toString();
	}
	
	private String decryptFile(String fileName, String defaultText) {
		StringBuffer sb = new StringBuffer();
		try {			
			byte[] encryptedBytes = new byte[(int)this.getAssets().openFd(fileName).getLength()];
			Log.d("DEBUG", "Length: " + encryptedBytes.length);
			InputStream is = this.getAssets().open(fileName);
			is.read(encryptedBytes);
			is.close();			
			byte[] rawKey = CryptoUtils.toByte(ENCRYPTED_SEED);			
			byte[] decryptedBytes = CryptoUtils.decrypt(rawKey, encryptedBytes);
			return new String(decryptedBytes, "UTF-8");
		} catch (Exception e) {
			sb.append(defaultText);
			e.printStackTrace();
		}		
		return sb.toString();
	}		
}
