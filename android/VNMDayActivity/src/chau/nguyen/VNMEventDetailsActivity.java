package chau.nguyen;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import chau.nguyen.calendar.VNMDate;
import chau.nguyen.calendar.VietCalendar;
import chau.nguyen.calendar.ui.VNMDatePickerDialog;
import chau.nguyen.calendar.ui.VNMDatePickerDialog.OnDateSetListener;

public class VNMEventDetailsActivity extends Activity {
	private static final int PROGRESS_DIALOG = 0;
	private static Map<Integer, String> repeations;
	public static final int NO_REPEAT = 0;
	public static final int MONTHLY_REPEAT = 1;
	public static final int YEARLY_REPEAT = 2;
	
	private static Map<Integer, String> reminds;
	public static final int REMIND_10_MINUTES = 0; // ten minutes
	public static final int REMIND_1_HOUR = 1; // one hour = 60 minutes
	public static final int REMIND_1_DAY = 2; // one day = 1440 minutes
	
	private static Map<Integer, String> numberYears;
	public static final int ONE_YEAR = 0;
	public static final int TWO_YEARS = 1;
	public static final int FIVE_YEARS = 2;
	public static final int TEN_YEARS = 3;
	public static final int TWENTY_YEARS = 4;
	
	private static Map<Integer, CalTable> calendars;
	private VNMDate startDate;
	private VNMDate endDate;
	protected EditText titleEditText;
	protected EditText locationEditText;
	protected EditText descriptionEditText;
	protected Button startDateButton;
	protected Button startTimeButton;
	protected Button endDateButton;
	protected Button endTimeButton;
	protected Button saveButton;
	protected Button discardButton;
	protected Spinner repeatsDropDown;
	protected Spinner remindersDropDown;
	protected Spinner calendarsDropDown;
	protected Spinner numberYearsDropDown;
	protected RadioButton lunarCalendarRadio;
	protected RadioButton solarCalendarRadio;
	protected LinearLayout numberYearsContainer;
	
	private boolean isLunar = true;
	
	private ProgressDialog progressDialog;
	
	static {
		reminds = new HashMap<Integer, String>();
		reminds.put(REMIND_10_MINUTES, "Nhắc trước 10 phút");
		reminds.put(REMIND_1_HOUR, "Nhắc trước 1 giờ");
		reminds.put(REMIND_1_DAY, "Nhắc trước 1 ngày");
		
		repeations = new HashMap<Integer, String>();
		repeations.put(NO_REPEAT, "Chỉ một lần");
		repeations.put(MONTHLY_REPEAT, "Hàng tháng");
		repeations.put(YEARLY_REPEAT, "Hàng năm");
		
		numberYears = new HashMap<Integer, String>();
		numberYears.put(ONE_YEAR, "Trong 1 năm");
		numberYears.put(TWO_YEARS, "Trong 2 năm");
		numberYears.put(FIVE_YEARS, "Trong 5 năm");
		numberYears.put(TEN_YEARS, "Trong 10 năm");
		numberYears.put(TWENTY_YEARS, "Trong 20 năm");
		
		calendars = new HashMap<Integer, CalTable>();
	}
	
	private static long getRemindTime(int id) {
		switch (id) {
			case REMIND_1_DAY:
				return 1440;
			case REMIND_1_HOUR:
				return 60;
			default:
				return 10;
		}
	}
	
	private static int getNumberYears(int index) {
		switch (index) {
		case ONE_YEAR:
			return 1;
			
		case TWO_YEARS:
			return 2;
			
		case TEN_YEARS:
			return 10;
			
		case TWENTY_YEARS:
			return 20;

		default:
			return 5;
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vnm_event_detail);
		init();
	}
	
	private void init() {
		this.titleEditText = (EditText)findViewById(R.id.titleEditText);
		this.descriptionEditText = (EditText)findViewById(R.id.descriptionEditText);
		this.locationEditText = (EditText)findViewById(R.id.locationEditText);
		this.startDateButton = (Button)findViewById(R.id.startDateButton);
		this.startTimeButton = (Button)findViewById(R.id.startTimeButton);
		this.endDateButton = (Button)findViewById(R.id.endDateButton);
		this.endTimeButton = (Button)findViewById(R.id.endTimeButton);
		this.saveButton = (Button)findViewById(R.id.saveButton);
		this.discardButton = (Button)findViewById(R.id.discardButton);
		this.remindersDropDown = (Spinner)findViewById(R.id.remindersDropDown);
		this.repeatsDropDown = (Spinner)findViewById(R.id.repeatsDropDown);
		this.calendarsDropDown = (Spinner)findViewById(R.id.calendarsDropDown);
		this.numberYearsDropDown = (Spinner)findViewById(R.id.numberYearsDropDown);
		this.lunarCalendarRadio = (RadioButton)findViewById(R.id.lunarCalendarRadio);
		this.solarCalendarRadio = (RadioButton)findViewById(R.id.solarCalendarRadio);
		this.numberYearsContainer = (LinearLayout)findViewById(R.id.numberYearsContainer);
		Calendar cal = Calendar.getInstance();
		String selectedDate = getIntent().getStringExtra(VNMDayActivity.SELECTED_DATE);
		String[] temps = selectedDate.split(",");
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(temps[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(temps[1]));
		cal.set(Calendar.YEAR, Integer.parseInt(temps[2]));
		
		this.startDate =  VietCalendar.convertSolar2LunarInVietnamese(cal.getTime());
		this.startDate.setHourOfDay(7);
		this.startDate.setMinute(0);
		setDate(this.startDateButton, this.startDate);
		setTime(this.startTimeButton, this.startDate.getHourOfDay(), this.startDate.getMinute());
		this.endDate = VietCalendar.convertSolar2LunarInVietnamese(cal.getTime());
		this.endDate.setHourOfDay(this.startDate.getHourOfDay() + 1);
		this.endDate.setMinute(0);
		setDate(this.endDateButton, this.endDate);
		setTime(this.endTimeButton, this.endDate.getHourOfDay(), this.endDate.getMinute());
		this.startDateButton.setOnClickListener(new DateClickListener(this.startDateButton));
		this.endDateButton.setOnClickListener(new DateClickListener(this.endDateButton));
		this.startTimeButton.setOnClickListener(new TimeClickListener(this.startTimeButton));
		this.endTimeButton.setOnClickListener(new TimeClickListener(this.endTimeButton));
		
		ArrayAdapter<String> repeatAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		for (Integer item : repeations.keySet()) {
			repeatAdapter.insert(repeations.get(item), item);
		}
		repeatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.repeatsDropDown.setAdapter(repeatAdapter);
		this.repeatsDropDown.setSelection(NO_REPEAT);
		this.repeatsDropDown.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long arg3) {
				repeatsDropDown.setSelection(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		ArrayAdapter<String> remindAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		for (Integer item : reminds.keySet()) {
			remindAdapter.insert(reminds.get(item), item);
		}
		remindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.remindersDropDown.setAdapter(remindAdapter);
		this.remindersDropDown.setSelection(REMIND_10_MINUTES);
		this.remindersDropDown.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long arg3) {
				remindersDropDown.setSelection(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		ArrayAdapter<String> numberYearsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		for (Integer item : numberYears.keySet()) {
			numberYearsAdapter.insert(numberYears.get(item), item);
		}
		numberYearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.numberYearsDropDown.setAdapter(numberYearsAdapter);
		this.numberYearsDropDown.setSelection(FIVE_YEARS);
		this.numberYearsDropDown.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long arg3) {
				numberYearsDropDown.setSelection(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		String[] projection = new String[] { "_id", "displayName" };		
		Cursor managedCursor = managedQuery(EventManager.CALENDARS_URI, projection, "selected=1", null, null);
		if (managedCursor == null) {
			// the device doesn't support Calendar
			Toast.makeText(this, "Máy của bạn không hỗ trợ lịch chuẩn của hệ thống. Chức năng này không sử dụng được. Mong bạn thông cảm!", 10)
				.show();
			this.finish();
			return;
		}
		if (managedCursor.moveToFirst()) {
			 String calId;
			 String calName;
			 int idColumn = managedCursor.getColumnIndex("_id");
			 int nameColumn = managedCursor.getColumnIndex("displayName");
			 int index = 0;
			 do {
			    calId = managedCursor.getString(idColumn);
			    calName = managedCursor.getString(nameColumn);
			    if (calName != null) {
			    	calendars.put(index, new CalTable(calId, calName));
				    index++;
			    }
			    
			 } while (managedCursor.moveToNext());
			 calendars.put(index, new CalTable(CalTable.ALL, "Tất cả"));
		}
		ArrayAdapter<String> calAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		for (Integer item : calendars.keySet()) {
			calAdapter.insert(calendars.get(item).name, item);
		}
		calAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.calendarsDropDown.setAdapter(calAdapter);
		this.calendarsDropDown.setSelection(calAdapter.getCount() - 1);
		this.calendarsDropDown.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long arg3) {
				calendarsDropDown.setSelection(position);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}			
		});
		
		this.saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Integer selected = calendarsDropDown.getSelectedItemPosition();
				String calId = ((CalTable)calendars.get(selected)).id;
				String[] calIds;
				if (CalTable.ALL.equals(calId)) {
					calIds = new String[calendars.size() - 1];
					for (int i = 0; i < calendars.size() - 1; i++) {
						calIds[i] = ((CalTable)calendars.get((Integer)i)).id;
					}
				} else {
					calIds = new String[1];
					calIds[0] = calId;
				}
				
				int numberYears = getNumberYears(numberYearsDropDown.getSelectedItemPosition());
			    long reminderMinutes = getRemindTime(remindersDropDown.getSelectedItemPosition());
			    int repeat = repeatsDropDown.getSelectedItemPosition();
				CreatingEvent creatingEvent = new CreatingEvent();
				creatingEvent.setLunarEvent(lunarCalendarRadio.isChecked());
				creatingEvent.setTitle(titleEditText.getText().toString());
				creatingEvent.setDescription(descriptionEditText.getText().toString());
				creatingEvent.setEventLocation(locationEditText.getText().toString());
				creatingEvent.setCalIds(calIds);
				creatingEvent.setContentResolver(getContentResolver());
				creatingEvent.setEndDate(endDate);
				creatingEvent.setStartDate(startDate);
				creatingEvent.setNumberYears(numberYears);
				creatingEvent.setReminderMinutes(reminderMinutes);
				creatingEvent.setRepeat(repeat);
				AsyncTask<CreatingEvent, Void, Void> creatingTask = new AsyncTask<CreatingEvent, Void, Void>() {
					@Override
					protected void onPreExecute() {						
						super.onPreExecute();
						showDialog(PROGRESS_DIALOG);
					}
					@Override
					protected Void doInBackground(CreatingEvent... params) {
						params[0].run();
						return null;
					}
					@Override
					protected void onPostExecute(Void result) {
						super.onPostExecute(result);		
						dismissDialog(PROGRESS_DIALOG);
						VNMEventDetailsActivity.this.finish();
					}
				};
				creatingTask.execute(creatingEvent);				
			}			
		});
		
		this.discardButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}			
		});
		
		this.lunarCalendarRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					numberYearsContainer.setVisibility(View.VISIBLE);
					isLunar = true;
					startDate = VietCalendar.convertSolar2LunarInVietnamese(startDate.getMinute(), startDate.getHourOfDay(), startDate.getDayOfMonth(), startDate.getMonth(), startDate.getYear());
					endDate = VietCalendar.convertSolar2LunarInVietnamese(endDate.getMinute(), endDate.getHourOfDay(), endDate.getDayOfMonth(), endDate.getMonth(), endDate.getYear());
					setDate(startDateButton, startDate);
					setDate(endDateButton, endDate);
					//Log.i("INFO", "Lundar radio have been checked");
				}
			}
		});
		
		this.solarCalendarRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					numberYearsContainer.setVisibility(View.GONE);
					isLunar = false;
					startDate = VietCalendar.convertLuner2SolarInVietnamese(startDate.getMinute(), startDate.getHourOfDay(), startDate.getDayOfMonth(), startDate.getMonth(), startDate.getYear());
					endDate = VietCalendar.convertLuner2SolarInVietnamese(endDate.getMinute(), endDate.getHourOfDay(), endDate.getDayOfMonth(), endDate.getMonth(), endDate.getYear());
					setDate(startDateButton, startDate);
					setDate(endDateButton, endDate);
					//Log.i("INFO", "Solar radio have been checked");
					Log.i("INFO", "start date " + startDate.toString() + " end date " + endDate.toString());
				}
			}
		});
		
		this.lunarCalendarRadio.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
		
		this.solarCalendarRadio.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
	}
	
	protected Dialog onCreateDialog(int id) {
        switch(id) {
        case PROGRESS_DIALOG:
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Đang tạo sự kiện...");
            return progressDialog;
        default:
            return null;
        }
    }
	
	private void setDate(TextView view, VNMDate date) {
		String str = isLunar ? " (Âm lịch)" : " (Dương lịch)";
		String text = isLunar ? date.getDayOfMonth() + "-" + date.getMonth() + "-" + date.getYear() : date.getDayOfMonth() + "-" + date.getMonth() + 1 + "-" + date.getYear();
		view.setText(text + str);
	}
	
	private void setTime(TextView view, int hourOfDay, int minute) {
		String hour = hourOfDay + "";
		String minuteInText = minute + "";
		if (hourOfDay < 10)
			hour = "0" + hour;
		if (minute < 10)
			minuteInText = "0" + minuteInText;
			
		view.setText(hour + ":" + minuteInText);
	}
	
	private class DateClickListener implements View.OnClickListener {
		 private View view;
		 public DateClickListener(View view) {
			 this.view = view;
		 }
		 public void onClick(View v) {
			 
			 if (view == startDateButton) {
				 VNMDatePickerDialog dialog = new VNMDatePickerDialog(VNMEventDetailsActivity.this, new DateSetListener(DateSetListener.START_DATE), startDate.getYear(), startDate.getMonth() - 1, startDate.getDayOfMonth());
				 dialog.setRadioGroupDateTypeVisible(false);
				 dialog.show();
			 } else {
				 VNMDatePickerDialog dialog = new VNMDatePickerDialog(VNMEventDetailsActivity.this, new DateSetListener(DateSetListener.END_DATE), endDate.getYear(), endDate.getMonth() - 1, endDate.getDayOfMonth());
				 dialog.setRadioGroupDateTypeVisible(false);
				 dialog.show();
			 }
		 }
	}
	
	private class DateSetListener implements OnDateSetListener {
		private static final String START_DATE = "startDate";
		private static final String END_DATE = "endDate";
		private String dialog;
		
		public DateSetListener(String dialog) {
			this.dialog = dialog;
		}

		@Override
		public void onDateSet(DatePicker view, boolean isSolarSelected,
				int year, int monthOfYear, int dayOfMonth) {
			monthOfYear ++;
			if (this.dialog.equals(START_DATE)) {
				startDate.setDayOfMonth(dayOfMonth);
				startDate.setMonth(monthOfYear);
				startDate.setYear(year);
				setDate(startDateButton, startDate);
				if (VietCalendar.compare(startDate, endDate) == 1) {
					endDate.setDayOfMonth(dayOfMonth);
					endDate.setMonth(monthOfYear);
					endDate.setYear(year);
					setDate(endDateButton, endDate);
				}
			} else {
				VNMDate temp = new VNMDate(dayOfMonth, monthOfYear, year, endDate.getHourOfDay(), endDate.getMinute());
				if (VietCalendar.compare(startDate, temp) == -1) {
					endDate = temp;
					setDate(endDateButton, endDate);
				} else {
					Toast.makeText(VNMEventDetailsActivity.this, "Thời điểm kết thúc phải sau thời điểm bắt đầu", Toast.LENGTH_SHORT).show();
				}
			}
			
		}
		
	}
	
	private class TimeClickListener implements View.OnClickListener {
		 private View view;
		 public TimeClickListener(View view) {
			 this.view = view;
		 }
		 public void onClick(View v) {
			 if (view == startTimeButton) {
				 new TimePickerDialog(VNMEventDetailsActivity.this, new TimeSetListener(TimeSetListener.START_TIME), startDate.getHourOfDay(), startDate.getMinute(), true).show();
			 } else {
				 new TimePickerDialog(VNMEventDetailsActivity.this, new TimeSetListener(TimeSetListener.END_TIME), endDate.getHourOfDay(), endDate.getMinute(), true).show();
			 }
		 }
	}
	
	private class TimeSetListener implements OnTimeSetListener {
		private static final String START_TIME = "startTime";
		private static final String END_TIME = "endTime";
		private String dialog;
		
		public TimeSetListener(String dialog) {
			this.dialog = dialog;
		}

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			if (START_TIME.equals(this.dialog)) {
				startDate.setHourOfDay(hourOfDay);
				startDate.setMinute(minute);
				setTime(startTimeButton, hourOfDay, minute);
				int startTimeInMinutes = startDate.getHourOfDay() * 60 + startDate.getMinute();
				int endTimeInMinutes = endDate.getHourOfDay() * 60 + endDate.getMinute();
				if (startTimeInMinutes >= endTimeInMinutes) {
					hourOfDay++;
					endDate.setHourOfDay(hourOfDay);
					endDate.setMinute(minute);
					setTime(endTimeButton, hourOfDay, minute);
				}
			} else {
				VNMDate temp = new VNMDate();
				temp = endDate;
				temp.setHourOfDay(hourOfDay);
				temp.setMinute(minute);
				if (VietCalendar.compare(startDate, temp) == -1) {
					endDate.setHourOfDay(hourOfDay);
					endDate.setMinute(minute);
					setTime(endTimeButton, hourOfDay, minute);
				} else {
					Toast.makeText(VNMEventDetailsActivity.this, "Thời điểm kết thúc phải sau thời điểm bắt đầu", Toast.LENGTH_SHORT).show();
				}
			}
		}
		
	}
	
	private class CalTable {
		public static final String ALL = "ALL";
		public String id;
		public String name;
		public CalTable(String id, String name) {
			this.id = id;
			this.name = name;
		}
	}
}
